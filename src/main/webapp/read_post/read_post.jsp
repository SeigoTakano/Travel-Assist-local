<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>旅の記録 - 投稿一覧</title>
    <%@ include file="../base.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/read_post.css">
    <script src="https://unpkg.com/lucide@latest"></script>
    <script>
        const contextPath = "${pageContext.request.contextPath}";
        // ★セッションからログイン中のユーザー名を取得（JS変数として保持）
        const loginUser = "<%= session.getAttribute("username") %>";
    </script>
</head>
<body>
    <div id="app">
        <main class="feed-container">
            <div class="search-section">
                <div class="search-input-outer">
                    <i data-lucide="search" class="search-icon-inner"></i>
                    <input type="text" id="search-input" placeholder="キーワードで検索...">
                </div>
            </div>

            <div id="post-feed" class="post-grid">
                <%-- JSで挿入 --%>
            </div>
        </main>

        <button id="fab" class="fab" title="新規投稿">
            <i data-lucide="plus"></i>
        </button>
    </div>

    <div id="zoom-overlay" class="zoom-overlay"></div>

    <script src="${pageContext.request.contextPath}/js/read_post.js"></script>
    <script>lucide.createIcons();</script>
</body>
</html>