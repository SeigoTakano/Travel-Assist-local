/*
  Leaflet + OSM stack (no API key)
  - Geocoding: Nominatim
  - Routing: OSRM demo server
  - Favorites/History: localStorage
*/

let map;
let routeLayer;
let cluster; // MarkerClusterGroup for POIs
let lastRouteCoords = null; // [[lat,lng], ...]
let allPois = []; // cached raw pois
let originMarker = null;
let destMarker = null;

const els = {};

function qs(id){ return document.getElementById(id); }

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

// Initialize Leaflet map when DOM is ready
document.addEventListener('DOMContentLoaded', initLeaflet);

function initLeaflet(){
  els.origin = qs('origin');
  els.destination = qs('destination');
  els.search = qs('search');
  els.routeSummary = qs('route-summary');
  els.btnFavorites = qs('btn-favorites');
  els.btnHistory = qs('btn-history');
  els.favoritesPanel = qs('favorites-panel');
  els.historyPanel = qs('history-panel');
  els.addFavorite = qs('add-favorite');
  els.clearHistory = qs('clear-history');
  els.favoritesList = qs('favorites-list');
  els.historyList = qs('history-list');
  els.addFavInline = qs('add-fav-inline');
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

  const tokyo = [35.6762, 139.6503];
  // Basemaps
  const osm = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 19, attribution: '&copy; OpenStreetMap contributors' });
  const sat = L.tileLayer('https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}', { maxZoom: 19, attribution: 'Tiles &copy; Esri' });
  map = L.map('map', { zoomControl: true, layers: [osm] }).setView(tokyo, 12);
  const baseLayers = { '標準': osm, '衛星写真': sat };
  cluster = L.markerClusterGroup();
  const overlays = { 'スポット': cluster };
  L.control.layers(baseLayers, overlays).addTo(map);
  L.control.scale({ metric: true, imperial: false }).addTo(map);
  map.addLayer(cluster);

  // Geolocate control
  const locate = L.control({ position: 'topleft' });
  locate.onAdd = function(){
    const btn = L.DomUtil.create('button', 'leaflet-bar');
    btn.title = '現在地へ移動';
    btn.textContent = '◎';
    btn.style.width = '32px'; btn.style.height = '32px'; btn.style.cursor='pointer';
    L.DomEvent.on(btn, 'click', (e)=>{ L.DomEvent.stop(e); geolocate(); });
    return btn;
  };
  locate.addTo(map);

  // Load storage
  STORE.favorites = loadStore(STORE.favoritesKey, []);
  STORE.history = loadStore(STORE.historyKey, []);
  renderFavorites();
  renderHistory();

  // Wire UI
  els.search.addEventListener('click', onSearch);
  [els.origin, els.destination].forEach(i => i.addEventListener('keydown', (e)=>{ if(e.key==='Enter'){ onSearch(); }}));

  els.btnFavorites.addEventListener('click', ()=>togglePanel('favorites'));
  els.btnHistory.addEventListener('click', ()=>togglePanel('history'));
  document.addEventListener('click', (e)=>{
    const withinFav = e.target.closest('#favorites-panel') || e.target.closest('#btn-favorites');
    const withinHist = e.target.closest('#history-panel') || e.target.closest('#btn-history');
    if(!withinFav) hidePanel('favorites');
    if(!withinHist) hidePanel('history');
  });

  els.addFavorite.addEventListener('click', addCurrentToFavorites);
  els.clearHistory.addEventListener('click', clearHistory);
  ;['change','click'].forEach(ev=>{
    els.poiSight?.addEventListener(ev, renderPois);
    els.poiFood?.addEventListener(ev, renderPois);
    els.poiService?.addEventListener(ev, renderPois);
  });

  // Mode switch
  els.modeRoute?.addEventListener('change', onModeChange);
  els.modeSight?.addEventListener('change', onModeChange);
  els.placeSearch?.addEventListener('click', onPlaceSearch);
  els.transportMode?.addEventListener('change', ()=>{
    // re-run routing if possible
    const origin = els.origin.value.trim();
    const destination = els.destination.value.trim();
    if(origin && destination){ onSearch(); }
  });
  els.addFavInline?.addEventListener('click', addCurrentToFavorites);

  // set initial visibility according to current mode
  onModeChange();
}

