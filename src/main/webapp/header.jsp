<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">

<header class="top-header">
    <div class="hamburger" id="hamburger">
        <span></span>
        <span></span>
        <span></span>
    </div>

    <div class="system-name">✈ トラベルアシスト</div>
    
    <a href="#" class="icon">
        <img src="" style="width:30px; height:30px; background:#ddd; border-radius:50%;" />
    </a>
</header>

<nav class="side-menu" id="side-menu">
    <a href="${pageContext.request.contextPath}/index.jsp">プラン作成</a>
    <a href="${pageContext.request.contextPath}/route_search/route_search.jsp">ルート検索</a>
    <a href="${pageContext.request.contextPath}/transport/transport.jsp">交通機関検索</a>
    <a href="${pageContext.request.contextPath}/weather/weather.jsp">天気検索</a>
    <a href="${pageContext.request.contextPath}/record/record.jsp">旅の記録</a>
</nav>

<script src="${pageContext.request.contextPath}/js/header.js" defer></script>