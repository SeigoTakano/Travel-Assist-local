<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>メニュー - トラベルアシスト</title>
    
    <%-- base.jsp で header.jsp や共通CSS/JSが読み込まれている前提 --%>
    <%@ include file="../base.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">
</head>
<body>

    <%-- 
       注意：base.jsp 内で既に <header> や <nav> が出力されている場合は、
       ここではいきなり <main> から書き始めてOKです。
    --%>

    <main class="menu-container">
        <div class="top-row">
            
            <section class="plan-section">
                <h2 class="section-title">これまでに作ったプラン</h2>
                <div class="plan-box">
                    <c:choose>
                        <c:when test="${empty planList}">
                            <p class="no-plan-text">作成済みプランがありません</p>
                        </c:when>
                        <c:otherwise>
                            <div class="plan-list">
                                <c:forEach var="plan" items="${planList}">
                                    <div class="plan-item">${plan.name}</div>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>

            <section class="history-section">
                <h2 class="section-title">最近の検索</h2>
                <div class="history-box">
                    <c:choose>
                        <c:when test="${empty recentSpots}">
                            <p class="no-history-text">なし</p>
                        </c:when>
                        <c:otherwise>
                            <ul class="history-mini-list">
                                <c:forEach var="route" items="${recentSpots}">
                                    <li>📍 ${route.endName}</li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>
        </div>

        <section class="function-grid">
            <a href="${pageContext.request.contextPath}/weather/weather.jsp" class="func-btn">☀️ 天気検索</a>
            <a href="${pageContext.request.contextPath}/transport/transport.jsp" class="func-btn">🚆 交通機関検索</a>
            <a href="${pageContext.request.contextPath}/record/record.jsp" class="func-btn">📔 旅の記録</a>
            <a href="${pageContext.request.contextPath}/route_search/route_search.jsp" class="func-btn">🗺️ ルート検索</a>
        </section>

        <section class="main-action">
            <a href="${pageContext.request.contextPath}/create_plan/create_plan.jsp" class="create-plan-btn">プラン作成</a>
        </section>
    </main>

</body>
</html>