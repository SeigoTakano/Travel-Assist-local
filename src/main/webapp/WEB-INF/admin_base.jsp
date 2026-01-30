<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${pageTitle}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user_info_list.css">


</head>

<body>

<!-- ===== ヘッダー ===== -->
<header class="header">
	<form action="${pageContext.request.contextPath}/admin_menu/admin_menu.jsp" method="get">
    <button class="menu-btn">メニュー</button>
    </form>
    <h1 class="header-title">${pageTitle}</h1>
<span class="account-btn" id="accountBtn">👤</span>
</header>

<!-- ===== メイン ===== -->
<div class="container">

    <!-- サイドバー -->
    <aside class="sidebar">
        <ul>
            <li><a href="${pageContext.request.contextPath}/user_info_list/user_info_list.jsp">ユーザー一覧</a></li>
            <li><a href="#">お問い合わせ</a></li>
            <li><a href="${pageContext.request.contextPath}/post_manage/post_manage.jsp">投稿管理</a></li>
        </ul>
    </aside>

    <!-- 画面ごとのコンテンツ -->
    <main class="content">
        <jsp:include page="${param.contentPage}"  />
    </main>

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
<form action="LogoutServlet" method="post" class="logout">
<button>ログアウト</button>
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

 