function togglePanel(which){
  if(which==='favorites'){
    els.favoritesPanel.classList.toggle('hidden');
  } else if(which==='history'){
    els.historyPanel.classList.toggle('hidden');
  }
}
function hidePanel(which){
  if(which==='favorites') els.favoritesPanel.classList.add('hidden');
  if(which==='history') els.historyPanel.classList.add('hidden');
}

async function onSearch(){
  const origin = els.origin.value.trim();
  const destination = els.destination.value.trim();
  if(!origin || !destination){
    showSummary('出発と到着を入力してください。');
    return;
  }
  try{
    const [o, d] = await Promise.all([ geocode(origin), geocode(destination) ]);
    if(!o || !d){
      showSummary('位置情報を取得できませんでした。住所を見直してください。');
      return;
    }
    const route = await routeOsrm(o, d);
    if(!route){
      showSummary('経路を取得できませんでした。');
      return;
    }

    drawRoute(route.geometry.coordinates);
    lastRouteCoords = route.geometry.coordinates.map(([lon,lat])=>[lat,lon]);
    setDraggableMarkers(o, d);
    await loadPoisAlongRoute();

    const distKm = (route.distance/1000).toFixed(1);
    const mins = Math.round(route.duration/60);
    const text = [
      `出発: ${origin}`,
      `到着: ${destination}`,
      `距離: ${distKm} km`,
      `時間: 約 ${mins} 分`,
    ].join('\n');
    showSummary(text);

    pushHistory({ origin, destination, when: Date.now() });
  }catch(err){
    console.error(err);
    showSummary('経路を取得できませんでした。ネットワーク状況をご確認ください。');
  }
}

function showSummary(text){
  els.routeSummary.textContent = text;
}

function onModeChange(){
  const routeOn = !!els.modeRoute?.checked;
  const originField = els.origin?.closest('.field');
  const destField = els.destination?.closest('.field');
  const searchField = els.search?.closest('.field');
  if(originField) originField.style.display = routeOn ? '' : 'none';
  if(destField) destField.style.display = routeOn ? '' : 'none';
  if(searchField) searchField.style.display = routeOn ? '' : 'none';
  if(els.sightField) els.sightField.style.display = routeOn ? 'none' : '';
  const transportField = els.transportMode?.closest('.field');
  if(transportField) transportField.style.display = routeOn ? '' : 'none';
  const favInline = els.addFavInline?.closest('.field');
  if(favInline) favInline.style.display = routeOn ? '' : 'none';
}

async function geocode(query){
  const url = new URL('https://nominatim.openstreetmap.org/search');
  url.searchParams.set('format','jsonv2');
  url.searchParams.set('q', query);
  url.searchParams.set('accept-language','ja');
  const res = await fetch(url.toString(), {
    headers: { 'Accept': 'application/json' }
  });
  if(!res.ok) return null;
  const data = await res.json();
  if(!data || data.length === 0) return null;
  const { lat, lon } = data[0];
  return { lat: parseFloat(lat), lng: parseFloat(lon) };
}

async function placeSearchNominatim(query){
  const url = new URL('https://nominatim.openstreetmap.org/search');
  url.searchParams.set('format','jsonv2');
  url.searchParams.set('q', query);
  url.searchParams.set('accept-language','ja');
  url.searchParams.set('limit','5');
  const res = await fetch(url.toString(), { headers: { 'Accept': 'application/json' } });
  if(!res.ok) return [];
  const arr = await res.json();
  return (arr||[]).map(x=>({
    lat: parseFloat(x.lat),
    lng: parseFloat(x.lon),
    name: x.display_name || '(名称未設定)',
    class: x.class,
    type: x.type
  }));
}

async function reverseGeocode(latlng){
  try{
    const url = new URL('https://nominatim.openstreetmap.org/reverse');
    url.searchParams.set('format','jsonv2');
    url.searchParams.set('lat', String(latlng.lat));
    url.searchParams.set('lon', String(latlng.lng));
    url.searchParams.set('accept-language','ja');
    const res = await fetch(url.toString(), { headers: { 'Accept': 'application/json' } });
    if(!res.ok) return '';
    const data = await res.json();
    return data.display_name || '';
  }catch{ return ''; }
}

