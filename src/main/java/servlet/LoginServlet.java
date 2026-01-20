package servlet;

import java.io.IOException;

import dao.LoginDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        LoginDao dao = new LoginDao();
        String username = dao.getUsernameByLogin(email, password);

        if (username != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            // menu.jspはwebapp直下にある場合はこのままでOK
            response.sendRedirect("menu.jsp"); 
        } else {
            request.setAttribute("error", "メールアドレス、またはパスワードが正しくありません。");
            // 修正箇所：フォルダ名を含める
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        }
    }
}