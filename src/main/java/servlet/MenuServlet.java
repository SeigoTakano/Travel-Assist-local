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
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. セッションからログインユーザー情報を取得
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // ログインしていない場合はログイン画面へリダイレクト
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = loginUser.getId();

        // 2. RouteDaoを使って最新の検索履歴（5件）を取得
        RouteDao routeDao = new RouteDao();
        List<Route> recentSpots = routeDao.getRecentRoutes(userId, 5);

        // 3. リクエストスコープにデータを保存
        // JSP側の ${recentSpots} という名前に合わせます
        request.setAttribute("recentSpots", recentSpots);

        /* * プラン一覧（planList）は他の人が担当とのことなので、
         * ここでは空のリストを渡すか、担当者が作成したDAOを呼び出す形になります。
         * 例: List<Plan> planList = planDao.getPlansByUserId(userId);
         * request.setAttribute("planList", planList);
         */

        // 4. menu.jsp へフォワード
        request.getRequestDispatcher("/WEB-INF/view/menu.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}