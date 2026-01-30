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

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 検索履歴取得
        RouteDao routeDao = new RouteDao();
        List<Route> recentSpots = routeDao.getRecentRoutes(loginUser.getId(), 5);
        request.setAttribute("recentSpots", recentSpots);

        // --- 権限による分岐ロジック ---
        if (loginUser.isManagerFlag() && loginUser.isAdminFlag()) {
            // スーパー管理者 (両方1)
            request.getRequestDispatcher("/super_admin_menu.jsp").forward(request, response);
        } 
        else if (loginUser.isManagerFlag()) {
            // 一般管理者 (managerのみ1)
            request.getRequestDispatcher("/admin_menu.jsp").forward(request, response);
        } 
        else {
            // 一般ユーザー
            request.getRequestDispatcher("/menu.jsp").forward(request, response);
        }
    }
}