<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<head>
  <meta charset="UTF-8" />
  <title>サブスクリプション</title>

  <!-- Google Fonts -->
  <link rel="preconnect" href="https://fonts.googleapis.com" />
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;600;700;800;900&display=swap" rel="stylesheet" />

  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage/subscription.css" />
</head>

<body>
  <header class="site-header">
    <div class="container">
      <h1 class="site-title">マイページ</h1>
      <nav class="tabs">
        <a class="tab" href="<%= request.getContextPath() %>/profile">プロフィール</a>
        <a class="tab" href="<%= request.getContextPath() %>/records">旅の記録</a>
        <a class="tab active" href="<%= request.getContextPath() %>/subscription">サブスクリプション</a>
      </nav>
    </div>
  </header>

  <main class="container pricing">
    <h2 class="pricing-title">もっと投稿を楽しく！</h2>

    <section class="cards">
      <!-- FREE -->
      <article class="card">
        <div class="card-head">
          <div class="plan-name free">FREE</div>
          <div class="price"><span class="yen">¥</span>0<span class="per">/月</span></div>
          <button class="btn btn-ghost" disabled>現在のプラン</button>
        </div>
        <ul class="features">
          <li class="muted lock">無制限で投稿</li>
          <li class="muted lock">投稿の削除</li>
          <li class="dim">投稿の文字数<span>300文字/1回</span></li>
          <li class="dim">投稿の写真数<span class="right">4枚/1回</span></li>
          <li class="dim">自動作成回数<span class="right">20回/1日</span></li>
          <li class="muted lock">投稿の編集</li>
          <li class="muted lock">無制限の投稿編集</li>
          <li class="muted lock">最新の機能</li>
        </ul>
      </article>

      <!-- GOLD -->
      <article class="card gold featured">
        <div class="badge">一番人気！1日約16円！</div>
        <div class="card-head">
          <div class="plan-name gold">GOLD</div>
          <div class="price"><span class="yen">¥</span>500<span class="per">/月</span></div>
          <button class="btn btn-gold">GOLDにアップグレード</button>
        </div>
        <ul class="features">
          <li class="muted lock">無制限で投稿</li>
          <li class="check">投稿の削除</li>
          <li class="dim">投稿の文字数<span>1500文字/1回</span></li>
          <li class="dim">投稿の写真数<span class="right">8枚/1回</span></li>
          <li class="dim">自動作成回数<span class="right">40回/1日</span></li>
          <li class="dim">投稿の編集回数<span>10回/1日</span></li>
          <li class="muted lock">無制限の投稿編集</li>
          <li class="muted lock">最新の機能</li>
        </ul>
      </article>

      <!-- PREMIUM -->
      <article class="card premium">
        <div class="card-head">
          <div class="plan-name premium">PREMIUM</div>
          <div class="price"><span class="yen">¥</span>2,000<span class="per">/月</span></div>
          <button class="btn btn-premium">PREMIUMにアップグレード</button>
        </div>
        <ul class="features">
          <li class="check">無制限で投稿</li>
          <li class="check">投稿の削除</li>
          <li class="dim">投稿の文字数<span>3000文字/1回</span></li>
          <li class="dim">投稿の写真数<span class="right">20枚/1回</span></li>
          <li class="dim">自動作成回数<span class="right">100回/1日</span></li>
          <li class="check">無制限の投稿編集</li>
          <li class="check">最新の機能</li>
        </ul>
      </article>
    </section>
  </main>
</body>
</html>
