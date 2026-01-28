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
        // DBからユーザー情報を取得
        User user = dao.login(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            
            // ★【ここが重要】Userオブジェクトをまるごとセッションに保存
            // これにより、RouteServletで (User) session.getAttribute("loginUser") が使えるようになります
            session.setAttribute("loginUser", user); 
            
            // 念のため、既存の個別データも残しておきます
            session.setAttribute("userId", user.getId()); 
            session.setAttribute("username", user.getUsername());
            
            // ★【重要】リダイレクト先を menu.jsp ではなくサーブレットの "/menu" に変更
            // これで MenuServlet が走り、最新の検索履歴が取得された状態でメニュー画面が開きます
            response.sendRedirect(request.getContextPath() + "/menu"); 
            
        } else {
            request.setAttribute("error", "メールアドレス、またはパスワードが正しくありません。");
            // パスはプロジェクト構成に合わせて調整してください
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        }
    }
}