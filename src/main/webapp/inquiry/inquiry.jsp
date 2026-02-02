<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>お問い合わせ</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/inquiry.css">
</head>
<body>
    <div class="card">
        <h1>お問い合わせ</h1>

        <!-- 成功メッセージ -->
        <c:if test="${not empty successMessage}">
            <div class="success">${successMessage}</div>
        </c:if>

        <form action="<%= request.getContextPath() %>/inquiry" method="post">

            <!-- お問い合わせ項目 -->
            <div class="field">
                <label>お問い合わせ項目</label>
                <select name="select">
                    <option value="">選択してください</option>
                    <option value="bug" <c:if test="${param.select == 'bug'}">selected</c:if>>バグ報告</option>
                    <option value="function" <c:if test="${param.select == 'function'}">selected</c:if>>機能面の問題</option>
                    <option value="site" <c:if test="${param.select == 'site'}">selected</c:if>>サイトの不具合</option>
                    <option value="subscription" <c:if test="${param.select == 'subscription'}">selected</c:if>>サブスクリプションの問題</option>
                    <option value="accountAccess" <c:if test="${param.select == 'accountAccess'}">selected</c:if>>アカウントのアクセスに関する問題</option>
                    <option value="frozenAccount" <c:if test="${param.select == 'frozenAccount'}">selected</c:if>>凍結されたアカウントに関する問題</option>
                    <option value="postSpecificUsers" <c:if test="${param.select == 'postSpecificUsers'}">selected</c:if>>投稿機能/特定のユーザーに関する問題</option>
                    <option value="other" <c:if test="${param.select == 'other'}">selected</c:if>>その他</option>
                </select>
                <c:if test="${not empty errors['select']}">
                    <div class="error-message">${errors['select']}</div>
                </c:if>
            </div>

            <!-- 名前 -->
            <div class="field">
                <label>お名前</label>
                <input type="text" name="name" value="${param.name}">
                <c:if test="${not empty errors['name']}">
                    <div class="error-message">${errors['name']}</div>
                </c:if>
            </div>

            <!-- メールアドレス -->
            <div class="field">
                <label>メールアドレス</label>
                <input type="text" name="email" value="${param.email}">
                <c:if test="${not empty errors['email']}">
                    <div class="error-message">${errors['email']}</div>
                </c:if>
            </div>

            <!-- お問い合わせ内容 -->
            <div class="field">
                <label>お問い合わせ内容</label>
                <textarea name="detail">${param.detail}</textarea>
                <c:if test="${not empty errors['detail']}">
                    <div class="error-message">${errors['detail']}</div>
                </c:if>
            </div>

            <div class="controls">
                <button type="submit" class="primary">送信</button>
                <button type="reset" class="secondary"
                	onclick="location.href='<%= request.getContextPath() %>/profile'">戻る</button>
            </div>

        </form>
    </div>
</body>
</html>
