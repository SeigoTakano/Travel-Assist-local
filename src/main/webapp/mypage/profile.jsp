<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>マイページ</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage/profile.css">
</head>
<body>

<header class="header">
    <h1 class="site-title">マイページ</h1>
    <nav class="tabs">
		<a class="tab active" href="<%= request.getContextPath() %>/profile">プロフィール</a>
    	<a class="tab" href="<%= request.getContextPath() %>/records">旅の記録</a>
    	<a class="tab" href="<%= request.getContextPath() %>/subscription">サブスクリプション</a>
	</nav>
</header>

<!-- ヘッダー直下のハンバーガー -->
<div class="hamburger-container">
    <div class="hamburger" id="hamburger">
        <span></span>
        <span></span>
        <span></span>
    </div>
</div>

<!-- ナビゲーションメニュー -->
<nav class="side-nav" id="side-menu">
    <a class="tab" href="${pageContext.request.contextPath}/create_plan/create_plan.jsp">プラン作成</a>
    <a class="tab" href="${pageContext.request.contextPath}/route_search/route_search.jsp">ルート検索</a>
    <a class="tab" href="${pageContext.request.contextPath}/transport/transport.jsp">交通機関検索</a>
    <a class="tab" href="${pageContext.request.contextPath}/weather/weather.jsp">天気検索</a>
    <a class="tab" href="${pageContext.request.contextPath}/create_post" class="side-link">旅の記録</a>
    <a class="tab" href="${pageContext.request.contextPath}/menu.jsp">メニューへ</a>
    <a class="tab" href="<%= request.getContextPath() %>/inquiry">お問い合わせ</a>
    <a class="tab" href="<%= request.getContextPath() %>/logout">ログアウト</a>
</nav>

<main class="container">
    <section class="profile-section">
        <h2>プロフィール</h2>

        <!-- ユーザー名 -->
        <div class="form-group">
    		<span class="form-label">ユーザーネーム</span>
    		<span class="form-value">${username}</span>
    		<a href="<%= request.getContextPath() %>/mypage/change/username.jsp"
    			class="btn-primary">編集</a>
		</div>

        <!-- メールアドレス -->
        <div class="form-group">
    		<span class="form-label">メールアドレス</span>
    		<span class="form-value">${email}</span>
    		<a href="<%= request.getContextPath() %>/mypage/change/email.jsp"
    			class="btn-primary">編集</a>
		</div>

        <div class="form-group password-group">
    		<a href="<%= request.getContextPath() %>/mypage/change/password.jsp"
    			class="btn-password">パスワード変更</a>
		</div>
    </section>
    
    <script src="${pageContext.request.contextPath}/js/header.js" defer></script>
</main>
</body>
</html>
