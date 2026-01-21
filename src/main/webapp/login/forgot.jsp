<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>パスワード再設定</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <%-- 階層が一つ深いので ../ でcssを参照 --%>
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">

    <style>
      .form-floating { margin-bottom: 15px; }
      .error-message { color: red; font-size: 0.9em; margin-top: -8px; margin-bottom: 10px; }
      .success-message { color: green; font-size: 0.95em; margin-top: 10px; }
    </style>
  </head>

  <body class="text-center">
    <main class="form-signin w-100 m-auto">
      <%-- サーバー側の ForgotPasswordServlet へPOST送信 --%>
      <form id="forgotForm" action="${pageContext.request.contextPath}/forgotPassword" method="post" novalidate>
        <h1 class="h3 mb-3 fw-normal">パスワード再設定</h1>

        <p class="mb-3 text-muted">登録済みのメールアドレスを入力してください。<br>
        パスワード変更画面へ進みます。</p>

        <%-- サーバーからの結果メッセージ表示エリア --%>
        <div class="error-message">${error}</div>
        <div class="success-message">${message}</div>

        <div class="form-floating">
          <input type="email" class="form-control" id="floatingEmail" name="email" placeholder="name@example.com" required>
          <label for="floatingEmail">メールアドレス</label>
          <div class="error-message" id="emailError"></div>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit">確認</button>

        <div class="mt-3">
          <%-- 同じフォルダ内なので直接指定 --%>
          <a href="login.jsp">ログイン画面に戻る</a>
        </div>

        <p class="mt-5 mb-3 text-body-secondary">&copy; 2026 Travel Assist</p>
      </form>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/forgot.js"></script>
  </body>
</html>