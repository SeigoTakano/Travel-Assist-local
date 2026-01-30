<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>パスワード変更</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage/change/password.css">
</head>
<body>
    <div class="container">
        <h1>パスワードを変更</h1>

        <form action="<%= request.getContextPath() %>/PasswordChangeServlet" method="post">

            <!-- 現在のパスワード -->
            <label>現在のパスワード</label>
            <div class="password-wrapper">
                <input type="password" id="current-password" name="currentPassword"
                       placeholder="現在のパスワードを入力">
                <button type="button" class="show-btn"
                        onclick="togglePassword('current-password', this)">表示</button>
            </div>

            <!-- 新しいパスワード -->
            <label>新しいパスワード</label>
            <div class="password-wrapper">
                <input type="password" id="new-password" name="newPassword"
                       placeholder="新しいパスワードを入力">
                <button type="button" class="show-btn"
                        onclick="togglePassword('new-password', this)">表示</button>
            </div>

            <!-- 確認用パスワード -->
            <label>新しいパスワードの確認</label>
            <div class="password-wrapper">
                <input type="password" id="confirm-password" name="confirmPassword"
                       placeholder="新しいパスワードの確認">
                <button type="button" class="show-btn"
                        onclick="togglePassword('confirm-password', this)">表示</button>
            </div>

            <!-- エラー表示 -->
            <c:if test="${not empty error}">
                <p class="condition">${error}</p>
            </c:if>

            <div class="button-area">
                <button type="submit" class="change-btn">変更</button>
                <button type="button" class="back-btn"
                	onclick="location.href='<%= request.getContextPath() %>/profile'">戻る</button>
            </div>
        </form>
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
