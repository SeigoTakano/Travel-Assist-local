<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>マイページ</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage/profile.css">
</head>
<body>

<header class="header">
    <h1 class="site-title">マイページ</h1>
    <nav class="tabs">
		<a class="tab active" href="<%= request.getContextPath() %>/profile">プロフィール</a>
    	<a class="tab" href="<%= request.getContextPath() %>/records">旅の記録</a>
    	<a class="tab" href="<%= request.getContextPath() %>/subscription">サブスクリプション</a>
	</nav>
</header>

<main class="container">
    <section class="profile-section">
        <h2>プロフィール</h2>

        <!-- ユーザー名 -->
        <div class="form-group">
    		<span class="form-label">ユーザーネーム</span>
    		<span class="form-value">${username}</span>
    		<a href="<%= request.getContextPath() %>/mypage/change/username.jsp"
    			class="btn-primary">編集</a>
		</div>

        <!-- ID（変更不可） -->
        <div class="form-group">
    		<span class="form-label">ID</span>
    		<span class="form-value">${userid}</span>
		</div>

        <!-- メールアドレス -->
        <div class="form-group">
    		<span class="form-label">メールアドレス</span>
    		<span class="form-value">${email}</span>
    		<a href="<%= request.getContextPath() %>/mypage/change/email.jsp"
    			class="btn-primary">編集</a>
		</div>

        <div class="form-group password-group">
    		<a href="<%= request.getContextPath() %>/mypage/change/password.jsp"
    			class="btn-password">パスワード変更</a>
		</div>
    </section>
</main>
</body>
</html>
