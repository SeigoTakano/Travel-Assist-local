<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // セッションからログインユーザー名を取得
    String loginUser = (String) session.getAttribute("username");
    if (loginUser == null) {
        // セッションがなければログイン画面へ
        response.sendRedirect(request.getContextPath() + "/login/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>旅の記録 - 新規投稿</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/create_post.css">
  <script>const contextPath = "${pageContext.request.contextPath}";</script>
</head>
<body>
  <div class="container">
    <div class="title-badge">旅の記録</div>

    <form action="${pageContext.request.contextPath}/create_post" method="post" enctype="multipart/form-data" id="postForm">
      <div class="card">
        
        <div style="text-align: right; font-size: 0.9em; color: #666; margin-bottom: 10px;">
            投稿者: <strong><%= loginUser %></strong> さん
        </div>

        <div class="row">
          <label for="title">タイトル（30文字以内）</label>
          <input type="text" id="title" name="title" placeholder="タイトルを入力" required maxlength="30">
        </div>

        <div class="content">
          <div class="image-area">
            <div class="preview" id="preview">
              <span class="icon">画像を選択</span>
            </div>
            <%-- 実際のファイル選択 --%>
            <input type="file" id="imageInput" name="imageFile" accept="image/*" required>
            <%-- 「JPG・PNG・GIF対応」の文字はスッキリさせるため削除 --%>
          </div>

          <div class="text-area">
            <label for="impression">感想（200文字以内）</label>
            <%-- ★maxlengthで文字数制限、requiredで必須入力に --%>
            <textarea id="impression" name="comment" placeholder="旅の思い出をここに!" required maxlength="200"></textarea>
          </div>
        </div>

        <%-- ★hiddenの「ゲストユーザー」は削除！サーブレット側でセッションから名前を取るため --%>

        <div class="buttons">
          <%-- キャンセルから「戻る」に名称変更 --%>
          <button class="btn save" type="button" onclick="history.back()">戻る</button>
          <button class="btn post" id="postBtn" type="submit">投稿する</button>
        </div>
      </div>
    </form>
  </div>
  <script src="${pageContext.request.contextPath}/js/create_post.js"></script>
</body>
</html>