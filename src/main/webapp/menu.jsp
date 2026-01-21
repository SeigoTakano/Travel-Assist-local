<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // セッションからユーザー名を取得
    String username = (String) session.getAttribute("username");
    
    // ログインしていない場合はログイン画面にリダイレクト
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Travel-Assist - ダッシュボード</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;500;700&display=swap');
        body {
            font-family: 'Noto Sans JP', sans-serif;
            background-color: #f8fafc;
            color: #1e293b;
        }
        .action-grid-btn { transition: all 0.3s ease; }
        .action-grid-btn:hover { transform: scale(1.02); }
    </style>
</head>
<body class="min-h-screen pb-32">

    <nav class="bg-white border-b border-slate-200 sticky top-0 z-50 shadow-sm">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex justify-between h-16 items-center">
                <div class="flex items-center gap-3 text-blue-600">
                    <span class="text-xl font-bold tracking-tight text-slate-800">Travel-Assist</span>
                </div>

                <div class="flex items-center gap-4">
                    <span class="text-sm font-medium text-slate-600 hidden sm:block">
                        <%= username %> 様
                    </span>
                    <a href="<%= request.getContextPath() %>/profile"
                    	class="text-slate-500 hover:text-blue-600 transition relative">
                        	<i class="fas fa-bell text-lg"></i>
                        	<span class="absolute top-0 right-0 block h-2 w-2 rounded-full bg-red-500 ring-2 ring-white"></span>
                    </a>
                    <a href="logout" class="w-8 h-8 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 hover:bg-blue-200 transition" title="ログアウト">
                        <i class="fas fa-sign-out-alt text-sm"></i>
                    </a>
                </div>
            </div>
        </div>
    </nav>

    <main class="max-w-7xl mx-auto p-8 lg:p-12">
        <header class="mb-12">
            <%-- 動的に名前を表示 --%>
            <h2 class="text-3xl font-bold text-slate-800">おかえりなさい、<%= username %> さん！</h2>
            <p class="text-slate-500 mt-1">今日はどんな旅の計画を立てますか？</p>
        </header>

        <section class="mb-16">
            <h3 class="text-xl font-bold text-slate-800 border-l-4 border-blue-600 pl-4 mb-8">これまでに作ったプラン</h3>
            
            <div class="bg-white rounded-3xl border-2 border-dashed border-slate-200 p-12 flex flex-col items-center justify-center text-center">
                <div class="w-20 h-20 bg-slate-50 rounded-full flex items-center justify-center mb-4">
                    <i class="fas fa-folder-open text-3xl text-slate-300"></i>
                </div>
                <h4 class="text-lg font-bold text-slate-700 mb-2">作成済みプランがありません</h4>
                <p class="text-slate-400 text-sm">最初の旅行プランを立てて、思い出を記録しましょう。</p>
            </div>
        </section>

        <section class="mb-16">
            <h3 class="text-xl font-bold text-slate-800 mb-8 border-l-4 border-blue-600 pl-4">ツールボックス</h3>
            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
                <button onclick="location.href='weather.jsp'" class="action-grid-btn bg-white p-10 rounded-3xl border border-slate-100 shadow-sm flex flex-col items-center hover:bg-orange-50 hover:border-orange-100 group">
                    <div class="w-16 h-16 bg-orange-500 text-white rounded-2xl flex items-center justify-center mb-4 shadow-lg shadow-orange-100 transition-transform group-hover:rotate-12">
                        <i class="fas fa-cloud-sun text-3xl"></i>
                    </div>
                    <span class="font-bold text-slate-700">天気検索</span>
                </button>

                <button onclick="location.href='transport.jsp'" class="action-grid-btn bg-white p-10 rounded-3xl border border-slate-100 shadow-sm flex flex-col items-center hover:bg-blue-50 hover:border-blue-100 group">
                    <div class="w-16 h-16 bg-blue-500 text-white rounded-2xl flex items-center justify-center mb-4 shadow-lg shadow-blue-100 transition-transform group-hover:rotate-12">
                        <i class="fas fa-train text-3xl"></i>
                    </div>
                    <span class="font-bold text-slate-700">交通機関検索</span>
                </button>

                <button onclick="location.href='history.jsp'" class="action-grid-btn bg-white p-10 rounded-3xl border border-slate-100 shadow-sm flex flex-col items-center hover:bg-green-50 hover:border-green-100 group">
                    <div class="w-16 h-16 bg-green-500 text-white rounded-2xl flex items-center justify-center mb-4 shadow-lg shadow-green-100 transition-transform group-hover:rotate-12">
                        <i class="fas fa-camera-retro text-3xl"></i>
                    </div>
                    <span class="font-bold text-slate-700">旅の記録</span>
                </button>

                <button onclick="location.href='route.jsp'" class="action-grid-btn bg-white p-10 rounded-3xl border border-slate-100 shadow-sm flex flex-col items-center hover:bg-purple-50 hover:border-purple-100 group">
                    <div class="w-16 h-16 bg-purple-500 text-white rounded-2xl flex items-center justify-center mb-4 shadow-lg shadow-purple-100 transition-transform group-hover:rotate-12">
                        <i class="fas fa-map-marked-alt text-3xl"></i>
                    </div>
                    <span class="font-bold text-slate-700">ルート検索</span>
                </button>
            </div>
        </section>
    </main>

    <footer class="py-8 border-t border-slate-100 text-center text-slate-400 text-sm">
        <p>&copy; 2026 TravelPlanner. All rights reserved.</p>
    </footer>

    <div class="fixed bottom-0 left-0 right-0 p-6 bg-gradient-to-t from-slate-50 to-transparent pointer-events-none">
        <div class="max-w-7xl mx-auto flex justify-center pointer-events-auto">
            <button onclick="location.href='create-plan.jsp'" class="w-full md:w-auto md:min-w-[400px] bg-blue-600 text-white py-5 px-12 rounded-2xl font-bold text-xl flex items-center justify-center gap-3 shadow-2xl shadow-blue-200 hover:bg-blue-700 transition-all hover:scale-[1.02] active:scale-[0.98]">
                <i class="fas fa-plus-circle text-2xl"></i>
                <span>新しい旅行プランを作成する</span>
            </button>
        </div>
    </div>

</body>
</html>