<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>投稿画面</title>

<!-- CSS読み込み -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/post.css">
</head>

<body>
<div class="container">

    <!-- 投稿 -->
    <div class="post-header">
        <div class="profile"></div>

        <div class="user-info">
            <strong>111111</strong>
            <span class="time">/2分前</span>
        </div>

        <button class="menu-btn" onclick="toggleMenu()">⋯</button>
    </div>

    <div class="post-text">
        ～～～～～～～～～～～～～～
    </div>

    <div class="post-image">
        <img src="<%= request.getContextPath() %>/img/sample.jpg" alt="花火">
    </div>

    <!-- メニュー -->
    <div id="popupMenu" class="popup-menu">
        <button>警告</button>
        <button>非表示</button>
        <button onclick="showDeleteDialog()">投稿削除</button>
    </div>

    <!-- 削除確認ダイアログ -->
    <div id="dialogOverlay" class="dialog-overlay">
        <div class="dialog">
            <div class="dialog-text">
                本当にこの投稿を削除しますか？
            </div>
            <div class="dialog-buttons">
                <button onclick="deletePost()">はい</button>
                <button onclick="closeDialog()">いいえ</button>
            </div>
        </div>
    </div>

    <!-- フッター -->
    <div class="footer">
        <button onclick="location.href='home.jsp'">HOME</button>
        <button onclick="location.href='search.jsp'">🔍</button>
        <button onclick="location.href='notice.jsp'">🔔</button>
    </div>

</div>

<script>
function toggleMenu() {
    const menu = document.getElementById("popupMenu");
    menu.style.display = (menu.style.display === "block") ? "none" : "block";
}

function showDeleteDialog() {
    document.getElementById("popupMenu").style.display = "none";
    document.getElementById("dialogOverlay").style.display = "block";
}

function closeDialog() {
    document.getElementById("dialogOverlay").style.display = "none";
}

function deletePost() {
    alert("削除処理（後でServletへ）");
}
</script>

</body>
</html>
