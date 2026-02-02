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
                <button type="button" class="show-btn" onclick="togglePasswordIcon('current-password', this)">
        			<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#6c757d" class="bi bi-eye" viewBox="0 0 16 16">
            			<path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z"/>
            			<path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0"/>
        			</svg>
    			</button>
            </div>

            <!-- 新しいパスワード -->
            <label>新しいパスワード</label>
            <div class="password-wrapper">
                <input type="password" id="new-password" name="newPassword"
                       placeholder="新しいパスワードを入力">
                <button type="button" class="show-btn" onclick="togglePasswordIcon('new-password', this)">
        			<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#6c757d" class="bi bi-eye" viewBox="0 0 16 16">
            			<path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z"/>
            			<path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0"/>
        			</svg>
    			</button>
            </div>

            <!-- 確認用パスワード -->
            <label>新しいパスワードの確認</label>
            <div class="password-wrapper">
                <input type="password" id="confirm-password" name="confirmPassword"
                       placeholder="新しいパスワードの確認">
                <button type="button" class="show-btn" onclick="togglePasswordIcon('confirm-password', this)">
        			<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#6c757d" class="bi bi-eye" viewBox="0 0 16 16">
            			<path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z"/>
            			<path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0"/>
        			</svg>
    			</button>
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
	function togglePasswordIcon(inputId, btn) {
    	const input = document.getElementById(inputId);
    	const svg = btn.querySelector("svg");

    	if (input.type === "password") {
        	input.type = "text";
        	// 目に斜線のアイコンに変更
        	svg.innerHTML = `
        	<path d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7 7 0 0 0-2.79.588l.77.771A6 6 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755q-.247.248-.517.486z"/>
        	<path d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829zm-2.943 1.299.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829z"/>
        	<path d="M3.35 5.47q-.27.24-.518.487A13 13 0 0 0 1.172 8q.086.13.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7 7 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12z"/>`;
    	} else {
        	input.type = "password";
        	// 目アイコンに戻す
        	svg.innerHTML = `
        	<path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z"/>
        	<path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0"/>`;
    	}
	}
	</script>
    
</body>
</html>
