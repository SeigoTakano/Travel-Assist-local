package servlet;

import java.io.IOException;

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

        HttpSession session = request.getSession();
        String currentUsername = request.getParameter("currentUsername");
        String newUsername = request.getParameter("newUsername");

        if (newUsername == null || !newUsername.matches("[A-Za-z0-9_]+")) {
            request.setAttribute("error", "ユーザーネームは英数字とアンダースコアのみです。");
            request.getRequestDispatcher("username.jsp").forward(request, response);
            return;
        }

        // TODO: データベース更新処理
        // UserDao.updateUsername(userId, newUsername);

        session.setAttribute("username", newUsername);

        response.sendRedirect("profile.jsp");
    }
}
