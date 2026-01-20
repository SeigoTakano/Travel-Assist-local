package servlet;

import java.io.IOException;

import dao.LoginDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        String errorMessage = null;

        if (username == null || username.isEmpty() || 
            email == null || email.isEmpty() || 
            password == null || password.isEmpty()) {
            errorMessage = "全ての項目を入力してください。";
        } else if (password.length() > 10) {
            errorMessage = "パスワードは10文字以内で設定してください。";
        }

        if (errorMessage != null) {
            request.setAttribute("error", errorMessage);
            // 修正箇所：フォルダ名を含める
            request.getRequestDispatcher("login/register.jsp").forward(request, response);
            return;
        }

        LoginDao dao = new LoginDao();

        try {
            if (dao.isEmailExists(email)) {
                request.setAttribute("error", "このメールアドレスは既に登録されています。");
                // 修正箇所：フォルダ名を含める
                request.getRequestDispatcher("login/register.jsp").forward(request, response);
                return;
            }

            boolean isSuccess = dao.registerUser(username, email, password);

            if (isSuccess) {
                // 修正箇所：リダイレクト先にもフォルダ名を含める
                response.sendRedirect("login/login.jsp?success=registered");
            } else {
                request.setAttribute("error", "登録処理中にエラーが発生しました。");
                request.getRequestDispatcher("login/register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            request.getRequestDispatcher("login/register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 直接アクセスされた場合もフォルダ内を表示
        response.sendRedirect("login/register.jsp");
    }
}