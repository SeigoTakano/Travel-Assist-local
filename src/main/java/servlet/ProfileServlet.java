package servlet;

import java.io.IOException;

import bean.User;
import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッション取得
        HttpSession session = request.getSession(false);
        if (session == null) {
            // セッションがない場合はログイン画面へリダイレクト
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return;
        }

        // Session から username を取得
        String username = (String) session.getAttribute("username");
        if (username == null) {
            // username が Session にない場合もログイン画面へ
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return;
        }

        // DB からユーザー情報を取得
        UserDao dao = new UserDao();
        User user = null;
        try {
            user = dao.getUserByUsername(username); // username で検索
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user != null) {
            request.setAttribute("email", user.getEmail());
            request.setAttribute("username", user.getUsername());
        }

        // profile.jsp にフォワード
        request.getRequestDispatcher("/mypage/profile.jsp").forward(request, response);
    }
}

