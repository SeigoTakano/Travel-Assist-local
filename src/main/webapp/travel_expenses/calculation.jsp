<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>旅費計算 | ジャンル別</title>

  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/calculation.css" />

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;600;700&display=swap" rel="stylesheet">

  <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>
</head>
<body>
  <header class="topbar">
    <div class="brand">旅費計算</div>
    <div class="avatar"></div>
  </header>

  <div class="layout">
    <aside class="sidebar">
      <div class="trip-meta card">
        <div class="date" id="today"></div>

        <label class="stack">
          <span>旅行タイトル</span>
          <input id="tripTitle" type="text" placeholder="1泊2日の旅" />
        </label>

        <label class="stack">
          <span>人数</span>
          <input id="people" type="number" min="1" value="2" />
        </label>

        <label class="stack">
          <span>予算（任意）</span>
          <input id="budget" type="number" min="0" placeholder="¥" />
        </label>

        <div class="stack">
          <span>端数処理</span>
          <div class="rounding-options">
            <label class="radio-option">
              <input type="radio" name="rounding" value="none" checked>
              <span>切り捨て</span>
            </label>
            <label class="radio-option">
              <input type="radio" name="rounding" value="roundup">
              <span>切り上げ</span>
            </label>
            <label class="radio-option">
              <input type="radio" name="rounding" value="10yen">
              <span>10円単位に切り上げ</span>
            </label>
          </div>
        </div>
      </div>

      <div class="card chart-card">
        <canvas id="donut"></canvas>
        <div class="legend" id="legend"></div>
      </div>

      <div class="totals card">
        <div class="row">
          <span>合計</span>
          <strong id="grandTotal">¥0</strong>
        </div>
        <div class="row">
          <span>1人あたり</span>
          <strong id="perPerson">¥0</strong>
        </div>
      </div>
    </aside>

    <main class="content">
      <div class="actions">
        <button id="clearAll" class="ghost">リセット</button>
        <button id="addCategory">ジャンル追加</button>
      </div>

      <section class="category" data-key="transport">
        <header>
          <h2>交通費</h2>
          <div class="subtotal">
            <strong id="subtotal-transport">¥0</strong>
            <span>/ 人 <span id="pp-transport">¥0</span></span>
          </div>
          <button class="icon del del-cat" data-key="transport">削除</button>
        </header>
        <div class="items" id="items-transport"></div>
      </section>

      <section class="category" data-key="food">
        <header>
          <h2>食費</h2>
          <div class="subtotal">
            <strong id="subtotal-food">¥0</strong>
            <span>/ 人 <span id="pp-food">¥0</span></span>
          </div>
          <button class="icon del del-cat" data-key="food">削除</button>
        </header>
        <div class="items" id="items-food"></div>
      </section>

      <section class="category" data-key="lodging">
        <header>
          <h2>宿泊費</h2>
          <div class="subtotal">
            <strong id="subtotal-lodging">¥0</strong>
            <span>/ 人 <span id="pp-lodging">¥0</span></span>
          </div>
          <button class="icon del del-cat" data-key="lodging">削除</button>
        </header>
        <div class="items" id="items-lodging"></div>
      </section>

      <div class="savebar">
        <button id="saveData">データ保存</button>
      </div>
    </main>
  </div>

  <template id="item-row-tpl">
    <div class="item-row">
      <input type="text" class="memo" placeholder="メモ">
      <input type="text" class="amount" placeholder="0">
      <button class="icon del">✕</button>
    </div>
  </template>

  <script src="<%= request.getContextPath() %>/js/app.js"></script>
</body>
</html>
