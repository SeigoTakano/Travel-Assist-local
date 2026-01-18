<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>メールアドレスを変更</title>
    <link rel="stylesheet" href="../../css/mypage/change/email.css">
</head>
<body>
    <div class="container">
    <form action="EmailChangeServlet" method="post">
    
        <h1>メールアドレスを変更</h1>

        <div class="form-group">
            <label>現在のメールアドレス</label>
            <input type="text" name="currentEmail" 
                   placeholder="メールアドレスを入力してください">
        </div>
        
        <div class="form-group">
            <label>新しいメールアドレス</label>
            <input type="text" name="newEmail" 
                   placeholder="新しいメールアドレスを入力してください">
        </div>

        <div class="button-area">
            <button type="submit" class="change-btn">変更</button>
            <button type="button" class="back-btn"
                    onclick="history.back()">戻る</button>
        </div>
    </div>
</body>
</html>
    