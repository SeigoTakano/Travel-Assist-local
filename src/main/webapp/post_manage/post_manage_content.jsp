<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<link rel="stylesheet" href="${pageContext.request.contextPath}/css/post_manage.css">

<div class="post-manage-page">

    <h2>投稿管理</h2>

    <div class="post-manage-content">

        <!-- 投稿がない場合 -->
        <c:if test="${empty postList}">
            <div class="no-post">
                投稿がありません
            </div>
        </c:if>

        <!-- 投稿がある場合 -->
        <c:forEach var="post" items="${postList}">
            <div class="post-item">

                <div class="post-header">
                    <div class="profile"></div>
                    <div class="user-info">
                        <strong>${post.userName}</strong>
                        <span class="time">/${post.createdAt}</span>
                    </div>
                </div>

                <div class="post-text">${post.content}</div>

                <c:if test="${not empty post.imagePath}">
                    <div class="post-image">
                        <img src="${pageContext.request.contextPath}${post.imagePath}">
                    </div>
                </c:if>

            </div>
        </c:forEach>

    </div>

    <!-- 管理画面用フッター（持たせる） -->
<!-- 管理画面用フッター -->
<div class="admin-footer">
    <button class="footer-btn">
        <span class="icon">🏠</span>
        <span class="label">HOME</span>
    </button>
    <button class="footer-btn">
        <span class="icon">🔍</span>
    </button>
    <button class="footer-btn">
        <span class="icon">🔔</span>
    </button>
</div>


</div>
