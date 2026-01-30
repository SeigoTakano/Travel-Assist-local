<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>メールアドレスを変更</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage/change/email.css">
</head>
<body>
    <div class="container">
        <h1>メールアドレスを変更</h1>

        <form action="<%= request.getContextPath() %>/EmailChangeServlet" method="post">
            
            <!-- 現在のメールアドレス -->
            <div class="form-group">
                <label>現在のメールアドレス</label>
                <input type="text" name="currentEmail" 
                       placeholder="現在のメールアドレスを入力してください"
                       value="${currentEmailValue}">
            </div>

            <!-- 新しいメールアドレス -->
            <div class="form-group">
                <label>新しいメールアドレス</label>
                <input type="text" name="newEmail" 
                       placeholder="新しいメールアドレスを入力してください"
                       value="${newEmailValue}">
            </div>

            <!-- エラーメッセージ -->
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <div class="button-area">
                <button type="submit" class="change-btn">変更</button>
                <button type="button" class="back-btn"
                        onclick="location.href='<%= request.getContextPath() %>/profile'">戻る</button>
            </div>
        </form>
    </div>
</body>
</html>
    