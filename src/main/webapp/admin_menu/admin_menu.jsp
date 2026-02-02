<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者メニュー</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin_menu.css">
</head>
<body>
<div class="header">

    管理者メニュー
<span class="account-btn" id="accountBtn">👤</span>
</div>
<div class="box">
<p>現在のアクセス数</p>
<input type="text" value="200" readonly> 人
</div>
<div class="menu-buttons">
<form action="${pageContext.request.contextPath}/inquiry_manage" method="get">
<button type="submit">お問い合わせ内容</button>
</form>
<form action="../user_info_list/user_info_list.jsp" method="get">
<button type="submit">ユーザー情報一覧</button>
</form>
<form action="../post_manage/post_manage.jsp" method="get">
<button>投稿管理</button>
</form>
</div>
<div class="right-box" id="adminInfoBox">
<p>管理者ID：1</p>
<p>管理者名：大原太郎</p>
<form action="AdminPasswordChangeServlet" method="post">
<p>パスワード</p>
<input type="password" name="password">
<br><br>
<button type="submit">パスワード変更</button>
</form>
<form action="<%= request.getContextPath() %>/logout" method="post" class="logout">
    <button type="submit">ログアウト</button>
</form>
</div>
<script>

    document.getElementById("accountBtn").onclick = function () {

        const box = document.getElementById("adminInfoBox");

        box.style.display = (box.style.display === "none" || box.style.display === "") 

                            ? "block" 

                            : "none";

    };
</script>
</body>
</html>
 