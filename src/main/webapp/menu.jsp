<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>メニュー - トラベルアシスト</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">
    
      <%@ include file="../base.jsp" %>
</head>
<body>
 
    <main class="menu-container">
        <section class="plan-section">
            <h2 class="section-title">これまでに作ったプラン</h2>
            <div class="plan-box">
                <c:choose>
                    <%-- プランがない場合 --%>
                    <c:when test="${empty planList}">
                        <p class="no-plan-text">作成済みプランがありません</p>
                    </c:when>
                    <%-- プランがある場合（後ほど実装） --%>
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
 
        <section class="function-grid">
            <a href="${pageContext.request.contextPath}/weather/weather.jsp" class="func-btn">
                <span class="icon"></span> 天気検索
            </a>
            <a href="${pageContext.request.contextPath}/transport/transport.jsp" class="func-btn">
                <span class="icon"></span> 交通機関検索
            </a>
            <a href="${pageContext.request.contextPath}/record/record.jsp" class="func-btn">
                <span class="icon"></span> 旅の記録
            </a>
            <a href="${pageContext.request.contextPath}/route_search/route_search.jsp" class="func-btn">
                <span class="icon"></span> ルート検索
            </a>
        </section>
 
        <section class="main-action">
            <a href="${pageContext.request.contextPath}/plan/create.jsp" class="create-plan-btn">
                プラン作成
            </a>
        </section>
    </main>
 
</body>
</html>