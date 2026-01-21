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
        <button id="btn-favorites" class="btn pill icon-left"> 
          <span class="icon">★</span> お気に入り 
        </button> 
        <button id="btn-history" class="btn pill icon-left"> 
          <span class="icon">🏷️</span> 履歴 
        </button> 
 
        <div id="favorites-panel" class="panel hidden"> 
          <div class="panel-header"> 
            <span class="panel-title">お気に入り</span>
            <button id="add-favorite" class="mini-btn primary">＋ 現在の経路を保存</button> 
          </div> 
          <ul id="favorites-list" class="list"></ul> 
        </div> 

        <div id="history-panel" class="panel hidden"> 
          <div class="panel-header"> 
            <span class="panel-title">履歴</span>
            <button id="clear-history" class="mini-btn danger">履歴をクリア</button> 
          </div> 
          <ul id="history-list" class="list"></ul> 
        </div> 
      </div> 
    </header> 
 
    <main class="layout"> 
      <section class="map-wrap card"> 
        <div id="map" class="map"></div> 
      </section> 
 
      <aside class="side card"> 
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
        <div class="field center"> 
          <button id="search" class="btn soft circle">ルート検索</button> 
        </div> 
        
        <div class="field" id="sight-field" style="display:none"> 
          <label for="place-query">観光地を検索:</label> 
          <input id="place-query" type="text" placeholder="例: 浅草寺" class="text-input" /> 
          <div class="center" style="margin-top:8px"> 
            <button id="place-search" class="btn soft circle">観光地検索</button> 
          </div> 
        </div> 
        
        <div class="results card inset"> 
          <div class="results-header small">結果の概要</div> 
          <div id="route-summary" class="results-body small">ルート検索結果が表示されます。</div>
          <div class="center" style="padding: 10px 0;">
            <button id="add-fav-inline" class="mini-btn primary" style="width: 90%;">★ お気に入りに登録</button>
          </div>
        </div> 
 
        <div class="poi card inset"> 
          <div class="results-header small">ルート沿いのスポット</div> 
          <div class="poi-filters small"> 
            <label><input type="checkbox" id="poi-sight" checked> 観光</label> 
            <label><input type="checkbox" id="poi-food" checked> 食事</label> 
            <label><input type="checkbox" id="poi-service" checked> SA/PA</label> 
          </div> 
          <div id="poi-list" class="results-body small"></div> 
        </div> 
 
        <div class="poi card inset"> 
          <div class="results-header small">スポット詳細</div> 
          <div id="poi-detail" class="results-body small muted">スポットを選択してください。</div> 
        </div> 
      </aside> 
    </main> 
  </div> 
 
  <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script> 
  <script src="https://unpkg.com/leaflet.markercluster@1.5.3/dist/leaflet.markercluster.js"></script> 
  <script src="../js/route_search.js"></script> 
</body> 
</html>