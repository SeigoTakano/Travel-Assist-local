<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>交通機関比較検索</title>
    
    <%@ include file="../base.jsp" %>
     
    <link rel="stylesheet" href="../css/transport.css">
    
</head>
<body>
    

    <div class="container">
        <nav class="tabs" id="tabMenu">
            <button class="tab active" data-type="shinkansen">新幹線</button>
            <button class="tab" data-type="plane">飛行機</button>
            <button class="tab" data-type="bus">高速バス</button>
        </nav>

        <section class="card">
            <h2 id="formTitle">新幹線検索</h2>
            <form id="searchForm">
                <div class="form-row">
                    <div class="field">
                        <label id="depLabel">出発地</label>
                        <select id="departure" required></select>
                    </div>
                    <div style="padding-bottom: 15px; font-size: 20px; color: #9ca3af;">⇄</div>
                    <div class="field">
                        <label id="destLabel">目的地</label>
                        <select id="destination" required></select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="field">
                        <label>出発日</label>
                        <input type="date" id="travelDate" required>
                    </div>
                    <div class="field">
                        <label>条件設定</label>
                        <select id="condition">
                            <option value="default">標準</option>
                            <option value="cheap">料金優先</option>
                            <option value="fast">時間優先</option>
                        </select>
                    </div>
                </div>

                <div class="form-row" style="gap: 10px;">
                    <button type="button" class="search-btn" id="singleBtn" style="flex: 1; background: #6b7280;">
                        <span id="singleBtnText">新幹線</span>のみ
                    </button>
                    <button type="button" class="search-btn" id="compareBtn" style="flex: 1;">
                        すべての手段を比較
                    </button>
                </div>
            </form>
        </section>

        <section class="result-section" id="resultCard" style="display:none;">
            <h3 style="text-align:center; margin-bottom:20px; color: #4b5563;">比較結果</h3>
            <div id="resultContent" class="comparison-grid"></div>
        </section>
    </div>

    <script src="../js/transport.js" defer></script>

</body>
	
</html>