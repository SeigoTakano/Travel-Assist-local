<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!doctype html> 
<html lang="ja"> 
<head> 
  <meta charset="utf-8" /> 
  <meta name="viewport" content="width=device-width, initial-scale=1" /> 
  <title>マップ検索</title> 
  
  <%@ include file="../base.jsp" %>
  
  <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" /> 
 
  <link rel="stylesheet" href="../css/route_search.css"> 
 
</head> 

<body> 
 
  <div id="app">
    <header class="top-bar"> 
      <div class="btn-group-left"> 
        <button id="btn-favorites" class="btn pill icon-left" aria-expanded="false" aria-controls="favorites-panel"> 
          <span class="icon">★</span> 
          お気に入り 
        </button> 
        <button id="btn-history" class="btn pill icon-left" aria-expanded="false" aria-controls="history-panel"> 
          <span class="icon">🏷️</span> 
          履歴 
        </button> 
 
        <div id="favorites-panel" class="panel hidden" role="region" aria-label="お気に入り"> 
          <div class="panel-header"> 
            <span class="panel-title">お気に入り</span>
            <button id="add-favorite" class="mini-btn primary">＋ 現在の経路を保存</button> 
          </div> 
          <ul id="favorites-list" class="list"></ul> 
          <div class="panel-footer small">保存は端末のブラウザに保存されます</div> 
        </div> 
        <div id="history-panel" class="panel hidden" role="region" aria-label="履歴"> 
          <div class="panel-header"> 
            <span class="panel-title">履歴</span>
            <button id="clear-history" class="mini-btn danger">履歴をクリア</button> 
          </div> 
          <ul id="history-list" class="list"></ul> 
        </div> 
      </div> 
    </header> 
 
    <main class="layout"> 
      <section class="map-wrap card" aria-label="地図"> 
        <div id="map" class="map"></div> 
      </section> 
 
      <aside class="side card" aria-label="検索フォーム"> 
        <div class="mode-switch small"> 
          <label class="radio-label"><input type="radio" name="mode" id="mode-route"> ルート</label> 
          <label class="radio-label"><input type="radio" name="mode" id="mode-sight" checked> 観光地</label> 
        </div> 
        <div class="field"> 
          <label for="origin">出発:</label> 
          <input id="origin" type="text" placeholder="住所や駅名" class="text-input" /> 
        </div> 
        <div class="field"> 
          <label for="destination">到着:</label> 
          <input id="destination" type="text" placeholder="住所や駅名" class="text-input" /> 
        </div> 
        <div class="field" id="transport-field"> 
          <label for="transport-mode">交通手段:</label> 
          <select id="transport-mode" class="select-input"> 
            <option value="driving" selected>自動車</option> 
            <option value="cycling">自転車</option> 
            <option value="walking">徒歩</option> 
          </select> 
        </div> 
        <div class="field center" style="gap:12px"> 
          <button id="search" class="btn soft circle icon-left"> 
            ルート検索 
          </button> 
        </div> 
        
        <div class="field" id="sight-field" style="display:none"> 
          <label for="place-query">観光地を検索:</label> 
          <input id="place-query" type="text" placeholder="例: 浅草寺, 金閣寺" class="text-input" /> 
          <div class="center" style="margin-top:8px"> 
            <button id="place-search" class="btn soft circle icon-left"> 
              観光地検索
            </button> 
          </div> 
        </div> 
        
        <div class="results card inset" aria-label="検索結果"> 
          <div class="results-header small">結果の概要</div> 
          <div id="route-summary" class="results-body small">ルート検索結果が表示されます。</div> 
        </div> 
 
        <div class="poi card inset" aria-label="ルート沿いスポット"> 
          <div class="results-header small">ルート沿いのスポット</div> 
          <div class="poi-filters small"> 
            <label class="checkbox-label"><input type="checkbox" id="poi-sight" checked> 観光</label> 
            <label class="checkbox-label"><input type="checkbox" id="poi-food" checked> 食事</label> 
            <label class="checkbox-label"><input type="checkbox" id="poi-service" checked> SA/PA</label> 
            <span class="muted small">（ルート±約1km）</span> 
          </div> 
          <div id="poi-list" class="results-body small"></div> 
        </div> 
 
        <div class="poi card inset" aria-label="スポット詳細"> 
          <div class="results-header small">スポット詳細</div> 
          <div id="poi-detail" class="results-body small muted">スポットを選択すると詳細が表示されます。</div> 
          <div class="center" style="margin:8px 0 6px"> 
            <button id="poi-fav" class="mini-btn secondary icon-left">★ お気に入りに追加</button> 
          </div> 
        </div> 
      </aside> 
    </main> 
  </div> 
 
  <script 
    src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js" 
    integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=" 
    crossorigin="" 
  ></script> 
  <script src="https://unpkg.com/leaflet.markercluster@1.5.3/dist/leaflet.markercluster.js"></script> 
  <script src="../js/route_search.js"></script> 
</body> 
	   
</html>