function currentProfile(){
  const v = els.transportMode?.value || 'driving';
  if(v==='cycling') return 'cycling';
  if(v==='walking') return 'walking';
  return 'driving';
}

async function routeOsrm(o, d){
  // OSRM expects lon,lat order
  const profile = currentProfile();
  const url = new URL(`https://router.project-osrm.org/route/v1/${profile}/${o.lng},${o.lat};${d.lng},${d.lat}`);
  url.searchParams.set('overview','full');
  url.searchParams.set('geometries','geojson');
  const res = await fetch(url.toString());
  if(!res.ok) return null;
  const data = await res.json();
  if(!data.routes || data.routes.length===0) return null;
  return data.routes[0];
}

function drawRoute(coords){
  // coords is [[lon,lat], ...]
  const latlngs = coords.map(([lon,lat]) => [lat,lon]);
  if(routeLayer){
    map.removeLayer(routeLayer);
  }
  routeLayer = L.polyline(latlngs, { color: '#2563eb', weight: 5, opacity: 0.85 }).addTo(map);
  map.fitBounds(routeLayer.getBounds(), { padding: [30,30] });
  // clear poi markers on new route
  clearPoiMarkers();
  allPois = [];
  if(els.poiList) els.poiList.innerHTML = '';
}

function setDraggableMarkers(o, d){
  const createOrMove = (marker, latlng, label) => {
    if(marker){ marker.setLatLng([latlng.lat, latlng.lng]); return marker; }
    const m = L.marker([latlng.lat, latlng.lng], { draggable: true }).addTo(map);
    m.bindTooltip(label, {permanent:false});
    m.on('dragend', async ()=>{
      const pos = m.getLatLng();
      const addr = await reverseGeocode({ lat: pos.lat, lng: pos.lng });
      if(label==='出発'){ els.origin.value = addr || `${pos.lat.toFixed(5)},${pos.lng.toFixed(5)}`; }
      if(label==='到着'){ els.destination.value = addr || `${pos.lat.toFixed(5)},${pos.lng.toFixed(5)}`; }
      // recompute route from dragged positions
      const oPos = originMarker.getLatLng();
      const dPos = destMarker.getLatLng();
      const route = await routeOsrm({lat:oPos.lat,lng:oPos.lng}, {lat:dPos.lat,lng:dPos.lng});
      if(route){
        drawRoute(route.geometry.coordinates);
        lastRouteCoords = route.geometry.coordinates.map(([lon,lat])=>[lat,lon]);
        await loadPoisAlongRoute();
      }
    });
    return m;
  };
  originMarker = createOrMove(originMarker, o, '出発');
  destMarker = createOrMove(destMarker, d, '到着');
}

function geolocate(){
  if(!navigator.geolocation){
    alert('このブラウザは位置情報に対応していません');
    return;
  }
  navigator.geolocation.getCurrentPosition(async (pos)=>{
    const { latitude, longitude } = pos.coords;
    map.setView([latitude, longitude], 14);
    const o = { lat: latitude, lng: longitude };
    originMarker = originMarker || L.marker([latitude, longitude], { draggable:true }).addTo(map);
    originMarker.setLatLng([latitude, longitude]);
    originMarker.bindTooltip('出発');
    const addr = await reverseGeocode(o);
    els.origin.value = addr || `${latitude.toFixed(5)},${longitude.toFixed(5)}`;

    // if destination exists, recompute route
    let d = null;
    if(destMarker){
      const p = destMarker.getLatLng();
      d = { lat: p.lat, lng: p.lng };
    } else if(els.destination.value.trim()){
      d = await geocode(els.destination.value.trim());
    }
    if(d){
      const route = await routeOsrm(o, d);
      if(route){
        drawRoute(route.geometry.coordinates);
        lastRouteCoords = route.geometry.coordinates.map(([lon,lat])=>[lat,lon]);
        setDraggableMarkers(o, d);
        await loadPoisAlongRoute();
      }
    }
  }, (err)=>{
    console.warn('Geolocation error', err);
  }, { enableHighAccuracy: true, timeout: 10000, maximumAge: 30000 });
}

