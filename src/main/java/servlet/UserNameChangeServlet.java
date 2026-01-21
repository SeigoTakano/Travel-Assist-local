package servlet;

import java.io.IOException;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UsernameChangeServlet")
public class UserNameChangeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return;
        }

        String currentUsername = (String) session.getAttribute("username");
        String newUsername = request.getParameter("newUsername");

        if (newUsername == null || !newUsername.matches("[A-Za-z0-9_]+")) {
            request.setAttribute("error", "ユーザーネームは英数字とアンダースコアのみです。");
            request.getRequestDispatcher("/mypage/change/username.jsp").forward(request, response);
            return;
        }

        // DB更新処理
        UserDao dao = new UserDao();
        boolean updated = dao.updateUsername(currentUsername, newUsername);

        if (updated) {
            // Session の username も更新
            session.setAttribute("username", newUsername);
            // 更新後は profile.jsp にリダイレクト
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            request.setAttribute("error", "ユーザーネームの更新に失敗しました。");
            request.getRequestDispatcher("/mypage/change/username.jsp").forward(request, response);
        }
    }
}
