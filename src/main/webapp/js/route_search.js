/*
  Leaflet + OSM stack
  - Favorites/History: localStorage + Database
*/

let map;
let routeLayer;
let cluster;
let lastRouteCoords = null; 
let allPois = []; 
let originMarker = null;
let destMarker = null;

const els = {};
function qs(id){ return document.getElementById(id); }

// LocalStorage Utils
function loadStore(key, fallback){
  try{ return JSON.parse(localStorage.getItem(key)) ?? fallback; }catch{ return fallback; }
}
function saveStore(key, value){
  try{ localStorage.setItem(key, JSON.stringify(value)); }catch{}
}

const STORE = {
  favoritesKey: 'mapSearch_favorites',
  historyKey: 'mapSearch_history',
  favorites: [],
  history: []
};

document.addEventListener('DOMContentLoaded', initLeaflet);

function initLeaflet(){
  // UI Elements Mapping
  els.origin = qs('origin');
  els.destination = qs('destination');
  els.search = qs('search');
  els.routeSummary = qs('route-summary');
  els.btnFavorites = qs('btn-favorites');
  els.btnHistory = qs('btn-history');
  els.favoritesPanel = qs('favorites-panel');
  els.historyPanel = qs('history-panel');
  els.addFavorite = qs('add-favorite'); // パネル内ボタン
  els.addFavInline = qs('add-fav-inline'); // ★新設ボタン
  els.clearHistory = qs('clear-history');
  els.favoritesList = qs('favorites-list');
  els.historyList = qs('history-list');
  els.poiList = qs('poi-list');
  els.poiSight = qs('poi-sight');
  els.poiFood = qs('poi-food');
  els.poiService = qs('poi-service');
  els.modeRoute = qs('mode-route');
  els.modeSight = qs('mode-sight');
  els.sightField = qs('sight-field');
  els.placeQuery = qs('place-query');
  els.placeSearch = qs('place-search');
  els.poiDetail = qs('poi-detail');
  els.transportMode = qs('transport-mode');

  // Map Setup
  const tokyo = [35.6762, 139.6503];
  const osm = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 19 });
  map = L.map('map', { layers: [osm] }).setView(tokyo, 12);
  cluster = L.markerClusterGroup().addTo(map);

  // Load storage
  STORE.favorites = loadStore(STORE.favoritesKey, []);
  STORE.history = loadStore(STORE.historyKey, []);
  renderFavorites();
  renderHistory();

  // Events
  els.search.addEventListener('click', onSearch);
  els.btnFavorites.addEventListener('click', ()=>togglePanel('favorites'));
  els.btnHistory.addEventListener('click', ()=>togglePanel('history'));
  
  // ★保存ボタン（2箇所とも同じ関数を呼ぶ）
  els.addFavorite?.addEventListener('click', addCurrentToFavorites);
  els.addFavInline?.addEventListener('click', addCurrentToFavorites);

  els.clearHistory.addEventListener('click', clearHistory);
  els.placeSearch?.addEventListener('click', onPlaceSearch);
  els.modeRoute?.addEventListener('change', onModeChange);
  els.modeSight?.addEventListener('change', onModeChange);

  onModeChange();
}

// DB保存用関数
function saveToDatabase(start, end, polyline, isFavorite) {
  if(!polyline) return; 
  
  const params = new URLSearchParams();
  params.append('start', start);
  params.append('end', end);
  params.append('polyline', JSON.stringify(polyline));
  params.append('isFavorite', isFavorite);

  fetch('../saveRoute', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: params.toString()
  })
  .then(res => { if(res.ok) console.log("DB保存成功"); })
  .catch(err => console.error("DB保存失敗", err));
}

// お気に入り追加処理
function addCurrentToFavorites(){
  const origin = els.origin.value.trim();
  const destination = els.destination.value.trim();

  if(!origin || !destination){
    alert('出発と到着を入力してください。');
    return;
  }
  if(!lastRouteCoords){
    alert('ルート検索を行ってから保存してください。');
    return;
  }

  // 重複チェック
  if(STORE.favorites.some(f => f.origin===origin && f.destination===destination)){
    alert('登録済みです。');
    return;
  }

  // LocalStorage保存
  STORE.favorites.push({ origin, destination, saved: Date.now() });
  saveStore(STORE.favoritesKey, STORE.favorites);
  renderFavorites();

  // DB保存
  saveToDatabase(origin, destination, lastRouteCoords, true);
  alert('お気に入りに保存しました！');
}

