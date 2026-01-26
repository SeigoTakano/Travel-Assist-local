<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ログイン - トラベルアシスト</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
    
  </head>

  <body class="text-center">
    <main class="form-signin">
      
      <form id="loginForm" action="${pageContext.request.contextPath}/login" method="post">
        <h1 class="h3">ログイン</h1>

        <%-- ✅ エラーメッセージ --%>
        <c:if test="${not empty error}">
          <div class="alert alert-danger p-2 mb-3" role="alert" style="font-size: 0.9rem;">
            ${error}
          </div>
        </c:if>

        <%-- メールアドレス --%>
        <div class="form-floating custom-spacing">
          <input type="email" class="form-control" id="floatingInput" name="email" placeholder="name@example.com" required>
          <label for="floatingInput">メールアドレス</label>
        </div>

        <%-- パスワード --%>
        <div class="form-floating custom-spacing">
          <input type="password" class="form-control" id="floatingPassword" name="password" placeholder="Password" required>
          <label for="floatingPassword">パスワード</label>
        </div>

        <%-- リンク集 --%>
        <div class="link-group d-flex justify-content-between">
          <a href="register.jsp">新規登録</a>
          <a href="forgot.jsp">パスワードをお忘れの方</a>
        </div>

        <%-- ログインボタン --%>
        <button class="w-100 btn btn-lg btn-primary" type="submit">ログイン</button>
      </form>
    </main>

    <script src="${pageContext.request.contextPath}/js/login.js"></script>
  </body>
</html>