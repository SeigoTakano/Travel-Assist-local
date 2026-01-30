<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
if (request.getAttribute("userList") == null) {
    response.sendRedirect(request.getContextPath() + "/manager_info_manage");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者情報管理</title>
<link rel="stylesheet" type="text/css" href="/css/manager_info_manage.css">
</head>
<body>
<div class="header">

    管理者メニュー
<span class="account-btn" id="accountBtn">👤</span>
</div>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/manager_info_manage.css">

<div class="main">

<form action="${pageContext.request.contextPath}/manager_info_manage"
      method="get"
      class="search-box">
    <input type="text"
           name="keyword"
           placeholder="ユーザーID / メールアドレス / ユーザー名"
           value="${param.keyword}">
    <button type="submit">🔍</button>
</form>

<table class="user-table">
    <thead>
        <tr>
            <th>ID</th>
            <th>メールアドレス</th>
            <th>ユーザー名</th>
            <th>アクション</th>
        </tr>
    </thead>

    <tbody>
    <c:forEach var="u" items="${userList}">
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/user_detail?id=${u.id}">
                    ${u.id}
                </a>
            </td>

            <form action="manager_update" method="post" class="edit-form">
                <input type="hidden" name="id" value="${u.id}">

                <!-- メール -->
                <td>
                    <span class="view">${u.email}</span>
                    <input type="text" name="email"
                           class="edit"
                           value="${u.email}"
                           style="display:none;">
                </td>

                <!-- ユーザー名 -->
                <td>
                    <span class="view">${u.username}</span>
                    <input type="text" name="username"
                           class="edit"
                           value="${u.username}"
                           style="display:none;">
                </td>

                <!-- アクション -->
                <td>
                    <button type="button" class="edit-btn">編集</button>
                    <button type="submit" class="done-btn" style="display:none;">完了</button>
                    <button type="submit" formaction="user_delete">削除</button>
                </td>
            </form>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script>
document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll(".edit-btn").forEach(btn => {
        btn.addEventListener("click", () => {

            const form = btn.closest(".edit-form");

            form.querySelectorAll(".view").forEach(e => e.style.display = "none");
            form.querySelectorAll(".edit").forEach(e => e.style.display = "inline-block");

            btn.style.display = "none";
            form.querySelector(".done-btn").style.display = "inline-block";
        });
    });

});
</script>

<div class="right-box" id="adminInfoBox">
<p>管理者ID：1</p>
<p>管理者名：大原太郎</p>
<form action="AdminPasswordChangeServlet" method="post">
<p>パスワード</p>
<input type="password" name="password">
<br><br>
<button type="submit">パスワード変更</button>
</form>
<form action="LogoutServlet" method="post" class="logout">
<button>ログアウト</button>
</form>
</div>
<script>

    document.getElementById("accountBtn").onclick = function () {

        const box = document.getElementById("adminInfoBox");

        box.style.display = (box.style.display === "none" || box.style.display === "") 

                            ? "block" 

                            : "none";

    };
</script>

</div>
</body>
</html>

