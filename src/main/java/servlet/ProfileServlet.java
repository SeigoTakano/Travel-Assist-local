package servlet;

import java.io.IOException;

import bean.User;
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

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return;
        }

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return;
        }

        request.setAttribute("username", loginUser.getUsername());
        request.setAttribute("email", loginUser.getEmail());

        request.getRequestDispatcher("/mypage/profile.jsp")
               .forward(request, response);
    }
}
