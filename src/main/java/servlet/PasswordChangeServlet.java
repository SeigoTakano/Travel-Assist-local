package servlet;

import java.io.IOException;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PasswordChangeServlet")
public class PasswordChangeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            // セッションがない場合はログイン画面へ
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // 簡単なバリデーション
        if (newPassword == null || newPassword.length() < 6) {
            request.setAttribute("error", "パスワードは8文字以上で入力してください。");
            request.getRequestDispatcher("/mypage/change/password.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "確認用パスワードと一致しません。");
            request.getRequestDispatcher("/mypage/change/password.jsp").forward(request, response);
            return;
        }

        // DB更新処理
        UserDao dao = new UserDao();
        boolean updated = dao.updatePasswordByUsername(username, newPassword);

        if (updated) {
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            request.setAttribute("error", "パスワードの更新に失敗しました。");
            request.getRequestDispatcher("/mypage/change/password.jsp").forward(request, response);
        }
    }
}
