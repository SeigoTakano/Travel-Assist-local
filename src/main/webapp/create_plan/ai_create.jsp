<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>トラベルアシスト</title>

	  <%@ include file="../base.jsp" %>
</head>
<body>

<body>
<div class="container mt-4">
    <div class="card p-4 shadow-sm">
        <h3 class="mb-4">スケジュール登録（AI作成）</h3>
        <form action="AiPlanServlet" method="post">
            <div class="row mb-3">
                <div class="col">
                    <label class="form-label">時間粒度</label>
                    <select name="timeStep" class="form-select">
                        <option value="30">30分単位</option>
                        <option value="60" selected>1時間単位</option>
                    </select>
                </div>
                <div class="col">
                    <label class="form-label">開始時間</label>
                    <input type="time" name="startTime" class="form-control" value="09:00">
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">プラン傾向</label>
                <select name="tendency" class="form-select">
                    <option value="balance">バランス型</option>
                    <option value="food">食事多め型</option>
                    <option value="sightseeing">観光多め型</option>
                    <option value="rest">休憩多め型</option>
                    <option value="move">移動多め型</option>
                </select>
            </div>

            <div class="mt-4">
                <button type="submit" class="btn btn-success w-100">AIでスケジュールを自動生成する</button>
            </div>
        </form>
    </div>
</div>
</body>

</html>