async function onSearch(){
  const origin = els.origin.value.trim();
  const destination = els.destination.value.trim();
  if(!origin || !destination) return;

  try{
    const [o, d] = await Promise.all([ geocode(origin), geocode(destination) ]);
    const route = await routeOsrm(o, d);
    if(!route) return;

    drawRoute(route.geometry.coordinates);
    lastRouteCoords = route.geometry.coordinates.map(([lon,lat])=>[lat,lon]);
    
    showSummary(`出発: ${origin}\n到着: ${destination}\n距離: ${(route.distance/1000).toFixed(1)} km`);
    
    // 履歴としてDB保存
    saveToDatabase(origin, destination, lastRouteCoords, false);
    pushHistory({ origin, destination, when: Date.now() });
  }catch(e){ console.error(e); }
}

// --- 以降、既存のLeaflet関連関数などは変更なし ---
function togglePanel(which){
  const p = which==='favorites' ? els.favoritesPanel : els.historyPanel;
  p.classList.toggle('hidden');
}

function onModeChange(){
  const routeOn = !!els.modeRoute?.checked;
  [els.origin, els.destination, els.search, els.transportMode, els.addFavInline].forEach(el => {
    if(el) el.closest('.field')?.style.setProperty('display', routeOn ? '' : 'none');
  });
  if(els.sightField) els.sightField.style.display = routeOn ? 'none' : '';
}

async function geocode(q){
  const res = await fetch(`https://nominatim.openstreetmap.org/search?format=jsonv2&q=${encodeURIComponent(q)}&accept-language=ja`);
  const data = await res.json();
  return data.length ? { lat: parseFloat(data[0].lat), lng: parseFloat(data[0].lon) } : null;
}

async function routeOsrm(o, d){
  const profile = els.transportMode?.value || 'driving';
  const res = await fetch(`https://router.project-osrm.org/route/v1/${profile}/${o.lng},${o.lat};${d.lng},${d.lat}?overview=full&geometries=geojson`);
  const data = await res.json();
  return data.routes?.[0];
}

function drawRoute(coords){
  const latlngs = coords.map(([lon,lat]) => [lat,lon]);
  if(routeLayer) map.removeLayer(routeLayer);
  routeLayer = L.polyline(latlngs, { color: '#2563eb', weight: 5 }).addTo(map);
  map.fitBounds(routeLayer.getBounds());
}

function showSummary(t){ els.routeSummary.textContent = t; }

function pushHistory(item){
  STORE.history.unshift(item);
  if(STORE.history.length > 20) STORE.history.length = 20;
  saveStore(STORE.historyKey, STORE.history);
  renderHistory();
}

function renderHistory(){
  els.historyList.innerHTML = STORE.history.length ? '' : '<li>履歴なし</li>';
  STORE.history.forEach(h => {
    const li = document.createElement('li');
    li.innerHTML = `<div>${h.origin} → ${h.destination}</div>`;
    els.historyList.appendChild(li);
  });
}

// 1. この関数を既存のものと入れ替えてください
function renderFavorites(){
  els.favoritesList.innerHTML = STORE.favorites.length ? '' : '<li>お気に入りなし</li>';
  STORE.favorites.forEach((f, idx) => {
    const li = document.createElement('li');
    // ボタンを追加し、onclickで削除関数を呼ぶようにします
    li.innerHTML = `
      <div style="display:flex; justify-content:space-between; width:100%; align-items:center;">
        <span>${f.origin} → ${f.destination}</span>
        <button class="mini-btn danger" onclick="deleteFavorite(${idx}, ${f.id || 'null'})" style="margin-left:10px;">削除</button>
      </div>
    `;
    els.favoritesList.appendChild(li);
  });
}

function clearHistory(){ STORE.history = []; saveStore(STORE.historyKey, []); renderHistory(); }

function escapeHtml(s){ return s.replace(/[&<>"]/g, c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;'}[c])); }
async function onPlaceSearch(){ /* 既存のまま */ }

// 2. この関数を JS の最後の方に追加してください
async function deleteFavorite(index, dbId) {
  if(!confirm('お気に入りから削除しますか？')) return;

  // DBからの削除処理（IDがある場合のみ）
  if(dbId && dbId !== null) {
    const params = new URLSearchParams();
    params.append('action', 'delete');
    params.append('id', dbId);
    try {
      await fetch('../saveRoute', { 
        method: 'POST', 
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString() 
      });
    } catch(e) { console.error("DB削除失敗", e); }
  }

  // 画面とLocalStorageからの削除
  STORE.favorites.splice(index, 1);
  saveStore(STORE.favoritesKey, STORE.favorites);
  renderFavorites();
}