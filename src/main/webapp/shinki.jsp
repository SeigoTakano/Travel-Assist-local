
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ja" data-bs-theme="auto">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>新規登録</title>
 
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
 
    <!-- 共通CSS -->
    <link href="login.css" rel="stylesheet">
 
    <style>
      /* 入力欄の間隔を調整 */
      .form-floating {
        margin-bottom: 15px;
      }
 
      /* エラーメッセージ */
      .error-message {
        color: red;
        font-size: 0.9em;
        margin-top: -8px;
        margin-bottom: 10px;
      }
    </style>
  </head>
 
  <body class="text-center">
    <main class="form-signin w-100 m-auto">
      <form id="registerForm" novalidate>
        <h1 class="h3 mb-3 fw-normal">新規登録</h1>
 
        <!-- ユーザーID -->
        <div class="form-floating">
          <input type="text" class="form-control" id="floatingID" name="floatingID" placeholder="user123">
          <label for="floatingID">ユーザーID</label>
          <div class="error-message" id="nameError"></div>
        </div>
 
        <!-- メールアドレス -->
        <div class="form-floating">
          <input type="email" class="form-control" id="floatingEmail" name="floatingEmail" placeholder="name@example.com">
          <label for="floatingEmail">メールアドレス</label>
          <div class="error-message" id="emailError"></div>
        </div>
 
        <!-- パスワード -->
        <div class="form-floating">
          <input type="password" class="form-control" id="floatingPassword" name="floatingPassword" placeholder="Password">
          <label for="floatingPassword">パスワード</label>
          <div class="error-message" id="passwordError"></div>
        </div>
 
        <!-- パスワード（確認用） -->
        <div class="form-floating">
          <input type="password" class="form-control" id="floatingPasswordConfirm" name="floatingPasswordConfirm" placeholder="PasswordConfirm">
          <label for="floatingPasswordConfirm">パスワード（確認用）</label>
          <div class="error-message" id="confirmError"></div>
        </div>
 
        <!-- 登録ボタン -->
        <button class="w-100 btn btn-lg btn-primary" type="submit">登録</button>
 
        <div class="mt-3">
          <a href="login.html">ログイン画面に戻る</a>
        </div>
      </form>
    </main>
 
    <script>
      document.getElementById("registerForm").addEventListener("submit", function (event) {
        event.preventDefault();
 
        const userId = document.getElementById("floatingName").value.trim();
        const email = document.getElementById("floatingEmail").value.trim();
        const password = document.getElementById("floatingPassword").value;
        const confirmPassword = document.getElementById("floatingPasswordConfirm").value;
 
        let isValid = true;
 
        // 既存IDリスト（例）
        const existingIDs = ["taro123", "user001", "test999"];
 
        // エラーメッセージ初期化
        document.getElementById("nameError").textContent = "";
        document.getElementById("emailError").textContent = "";
        document.getElementById("passwordError").textContent = "";
        document.getElementById("confirmError").textContent = "";
 
        // ✅ ユーザーIDチェック：英数字のみ
        if (!/^[A-Za-z0-9]+$/.test(userId)) {
          document.getElementById("nameError").textContent = "ユーザーIDは英字と数字のみ使用できます";
          isValid = false;
        } else if (existingIDs.includes(userId)) {
          document.getElementById("nameError").textContent = "このユーザーIDはすでに使用されています";
          isValid = false;
        }
 
        // ✅ メール形式チェック
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
          document.getElementById("emailError").textContent = "正しいメールアドレスの形式で入力してください";
          isValid = false;
        }
 
        // ✅ パスワードの複雑性チェック（8文字以上・英字・数字・記号を含む）
        if (!/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[\W_]).{8,}$/.test(password)) {
          document.getElementById("passwordError").textContent = "パスワードは8文字以上で英字・数字・記号を含めてください";
          isValid = false;
        }
 
        // ✅ パスワード一致チェック
        if (password !== confirmPassword) {
          document.getElementById("confirmError").textContent = "パスワードが一致しません。";
          isValid = false;
        }
 
        if (isValid) {
          alert("登録が完了しました！");
          // 実際はここでサーバー送信処理などを行う
        }
      });
    </script>
 
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
 
 