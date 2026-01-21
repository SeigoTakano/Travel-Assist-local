<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザー一覧</title>
<link rel="stylesheet" href="user_info_list_detil.css">
</head>
<body>

<!-- ヘッダー -->
<header class="header">
  <h1>ユーザー一覧</h1>
  <div class="user-icon">👤</div>
</header>

<!-- 検索エリア -->
<div class="search-area">
  <input type="text" id="searchInput" placeholder="ユーザーID・メール検索">
  <button id="searchBtn">🔍</button>
</div>

<!-- ユーザー一覧 -->
<section class="table-area">
  <table>
    <thead>
      <tr>
        <th>ユーザーID</th>
        <th>メールアドレス</th>
        <th>名前</th>
        <th>アクション</th>
      </tr>
    </thead>
    <tbody id="userTableBody">
      <%-- JSで動的に生成 --%>
    </tbody>
  </table>

  <div id="noUserMessage" class="empty-message">
    データがありません
  </div>
</section>

<!-- 投稿一覧 -->
<section class="post-area">
  <h2>ユーザーの投稿</h2>
  <div id="postContent" class="empty-message">
    投稿がありません
  </div>
</section>

<script src="user_info_list_detil.js"></script>
</body>
</html>
