package servlet;

import java.io.IOException;

import bean.User;
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
        // 戻り値をUser型で受け取る
        User user = dao.login(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            // IDと名前を両方セッションに保存
            session.setAttribute("userId", user.getId()); 
            session.setAttribute("username", user.getUsername());
            
            // ログイン成功時はメニューへ
            response.sendRedirect("menu.jsp"); 
        } else {
            request.setAttribute("error", "メールアドレス、またはパスワードが正しくありません。");
            // login.jspがwebapp/login/フォルダ内にある場合のパス
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        }
    }
}