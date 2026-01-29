<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ユーザーネームを変更</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage/change/username.css">
</head>
<body>
    <div class="container">
        <h1>ユーザーネームを変更</h1>

        <form action="<%= request.getContextPath() %>/UsernameChangeServlet" method="post">

            <!-- 現在のユーザーネーム -->
            <div class="form-group">
                <label>現在のユーザーネーム</label>
                <input type="text" name="currentUsername" value="${username}" readonly>
            </div>

            <!-- 新しいユーザーネーム -->
            <div class="form-group">
                <label>新しいユーザーネーム</label>
                <input type="text" name="newUsername"
                       placeholder="新しいユーザーネームを入力してください"
                       value="<c:out value='${newUsernameValue}'/>">

                <c:if test="${not empty error}">
                    <div class="error-message">${error}</div>
                </c:if>
            </div>

            <!-- ボタン -->
            <div class="button-area">
                <button type="submit" class="btn save">変更</button>
                <button type="button" class="btn cancel" onclick="history.back()">戻る</button>
            </div>
        </form>
    </div>
</body>
</html>
