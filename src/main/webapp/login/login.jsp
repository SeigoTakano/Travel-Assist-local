<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ログイン - トラベルアシスト</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
  </head>

  <body class="text-center">
    <main class="form-signin">
      <form id="loginForm" action="${pageContext.request.contextPath}/login" method="post">
        <h1 class="h3 mb-3 fw-normal">ログイン</h1>

        <%-- エラーメッセージ --%>
        <c:if test="${not empty error}">
          <div class="alert alert-danger p-2 mb-3" role="alert" style="font-size: 0.9rem;">
            ${error}
          </div>
        </c:if>

        <%-- メールアドレス --%>
        <div class="form-floating mb-2">
          <input type="email" class="form-control" id="floatingInput" name="email" placeholder="name@example.com" required>
          <label for="floatingInput">メールアドレス</label>
        </div>

        <%-- パスワード (表示切り替えボタン付き) --%>
        <div class="form-floating mb-3 position-relative">
          <input type="password" class="form-control" id="floatingPassword" name="password" placeholder="Password" style="padding-right: 3rem;" required>
          <label for="floatingPassword">パスワード</label>
          
          <%-- パスワード表示ボタン (z-indexで最前面に) --%>
          <button type="button" id="togglePassword" class="btn position-absolute top-50 end-0 translate-middle-y me-1" style="z-index: 100; border: none; background: transparent;">
            <svg id="eyeIcon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#6c757d" class="bi bi-eye" viewBox="0 0 16 16">
              <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z"/>
              <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0"/>
            </svg>
          </button>
        </div>

        <div class="link-group d-flex justify-content-between mb-3">
          <a href="register.jsp" class="text-decoration-none small">新規登録</a>
          <a href="forgot.jsp" class="text-decoration-none small">パスワードをお忘れの方</a>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit">ログイン</button>
        <p class="mt-5 mb-3 text-body-secondary">&copy; 2026 Travel Assist</p>
      </form>
    </main>

    <script src="${pageContext.request.contextPath}/js/login.js"></script>
  </body>
</html>