package servlet;

import java.io.IOException;

import dao.RouteDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/saveRoute")
public class RouteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 本来はログイン中のユーザーIDを使いますが、テスト用に「1」を固定で入れます
        int userId = 1; 
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String polyline = request.getParameter("polyline");
        boolean isFav = Boolean.parseBoolean(request.getParameter("isFavorite"));

        RouteDao dao = new RouteDao();
        boolean success = dao.insertRoute(userId, start, end, polyline, isFav);

        response.setStatus(success ? 200 : 500);
    }
}