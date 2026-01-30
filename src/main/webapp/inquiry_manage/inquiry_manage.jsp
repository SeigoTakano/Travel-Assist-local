<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>お問い合わせ一覧</title>
<link rel="stylesheet"
    href="<%= request.getContextPath() %>/css/inquiry_manage.css">
</head>
<body>

<header class="header">
    <button class="menu-btn" id="menuBtn">メニュー</button>
    <h1>お問い合わせ一覧</h1>
    <button class="user-btn" id="userBtn" aria-label="ユーザーメニュー">👤</button>
</header>

<div class="container">

    <aside class="sidebar">
        <ul>
            <li>
                <button class="side-btn" data-target="user">ユーザー情報一覧</button>
            </li>
            <li class="active">
                <button class="side-btn" data-target="inquiry">お問い合わせ</button>
            </li>
            <li>
                <button class="side-btn" data-target="post">投稿一覧</button>
            </li>
        </ul>
    </aside>

    <main class="main">

        <div class="search-area">
            <select class="w200">
                <option>その他</option>
                <option>バグ報告</option>
                <option>機能面の問題</option>
                <option>サイトの不具合</option>
                <option>サブスクリプションの問題</option>
                <option>アカウントアクセスに関する問題</option>
                <option>凍結されたアカウントに関する問題</option>
                <option>投稿機能に関する問題</option>
            </select>
            <input type="text" class="w500" placeholder="ワード検索">
            <button class="w100">検索</button>
        </div>

        <div class="list">

            <c:forEach var="inq" items="${inquiryList}">
                <div class="card">
                    <div class="card-header">
                        <span class="name copy-target">${inq.name}様</span>
                        <span class="mail copy-target">${inq.email}</span>
                        <span class="time">${inq.createdAt}</span>
                    </div>

                    <div class="card-body">
                        <textarea class="inquiry" readonly>${inq.message}</textarea>

                        <div class="card-side">
                            <select>
                                <option ${inq.status == '未確認' ? 'selected' : ''}>未確認</option>
                                <option ${inq.status == '対応中' ? 'selected' : ''}>対応中</option>
                                <option ${inq.status == '対応済' ? 'selected' : ''}>対応済</option>
                            </select>

                            <button class="reply-btn" data-mail="${inq.email}">返信</button>

                            <div class="edit">
                                <div>最終編集日</div>
                                <div>${inq.updatedAt}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>

        <div class="pagination" id="pagination"></div>

    </main>
</div>

<div id="toast">コピーしました。</div>

<script src="<%= request.getContextPath() %>/js/inquiry_manage.js"></script>
</body>
</html>
