package servlet;

import java.io.IOException;
import java.util.List;

import bean.Route;
import bean.User;
import dao.RouteDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/route_search")
public class RouteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        // --- 1. 履歴一覧をJSONで返す処理 (JSの loadHistory 用) ---
        if ("list".equals(action)) {
            HttpSession session = request.getSession();
            User loginUser = (User) session.getAttribute("loginUser");

            RouteDao dao = new RouteDao();
            // 最新10件を取得
            List<Route> list = dao.getRecentRoutes(loginUser.getId(), 10);

            // レスポンス設定
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // JSON文字列を手動構築 (JS側の route.start_name などのキー名に合わせる)
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                Route r = list.get(i);
                json.append("{");
                json.append("\"id\":").append(r.getId()).append(",");
                json.append("\"start_name\":\"").append(r.getStartName()).append("\",");
                json.append("\"end_name\":\"").append(r.getEndName()).append("\"");
                json.append("}");
                if (i < list.size() - 1) json.append(",");
            }
            json.append("]");

            response.getWriter().write(json.toString());
            return; // JSONを返したら終了
        }

        // --- 2. 通常の画面表示 ---
        request.getRequestDispatcher("/route_search/route_search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String action = request.getParameter("action");
        RouteDao dao = new RouteDao();
        boolean success = false;

        try {
            if ("delete".equals(action)) {
                // 削除実行
                int routeId = Integer.parseInt(request.getParameter("id"));
                success = dao.deleteRoute(routeId);
            } else {
                // 保存実行
                String start = request.getParameter("start");
                String end = request.getParameter("end");
                String polyline = request.getParameter("polyline");
                boolean isFav = Boolean.parseBoolean(request.getParameter("isFavorite"));
                
                success = dao.insertRoute(loginUser.getId(), start, end, polyline, isFav);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}