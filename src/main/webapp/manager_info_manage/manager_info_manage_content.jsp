<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者情報管理</title>
<link rel="stylesheet" type="text/css" 

      href="../css/user_info_list.css">
</head>
<body>
<div class="main">
<!-- タイトル（ヘッダーではなくページ内の表示として） -->
<div class="page-title">管理者情報管理</div>
<!-- 検索ボックス -->
<form action="UserListServlet" method="get" class="search-box">
<input type="text" name="keyword" placeholder="ユーザーID / メールアドレス">
<button type="submit">🔍</button>
</form>
<!-- ユーザー一覧テーブル -->
<table class="user-table">
<thead>
<tr>
<th>ユーザーID</th>
<th>メールアドレス</th>
<th>名前</th>
<th>アクション</th>
</tr>
</thead>
<tbody>
<%-- データなしのため空行のみ --%>
<tr>
<td colspan="4" class="no-data">現在データがありません</td>
</tr>
</tbody>
</table>
</div>
</body>
</html>

 