<%@ page contentType="text/html; charset=UTF-8" %>

<div class="main">

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
            <tr>
                <td colspan="4" class="no-data">現在データがありません</td>
            </tr>
        </tbody>
    </table>

</div>
