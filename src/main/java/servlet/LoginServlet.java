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
        User user = dao.login(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            
            // Userオブジェクトをまるごと保存（フラグ情報も含まれている）
            session.setAttribute("loginUser", user); 
            
            // 個別データも保持
            session.setAttribute("userId", user.getId()); 
            session.setAttribute("username", user.getUsername());
            
            // ★ メニュー用のサーブレットへリダイレクト
            response.sendRedirect(request.getContextPath() + "/menu"); 
            
        } else {
            request.setAttribute("error", "メールアドレス、またはパスワードが正しくありません。");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        }
    }
}