async function loadPoisAlongRoute(){
  if(!lastRouteCoords || !routeLayer) return;
  // Use map bounds around the route with small padding
  const b = routeLayer.getBounds();
  const pad = 0.02; // ~2km padding
  const south = b.getSouth() - pad;
  const west = b.getWest() - pad;
  const north = b.getNorth() + pad;
  const east = b.getEast() + pad;

  try{
    const data = await overpassFetch({south, west, north, east});
    allPois = data;
    renderPois();
  }catch(e){
    console.error('POI fetch failed', e);
    if(els.poiList) els.poiList.textContent = 'スポットの取得に失敗しました。時間をおいて再度お試しください。';
  }
}

async function overpassFetch(bbox){
  // Fetch POIs as nodes + ways + relations (with centers) to catch buildings and areas
  // Categories:
  //  - 観光: tourism=*, historic=*, leisure=park
  //  - 食事: amenity=restaurant|cafe|fast_food|food_court, shop=supermarket|convenience
  //  - SA/PA: highway=services|rest_area, amenity=fuel|charging_station
  const {south, west, north, east} = bbox;
  const q = `
    [out:json][timeout:25];
    (
      node["tourism"]["tourism"!~"hotel"](${south},${west},${north},${east});
      way["tourism"]["tourism"!~"hotel"](${south},${west},${north},${east});
      relation["tourism"]["tourism"!~"hotel"](${south},${west},${north},${east});

      node["historic"](${south},${west},${north},${east});
      way["historic"](${south},${west},${north},${east});
      relation["historic"](${south},${west},${north},${east});

      node["leisure"="park"](${south},${west},${north},${east});
      way["leisure"="park"](${south},${west},${north},${east});

      node["amenity"~"^(restaurant|cafe|fast_food|food_court)$"](${south},${west},${north},${east});
      way["amenity"~"^(restaurant|cafe|fast_food|food_court)$"](${south},${west},${north},${east});
      relation["amenity"~"^(restaurant|cafe|fast_food|food_court)$"](${south},${west},${north},${east});

      node["shop"~"^(supermarket|convenience)$"](${south},${west},${north},${east});
      way["shop"~"^(supermarket|convenience)$"](${south},${west},${north},${east});

      node["highway"~"^(services|rest_area)$"](${south},${west},${north},${east});
      way["highway"~"^(services|rest_area)$"](${south},${west},${north},${east});
      relation["highway"~"^(services|rest_area)$"](${south},${west},${north},${east});

      node["amenity"~"^(fuel|charging_station)$"](${south},${west},${north},${east});
      way["amenity"~"^(fuel|charging_station)$"](${south},${west},${north},${east});
      relation["amenity"~"^(fuel|charging_station)$"](${south},${west},${north},${east});
    );
    out center tags;
  `;
  const res = await fetch('https://overpass-api.de/api/interpreter', {
    method: 'POST',
    headers: { 'Content-Type': 'text/plain;charset=UTF-8' },
    body: q
  });
  if(!res.ok) throw new Error('Overpass error');
  const json = await res.json();
  const elements = json.elements || [];
  return elements.map(el=>{
    let lat, lng;
    if(el.type === 'node'){
      lat = el.lat; lng = el.lon;
    }else if(el.center){
      lat = el.center.lat; lng = el.center.lon;
    }
    if(typeof lat !== 'number' || typeof lng !== 'number') return null;
    return {
      id: `${el.type}/${el.id}`,
      lat, lng,
      name: el.tags?.name || '(名称未設定)',
      tags: el.tags || {}
    };
  }).filter(Boolean);
}

