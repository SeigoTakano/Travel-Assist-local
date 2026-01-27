<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">

<header class="top-header">
    <div class="hamburger" id="hamburger">
        <span></span>
        <span></span>
        <span></span>
    </div>

    <div class="system-name">✈ トラベルアシスト</div>
    
    <a href="<%= request.getContextPath() %>/profile" class="icon">
        <span class="user-name">${username} さん</span>
        <img src="${pageContext.request.contextPath}/images/profile/profile.png" alt="プロフィール" />
    </a>
</header>

<nav class="side-menu" id="side-menu">
    <a href="${pageContext.request.contextPath}/create_plan/create_plan.jsp" class="side-link">プラン作成</a>
    <a href="${pageContext.request.contextPath}/route_search/route_search.jsp" class="side-link">ルート検索</a>
    <a href="${pageContext.request.contextPath}/transport/transport.jsp" class="side-link">交通機関検索</a>
    <a href="${pageContext.request.contextPath}/weather/weather.jsp" class="side-link">天気検索</a>
    <a href="${pageContext.request.contextPath}/record/record.jsp" class="side-link">旅の記録</a>

    <div class="side-menu-footer">
        <a href="${pageContext.request.contextPath}/menu/menu.jsp" class="btn-sidebar home">メニューへ戻る</a>
        <a href="${pageContext.request.contextPath}/logout" class="btn-sidebar logout">ログアウト</a>
    </div>
</nav>

<script src="${pageContext.request.contextPath}/js/header.js" defer></script>