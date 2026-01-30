<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>お問い合わせ一覧 - 管理画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/inquiry_manage.css">
</head>
<body>
 
<header class="header">
    <button class="menu-btn" onclick="location.href='menu.jsp'">メニュー</button>
    <h1>お問い合わせ一覧</h1>
    <button class="user-btn">👤</button>
</header>
 
<div class="container">
    <aside class="sidebar">
        <ul>
            <li><button class="side-btn" onclick="location.href='user_list'">ユーザー情報一覧</button></li>
            <li class="active"><button class="side-btn">お問い合わせ</button></li>
            <li><button class="side-btn" onclick="location.href='post_list'">投稿一覧</button></li>
        </ul>
    </aside>
 
    <main class="main">
        <%-- 検索フォーム --%>
        <form action="inquiry_manage" method="get" class="search-area">
            <select class="w200" name="category">
                <option value="">全てのカテゴリ</option>
                <c:set var="cats" value="その他,バグ報告,機能面の問題,サイトの不具合,サブスクリプションに関する問題,アカウントアクセスに関する問題,凍結されたアカウントに関する問題,投稿機能/特定のユーザに関する問題" />
                <c:forEach var="c" items="${cats.split(',')}">
                    <option value="${c}" ${selectedCategory == c ? 'selected' : ''}>${c}</option>
                </c:forEach>
            </select>
            <input type="text" name="keyword" value="${keyword}" id="searchInput" class="w500" placeholder="名前、メールアドレス、内容で検索">
            <button type="submit" class="w100">検索</button>
        </form>
 
        <div class="list">
            <c:choose>
                <c:when test="${not empty inquiryList}">
                    <c:forEach var="inq" items="${inquiryList}">
                        <div class="card">
                            <div class="card-header">
                                <span class="name copy-target"><strong>${inq.name}</strong> 様</span>
                                <span class="mail copy-target">${inq.email}</span>
                                <span class="time"><fmt:formatDate value="${inq.createdAt}" pattern="yyyy/MM/dd HH:mm" /></span>
                            </div>
                            <div class="card-body">
                                <textarea class="inquiry" readonly>${inq.message}</textarea>
                                <div class="card-side">
                                    <label class="small">ステータス</label>
                                    <select class="status-select">
                                        <option value="未確認" ${inq.status == '未確認' ? 'selected' : ''}>未確認</option>
                                        <option value="対応中" ${inq.status == '対応中' ? 'selected' : ''}>対応中</option>
                                        <option value="対応済" ${inq.status == '対応済' ? 'selected' : ''}>対応済</option>
                                    </select>
                                    <button class="reply-btn" data-mail="${inq.email}">返信</button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info">該当するデータがありません。</div>
                </c:otherwise>
            </c:choose>
        </div>
        <div id="pagination"></div>
    </main>
</div>
 
<div id="toast" style="display:none;">コピーしました。</div>
<script src="${pageContext.request.contextPath}/js/inquiry_manage.js"></script>
</body>
</html>
Oracle Java Technologies | Oracle
Java can help reduce costs, drive innovation, & improve application services; the #1 programming language for IoT, enterprise architecture, and cloud computing.
 