function renderPois(){
  if(!els.poiList) return;
  clearPoiMarkers();

  const filters = {
    sight: !!els.poiSight?.checked,
    food: !!els.poiFood?.checked,
    service: !!els.poiService?.checked
  };

  const route = lastRouteCoords; // [[lat,lng]]
  if(!route) return;

  // Filter by category and distance to route within ~1000m
  const withinM = 1000;
  const filtered = allPois.filter(p=>{
    const cat = poiCategory(p.tags);
    if(cat==='sight' && !filters.sight) return false;
    if(cat==='food' && !filters.food) return false;
    if(cat==='service' && !filters.service) return false;
    const d = distancePointToPolylineMeters([p.lat,p.lng], route);
    return d <= withinM;
  });

  if(filtered.length===0){
    els.poiList.textContent = '該当スポットはありません。フィルタや検索条件を見直してください。';
    return;
  }
  els.poiList.innerHTML = '';

  filtered.slice(0,300).forEach(p=>{
    const marker = L.marker([p.lat,p.lng]);
    marker.bindPopup(`${escapeHtml(p.name)}`);
    marker._poi = p;
    marker.on('click', ()=>{ showPoiDetail(p); });
    cluster.addLayer(marker);

    const item = document.createElement('div');
    item.className = 'poi-item';
    const cat = poiCategory(p.tags);
    const label = catLabel(cat);
    item.textContent = `${label}  ${p.name}`;
    item.style.cursor = 'pointer';
    item.addEventListener('click', ()=>{
      map.setView([p.lat,p.lng], Math.max(map.getZoom(), 14));
      marker.openPopup();
      showPoiDetail(p);
    });
    els.poiList.appendChild(item);
  });
}

function poiCategory(tags){
  const t = tags || {};
  if(t.tourism && !/hotel/.test(t.tourism)) return 'sight';
  if(t.historic || t.leisure === 'park') return 'sight';
  if(t.amenity && /^(restaurant|cafe|fast_food|food_court)$/.test(t.amenity)) return 'food';
  if(t.shop && /^(supermarket|convenience)$/.test(t.shop)) return 'food';
  if((t.highway && /^(services|rest_area)$/.test(t.highway)) || (t.amenity && /^(fuel|charging_station)$/.test(t.amenity))) return 'service';
  return 'other';
}

function catLabel(cat){
  if(cat==='sight') return '観光';
  if(cat==='food') return '食事';
  if(cat==='service') return 'SA/PA・燃料';
  return 'その他';
}

function clearPoiMarkers(){
  try{ cluster?.clearLayers(); }catch{}
}

function showPoiDetail(p){
  if(!els.poiDetail) return;
  const tags = p.tags || {};
  const lines = [];
  lines.push(`名称: ${escapeHtml(p.name || '(名称未設定)')}`);
  const cat = poiCategory(tags);
  if(cat && cat!=='other') lines.push(`カテゴリ: ${escapeHtml(catLabel(cat))}`);
  if(tags['opening_hours']) lines.push(`営業時間: ${escapeHtml(tags['opening_hours'])}`);
  if(tags['website']) lines.push(`サイト: ${escapeHtml(tags['website'])}`);
  if(tags['phone']) lines.push(`電話: ${escapeHtml(tags['phone'])}`);
  if(tags['addr:full']) lines.push(`住所: ${escapeHtml(tags['addr:full'])}`);
  els.poiDetail.classList.remove('muted');
  els.poiDetail.textContent = lines.join('\n');
}

async function onPlaceSearch(){
  const q = els.placeQuery?.value.trim();
  if(!q) return;
  try{
    const results = await placeSearchNominatim(q);
    if(!results.length){
      els.poiDetail.classList.remove('muted');
      els.poiDetail.textContent = '観光地が見つかりませんでした。キーワードを見直してください。';
      return;
    }
    const r = results[0];
    const marker = L.marker([r.lat, r.lng]).addTo(map);
    marker.bindPopup(`${escapeHtml(r.name)}`).openPopup();
    map.setView([r.lat, r.lng], 15);
    showPoiDetail({ name: r.name, lat: r.lat, lng: r.lng, tags: { class: r.class, type: r.type } });
  }catch(e){
    console.error(e);
  }
}

// Distance utilities
function distancePointToPolylineMeters(pointLatLng, polylineLatLngs){
  let min = Infinity;
  for(let i=0;i<polylineLatLngs.length-1;i++){
    const a = polylineLatLngs[i];
    const b = polylineLatLngs[i+1];
    const d = distancePointToSegmentMeters(pointLatLng, a, b);
    if(d < min) min = d;
  }
  return min;
}

