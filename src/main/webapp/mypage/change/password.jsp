<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>パスワード変更</title>
    <link rel="stylesheet" href="../../css/mypage/change/password.css">
</head>
<body>
    <div class="container">
    <form action="PasswordChangeServlet" method="post">
    
        <h1>パスワードを変更</h1>
        
        <label>現在のパスワード</label>
        <div class="password-wrapper">
            <input type="password" id="current-password" name="current-password"
                   placeholder="現在のパスワードを入力">
            <button type="button" class="show-btn"
                    onclick="togglePassword('current-password', this)">表示</button>
        </div>
        
        <label>新しいパスワード</label>
        <div class="password-wrapper">
            <input type="password" id="new-password" name="new-password"
                   placeholder="新しいパスワードを入力">
            <button type="button" class="show-btn"
                    onclick="togglePassword('new-password', this)">表示</button>
        </div>

        <c:if test="${showPasswordError}">
    		<p class="condition">※英数字・記号をすべて含めてください</p>
    		<p class="condition">※8文字以上で設定してください</p>
		</c:if>
        
        
        <label>新しいパスワード（確認）</label>
        <div class="password-wrapper">
            <input type="password" id="confirm-password" name="confirm-password"
                   placeholder="新しいパスワードの確認">
            <button type="button" class="show-btn"
                    onclick="togglePassword('confirm-password', this)">表示</button>
        </div>

        <div class="button-area">
            <button type="submit" class="change-btn">変更</button>
            <button type="button" class="back-btn"
                    onclick="history.back()">戻る</button>
        </div>
    </div>

    <script>
        function togglePassword(inputId, btn) {
            const input = document.getElementById(inputId);
            if (input.type === "password") {
                input.type = "text";
                btn.textContent = "非表示";
            } else {
                input.type = "password";
                btn.textContent = "表示";
            }
        }
    </script>
</body>
</html>
