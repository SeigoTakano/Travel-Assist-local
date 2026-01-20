<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- JSTL（cタグ）を使えるように宣言します。もしエラーが出る場合はライブラリの配置を確認してください --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ja" data-bs-theme="auto">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ログイン画面</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
  </head>

  <body class="text-center">
    <main class="form-signin w-100 m-auto">
      
      <%-- ✅ 新規登録成功時のメッセージ表示 --%>
      <c:if test="${param.success == 'registered'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          <strong>登録完了！</strong> ログインしてください。
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
      </c:if>

      <%-- ✅ ログイン失敗（ID/PW間違い）時のメッセージ表示 --%>
      <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">
          ${error}
        </div>
      </c:if>

      <form id="loginForm" action="${pageContext.request.contextPath}/login" method="post">
        <h1 class="h3 mb-4 fw-normal">ログイン</h1>

        <div class="form-floating mb-2">
          <input type="email" class="form-control" id="floatingInput" name="email" placeholder="name@example.com" required>
          <label for="floatingInput">メールアドレス</label>
        </div>

        <div class="form-floating mb-3">
          <input type="password" class="form-control" id="floatingPassword" name="password" placeholder="Password" required>
          <label for="floatingPassword">パスワード</label>
        </div>

        <div class="d-flex justify-content-between mb-3 small link-group">
          <a href="register.jsp" class="text-decoration-none">新規登録</a>
          <a href="forgot.jsp" class="text-decoration-none">パスワードをお忘れの方</a>
        </div>

        <div class="checkbox mb-3 text-start">
          <label>
            <input type="checkbox" name="rememberMe" value="true"> ログイン情報を記憶する
          </label>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit">ログイン</button>
      </form>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/login.js"></script>
  </body>
</html>