function distancePointToSegmentMeters(p, a, b){
  // Equirectangular approximation
  const latRad = Math.PI/180;
  const x = (lng)=> lng * Math.cos(((a[0]+b[0])/2) * latRad);
  const ax = x(a[1]), ay = a[0];
  const bx = x(b[1]), by = b[0];
  const px = x(p[1]), py = p[0];
  const vx = bx - ax, vy = by - ay;
  const wx = px - ax, wy = py - ay;
  const c1 = vx*wx + vy*wy;
  const c2 = vx*vx + vy*vy;
  let t = c2 ? (c1 / c2) : 0;
  t = Math.max(0, Math.min(1, t));
  const cx = ax + t*vx, cy = ay + t*vy;
  const metersPerDegLat = 111320;
  const metersPerDegLng = metersPerDegLat * Math.cos(((a[0]+b[0])/2) * latRad);
  const dx = (px - cx) * metersPerDegLng;
  const dy = (py - cy) * metersPerDegLat;
  return Math.hypot(dx, dy);
}

function pushHistory(item){
  const last = STORE.history[0];
  if(!last || last.origin !== item.origin || last.destination !== item.destination){
    STORE.history.unshift(item);
    if(STORE.history.length > 20) STORE.history.length = 20;
    saveStore(STORE.historyKey, STORE.history);
    renderHistory();
  }
}

function renderHistory(){
  els.historyList.innerHTML = '';
  if(STORE.history.length === 0){
    const li = document.createElement('li');
    li.textContent = '履歴はありません';
    els.historyList.appendChild(li);
    return;
  }
  STORE.history.forEach((h, idx)=>{
    const li = document.createElement('li');
    const row = document.createElement('div');
    row.className = 'row';
    row.innerHTML = `<strong>${escapeHtml(h.origin)}</strong> → <strong>${escapeHtml(h.destination)}</strong>`;
    const actions = document.createElement('div');
    const useBtn = document.createElement('button');
    useBtn.textContent = '使用';
    useBtn.addEventListener('click', ()=>{
      els.origin.value = h.origin;
      els.destination.value = h.destination;
      onSearch();
    });
    actions.appendChild(useBtn);
    li.appendChild(row);
    li.appendChild(actions);
    els.historyList.appendChild(li);
  });
}

function clearHistory(){
  STORE.history = [];
  saveStore(STORE.historyKey, STORE.history);
  renderHistory();
}

function addCurrentToFavorites(){
  const origin = els.origin.value.trim();
  const destination = els.destination.value.trim();
  if(!origin || !destination){
    alert('出発と到着を入力してください。');
    return;
  }
  if(STORE.favorites.some(f => f.origin===origin && f.destination===destination)){
    alert('すでにお気に入りに登録されています。');
    return;
  }
  STORE.favorites.push({ origin, destination, saved: Date.now() });
  saveStore(STORE.favoritesKey, STORE.favorites);
  renderFavorites();
}

function renderFavorites(){
  els.favoritesList.innerHTML = '';
  if(STORE.favorites.length === 0){
    const li = document.createElement('li');
    li.textContent = 'お気に入りはありません';
    els.favoritesList.appendChild(li);
    return;
  }
  STORE.favorites.forEach((f, idx)=>{
    const li = document.createElement('li');
    const row = document.createElement('div');
    row.className = 'row';
    row.innerHTML = `<strong>${escapeHtml(f.origin)}</strong> → <strong>${escapeHtml(f.destination)}</strong>`;

    const actions = document.createElement('div');
    const useBtn = document.createElement('button');
    useBtn.textContent = '使用';
    useBtn.addEventListener('click', ()=>{
      els.origin.value = f.origin;
      els.destination.value = f.destination;
      onSearch();
    });
    const delBtn = document.createElement('button');
    delBtn.textContent = '削除';
    delBtn.addEventListener('click', ()=>{
      STORE.favorites.splice(idx,1);
      saveStore(STORE.favoritesKey, STORE.favorites);
      renderFavorites();
    });

    actions.appendChild(useBtn);
    actions.appendChild(delBtn);
    li.appendChild(row);
    li.appendChild(actions);
    els.favoritesList.appendChild(li);
  });
}

function escapeHtml(s){
  return s.replace(/[&<>"]/g, c=>({
    '&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;'
  })[c]);
}
