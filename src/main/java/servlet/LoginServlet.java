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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // DAOで詳細情報を取得するように改造しても良いですが、まずは基本形
         LoginDao dao = new LoginDao();
        boolean isSuccess = dao.loginCheck(email, password);
        
        if (isSuccess) {
            HttpSession session = request.getSession();
            // セッションにログイン情報を格納
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("userEmail", email);
            
            response.sendRedirect("welcome.jsp");
        } else {
            // 失敗時はログイン画面へ戻る
            request.setAttribute("error", "メールアドレス、またはパスワードが正しくありません。");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}