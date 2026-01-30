<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user_info_list.css">

<div class="main">

<form action="${pageContext.request.contextPath}/user_info_list"
      method="get"
      class="search-box">
    <input type="text"
           name="keyword"
           placeholder="ユーザーID / メールアドレス / ユーザー名"
           value="${param.keyword}">
    <button type="submit">🔍</button>
</form>


    <!-- ユーザー一覧 -->
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

        <c:choose>
            <c:when test="${empty userList}">
                <tr>
                    <td colspan="4" class="no-data">
                        該当するユーザー情報がありません
                    </td>
                </tr>
            </c:when>

            <c:otherwise>
                <c:forEach var="u" items="${userList}">
                    <tr>
                    	<td>
						  <a href="${pageContext.request.contextPath}/user_detail?id=${u.id}">
						    ${u.id}
						  </a>
						</td>
                        <td>${u.email}</td>
                        <td>${u.username}</td>
	                    <td>
						  <form action="${pageContext.request.contextPath}/user_delete" method="post"
						        onsubmit="return confirm('このユーザーを削除しますか？');">
						    <input type="hidden" name="id" value="${u.id}">
						    <button type="submit">削除</button>
						  </form>
						</td>
                       
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        </tbody>
    </table>

</div>
