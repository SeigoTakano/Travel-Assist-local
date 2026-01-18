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
        <h3 class="mb-4">プラン作成</h3>
        <form action="PlanServlet" method="post" id="planForm">
            <div class="row mb-3">
                <div class="col">
                    <label class="form-label">プラン名</label>
                    <input type="text" name="planName" class="form-control" placeholder="例：北海道4泊5日の旅" required>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col">
                    <label class="form-label">出発地</label>
                    <input type="text" name="departure" id="departure" class="form-control" placeholder="例：東京駅" required>
                </div>
                <div class="col">
                    <label class="form-label">行き先</label>
                    <input type="text" name="destination" id="destination" class="form-control" placeholder="例：札幌" required>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col">
                    <label class="form-label">開始日</label>
                    <input type="date" name="startDate" id="startDate" class="form-control" onchange="generateDateButtons()" required>
                </div>
                <div class="col">
                    <label class="form-label">終了日</label>
                    <input type="date" name="endDate" id="endDate" class="form-control" onchange="generateDateButtons()" required>
                </div>
            </div>

            <div id="dateButtonArea" class="mb-4 d-flex flex-wrap gap-2">
                </div>

            <div class="d-flex gap-2">
                <button type="button" class="btn btn-outline-primary" onclick="location.href='aiCreate.jsp'">AI作成ページへ</button>
                <button type="reset" class="btn btn-outline-danger">プランをリセット</button>
                <button type="submit" class="btn btn-primary ms-auto">プラン作成へ</button>
            </div>
        </form>
    </div>
</div>

<script>
function generateDateButtons() {
    const start = document.getElementById('startDate').value;
    const end = document.getElementById('endDate').value;
    const area = document.getElementById('dateButtonArea');
    area.innerHTML = ''; // 一度リセット

    if (!start || !end) return;

    const startDate = new Date(start);
    const endDate = new Date(end);

    // 日付の差分を計算してボタンを生成
    for (let d = new Date(startDate); d <= endDate; d.setDate(d.getDate() + 1)) {
        const dateStr = d.toISOString().split('T')[0];
        const btn = document.createElement('button');
        btn.type = 'button';
        btn.className = 'btn btn-outline-secondary';
        btn.innerText = dateStr;
        btn.onclick = function() {
            // 全ボタンを白（outline）に戻してから自分だけ青にする
            document.querySelectorAll('#dateButtonArea .btn').forEach(b => {
                b.className = 'btn btn-outline-secondary';
            });
            this.className = 'btn btn-primary';
        };
        area.appendChild(btn);
    }
}

// バリデーション：特定の個人名などの入力を防ぐ簡易チェック
document.getElementById('planForm').onsubmit = function(e) {
    const checkFields = ['departure', 'destination'];
    const invalidPattern = /(.+の家|マイハウス|自宅)/;
    
    for (let id of checkFields) {
        const val = document.getElementById(id).value;
        if (invalidPattern.test(val)) {
            alert('場所名には具体的な公共施設や駅、地名を入力してください。');
            e.preventDefault();
            return false;
        }
    }
};
</script>
</body>

</html>