<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>旅の記録</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage/records.css">

    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>

<header class="header">
    <h1 class="site-title">マイページ</h1>
    <nav class="tabs">
        <a class="tab" href="<%= request.getContextPath() %>/profile">プロフィール</a>
        <a class="tab active" href="<%= request.getContextPath() %>/records">旅の記録</a>
        <a class="tab" href="<%= request.getContextPath() %>/subscription">サブスクリプション</a>
    </nav>
</header>

<div class="hamburger-container">
    <div class="hamburger" id="hamburger">
        <span></span>
        <span></span>
        <span></span>
    </div>
</div>

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

<main class="page-wrapper">

    <h2>保存された旅の記録</h2>

    <!-- データあり -->
    <div id="post-feed" class="post-grid">
    	<%-- JSで挿入 --%>
    </div>

</main>

<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>

<script src="${pageContext.request.contextPath}/js/records.js"></script>
<script src="${pageContext.request.contextPath}/js/header.js" defer></script>

</body>
</html>

