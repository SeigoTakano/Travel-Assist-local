<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ユーザー名を変更</title>
	<link rel="stylesheet" href="../../css/mypage/change/username.css">
</head>
<body>
	<div class="container">
    <form action="UsernameChangeServlet" method="post">
    
    	<h1>ユーザーネームを変更</h1>
        
        <div class="form-group">
            <label>現在のユーザーネーム</label>
            <input type="text" name="currentUsername"
            		value="${username}" readonly>
        </div>

        <div class="form-group">
            <label>新しいユーザーネーム</label>
            <input type="text" name="newUsername" 
                   placeholder="新しいユーザーネームを入力してください">
                   
            <c:if test="${not empty error}">
            	<p class="note">${error}</p>
            </c:if>
        </div>

        <div class="button-area">
        	<button type="submit" class="btn save">変更</button>
            <button type="button" class="btn cancel"
                    onclick="history.back()">戻る</button>
        </div>
    </form>
</div>

</body>
</html>