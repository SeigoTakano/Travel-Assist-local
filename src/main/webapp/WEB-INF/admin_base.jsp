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
	<form action="${pageContext.request.contextPath}/admin/menu" method="get">
    <button class="menu-btn">メニュー</button>
    </form>
    <h1 class="header-title">${pageTitle}</h1>
    <div class="user-icon">👤</div>
</header>

<!-- ===== メイン ===== -->
<div class="container">

    <!-- サイドバー -->
    <aside class="sidebar">
        <ul>
            <li><a href="${pageContext.request.contextPath}/user/list">ユーザー一覧</a></li>
            <li><a href="#">お問い合わせ</a></li>
            <li><a href="#">投稿管理</a></li>
        </ul>
    </aside>

    <!-- 画面ごとのコンテンツ -->
    <main class="content">
        <jsp:include page="${param.contentPage}"  />
    </main>

</div>

</body>
</html>

 