<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>新規登録</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"> 
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
  </head>
  <body class="text-center">
    <main class="form-signin w-100 m-auto">
      <%-- サーバー側のサーブレット /register へPOST送信 --%>
      <form id="registerForm" action="${pageContext.request.contextPath}/register" method="post" novalidate>
        <h1 class="h3 mb-3 fw-normal">新規登録</h1>

        <%-- サーバー側からのエラー表示用 --%>
        <div id="serverError" class="error-message">${error}</div>

        <div class="form-floating">
          <input type="text" class="form-control" id="floatingName" name="username" placeholder="user123" required>
          <label for="floatingName">ユーザー名（ID）</label>
          <div class="error-message" id="nameError"></div>
        </div>

        <div class="form-floating">
          <input type="email" class="form-control" id="floatingEmail" name="email" placeholder="name@example.com" required>
          <label for="floatingEmail">メールアドレス</label>
          <div class="error-message" id="emailError"></div>
        </div>

        <div class="form-floating">
          <%-- DBがVARCHAR(10)のため、maxlengthを10に制限 --%>
          <input type="password" class="form-control" id="floatingPassword" name="password" placeholder="Password" required maxlength="10">
          <label for="floatingPassword">パスワード (最大10文字)</label>
          <div class="error-message" id="passwordError"></div>
        </div>

        <div class="form-floating">
          <input type="password" class="form-control" id="floatingPasswordConfirm" placeholder="PasswordConfirm" required maxlength="10">
          <label for="floatingPasswordConfirm">パスワード（確認用）</label>
          <div class="error-message" id="confirmError"></div>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit">登録</button>

        <div class="mt-3">
          <a href="login.jsp">ログイン画面に戻る</a>
        </div>
      </form>
    </main>

    <script src="${pageContext.request.contextPath}/js/register.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>