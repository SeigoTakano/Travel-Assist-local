<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tomcat 10+ では uri は jakarta.tags.core を使用します --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>メニュー - トラベルアシスト</title>
    
    <%-- 共通CSS/JS読み込み --%>
    <%@ include file="../base.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">
    
    <style>
        /* 簡易的なスタイル調整：リンクを押しやすくする */
        .history-link {
            text-decoration: none;
            color: #333;
            display: block;
            padding: 5px;
            border-radius: 4px;
            transition: background 0.2s;
        }
        .history-link:hover {
            background-color: #f0f4ff;
            color: #2563eb;
        }
    </style>
</head>
<body>

    <main class="menu-container">
        <div class="top-row">
            
            <%-- プランセクション --%>
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

            <%-- 履歴セクション --%>
            <section class="history-section">
                <h2 class="section-title">最近の検索</h2>
                <div class="history-box">
                    <c:choose>
                        <c:when test="${empty recentSpots}">
                            <p class="no-history-text">履歴はありません</p>
                        </c:when>
                        <c:otherwise>
                            <ul class="history-mini-list" style="list-style: none; padding: 0;">
                                <c:forEach var="route" items="${recentSpots}">
                                    <li style="margin-bottom: 8px;">
                                        <%-- 地図画面へパラメータ（出発地・目的地）を渡すリンク --%>
                                        <a href="${pageContext.request.contextPath}/route_search/route_search.jsp?start=${route.startName}&dest=${route.endName}" class="history-link">
                                            📍 ${route.endName}
                                            <small style="display:block; color: #888; margin-left: 20px;">
                                                from: ${route.startName}
                                            </small>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>
        </div>

        <%-- 機能ボタン一覧 --%>
        <section class="function-grid">
            <a href="${pageContext.request.contextPath}/weather" class="func-btn">天気検索</a>
            <a href="${pageContext.request.contextPath}/transport" class="func-btn">交通機関検索</a>
            <a href="${pageContext.request.contextPath}/read_post" class="func-btn">旅の記録</a>
            <a href="${pageContext.request.contextPath}/route_search" class="func-btn">ルート検索</a>
        </section>

        <section class="main-action">
            <a href="${pageContext.request.contextPath}/create_plan" class="create-plan-btn">プラン作成</a>
        </section>
    </main>

</body>
</html>