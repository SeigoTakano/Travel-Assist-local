<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user_detail.css">

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

    <!-- ===== ユーザー情報 ===== -->
    <table class="user-table">
        <thead>
            <tr>
                <th>ユーザーID</th>
                <th>メールアドレス</th>
                <th>ユーザー名</th>
                <th>アクション</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>${user.id}</td>
                <td>${user.email}</td>
                <td>${user.username }</td>
	             <td>
				 	<form action="${pageContext.request.contextPath}/user_delete" method="post"
						onsubmit="return confirm('このユーザーを削除しますか？');">
				    	<input type="hidden" name="id" value="${u.id}">
						<button type="submit">削除</button>
					</form>
				 </td>
            </tr>
        </tbody>
    </table>

    <!-- ===== 投稿一覧 ===== -->
    <div class="post-box">
        <div class="post-header">
            ユーザー${u.id}の投稿
        </div>

        <div class="post-content">
            <c:choose>
                <c:when test="${empty postList}">
                    <p class="no-post">投稿がありません</p>
                </c:when>

                <c:otherwise>
                    <ul class="post-list">
                        <c:forEach var="p" items="${postList}">
                            <li>${p.title}</li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</div>
