let map;
let routeLayer;
let lastRouteCoords = null; 

const els = {};
function qs(id){ return document.getElementById(id); }

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
  els.addFavorite = qs('add-favorite');
  els.addFavInline = qs('add-fav-inline');
  els.favoritesList = qs('favorites-list');
  els.historyList = qs('history-list');
  els.transportMode = qs('transport-mode');
  
  // モード切替用
  const modeRoute = qs('mode-route');
  const modeSight = qs('mode-sight');
  const routeFields = qs('route-fields');
  const sightField = qs('sight-field');

  // Map Setup
  map = L.map('map').setView([35.6762, 139.6503], 12);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 19 }).addTo(map);

  // モード切替イベント
  const onModeChange = () => {
    if (modeRoute.checked) {
      routeFields.style.display = 'block';
      sightField.style.display = 'none';
    } else {
      routeFields.style.display = 'none';
      sightField.style.display = 'block';
    }
  };
  modeRoute.addEventListener('change', onModeChange);
  modeSight.addEventListener('change', onModeChange);

  // 検索ボタンイベント
  els.search.addEventListener('click', onSearch);
  qs('place-search').addEventListener('click', onPlaceSearch);
  
  // パネル切替
  els.btnFavorites.addEventListener('click', () => {
    els.favoritesPanel.classList.toggle('hidden');
    if(!els.favoritesPanel.classList.contains('hidden')) loadList('favorites');
  });
  els.btnHistory.addEventListener('click', () => {
    els.historyPanel.classList.toggle('hidden');
    if(!els.historyPanel.classList.contains('hidden')) loadList('history');
  });

  els.addFavorite?.addEventListener('click', () => addCurrentToDb(true));
  els.addFavInline?.addEventListener('click', () => addCurrentToDb(true));
}

// 観光地検索
async function onPlaceSearch() {
  const q = qs('place-query').value.trim();
  if (!q) return;
  try {
    const res = await fetch(`https://nominatim.openstreetmap.org/search?format=jsonv2&q=${encodeURIComponent(q)}&accept-language=ja`);
    const data = await res.json();
    if (data.length > 0) {
      const latlng = [parseFloat(data[0].lat), parseFloat(data[0].lon)];
      map.setView(latlng, 15);
      L.marker(latlng).addTo(map).bindPopup(data[0].display_name).openPopup();
    }
  } catch (e) { console.error(e); }
}

// 保存処理
async function addCurrentToDb(isFavorite) {
  const start = els.origin.value.trim();
  const end = els.destination.value.trim();
  if (!start || !end || !lastRouteCoords) return;

  try {
    const params = new URLSearchParams();
    params.append('start', start);
    params.append('end', end);
    params.append('polyline', JSON.stringify(lastRouteCoords));
    params.append('isFavorite', isFavorite);

    const response = await fetch(`${contextPath}/route_search`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: params.toString()
    });
    if (response.ok && isFavorite) loadList('favorites');
  } catch (e) { console.error(e); }
}

// 削除処理
window.deleteEntry = async function(id, type) {
  if (!confirm('削除しますか？')) return;
  try {
    const params = new URLSearchParams();
    params.append('action', 'delete');
    params.append('id', id);
    const response = await fetch(`${contextPath}/route_search`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: params.toString()
    });
    if (response.ok) loadList(type);
  } catch (e) { console.error(e); }
};

// リスト読み込み
async function loadList(type) {
  const action = type === 'favorites' ? 'fav_list' : 'list';
  const response = await fetch(`${contextPath}/route_search?action=${action}`);
  if (!response.ok) return;
  const data = await response.json();
  const listEl = type === 'favorites' ? els.favoritesList : els.historyList;
  listEl.innerHTML = data.map(item => `
    <li class="list-item">
      <span>${item.start_name} → ${item.end_name}</span>
      <button class="mini-btn danger" onclick="deleteEntry(${item.id}, '${type}')">削除</button>
    </li>
  `).join('') || '<li>なし</li>';
}

// ルート検索
async function onSearch() {
  const origin = els.origin.value.trim();
  const destination = els.destination.value.trim();
  if (!origin || !destination) return;
  try {
    const [o, d] = await Promise.all([geocode(origin), geocode(destination)]);
    const profile = els.transportMode.value;
    const res = await fetch(`https://router.project-osrm.org/route/v1/${profile}/${o.lng},${o.lat};${d.lng},${d.lat}?overview=full&geometries=geojson`);
    const route = (await res.json()).routes[0];
    
    const latlngs = route.geometry.coordinates.map(([lon, lat]) => [lat, lon]);
    if (routeLayer) map.removeLayer(routeLayer);
    routeLayer = L.polyline(latlngs, { color: '#2563eb', weight: 5 }).addTo(map);
    map.fitBounds(routeLayer.getBounds());
    
    lastRouteCoords = latlngs;
    els.routeSummary.textContent = `距離: ${(route.distance/1000).toFixed(1)} km`;
    addCurrentToDb(false); // 自動履歴保存
  } catch (e) { console.error(e); }
}

async function geocode(q) {
  const res = await fetch(`https://nominatim.openstreetmap.org/search?format=jsonv2&q=${encodeURIComponent(q)}&accept-language=ja`);
  const data = await res.json();
  return data.length ? { lat: parseFloat(data[0].lat), lng: parseFloat(data[0].lon) } : null;
}