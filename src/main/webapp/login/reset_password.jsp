<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>新しいパスワードの設定</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
  </head>

  <body class="text-center">
    <main class="form-signin w-100 m-auto">
      <form id="resetForm" action="${pageContext.request.contextPath}/resetPassword" method="post">
        <h1 class="h3 mb-3 fw-normal">パスワード再設定</h1>

        <p class="mb-3 text-muted">新しいパスワードを入力してください。</p>

        <%-- ✅ JSTLを使用して安全にメールアドレスを引き継ぐ --%>
        <input type="hidden" name="email" value="${param.email}">

        <%-- エラー表示 --%>
        <c:if test="${not empty error}">
            <div class="alert alert-danger p-2" style="font-size: 0.85rem;">${error}</div>
        </c:if>

        <div class="form-floating">
          <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="Password" required>
          <label for="newPassword">新しいパスワード</label>
        </div>

        <div class="form-floating">
          <input type="password" class="form-control" id="confirmPassword" placeholder="Confirm Password" required>
          <label for="confirmPassword">パスワード（確認）</label>
        </div>
        
        <%-- JSで制御するエラーメッセージ用 --%>
        <div id="jsError" class="text-danger small mb-3"></div>

        <button class="w-100 btn btn-lg btn-primary" type="submit">パスワードを更新する</button>

        <p class="mt-5 mb-3 text-body-secondary">&copy; 2026 Travel Assist</p>
      </form>
    </main>

    <script src="${pageContext.request.contextPath}/js/reset.js"></script>
  </body>
</html>