<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>旅の記録</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage/records.css">
</head>
<body>

<header class="header">
    <h1 class="site-title">マイページ</h1>
    <nav class="tabs">
		<a class="tab" href="<%= request.getContextPath() %>/profile">プロフィール</a>
    	<a class="tab active" href="<%= request.getContextPath() %>/records">旅の記録</a>
    	<a class="tab" href="<%= request.getContextPath() %>/subscription">サブスクリプション</a>
	</nav>
</header>

<main class="container">
	
</main>
</body>
</html>