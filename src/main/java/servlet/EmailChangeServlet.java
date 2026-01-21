package servlet;

import java.io.IOException;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/EmailChangeServlet")
public class EmailChangeServlet extends HttpServlet {
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
        String newEmail = request.getParameter("newEmail");

        // 簡単なバリデーション
        if (newEmail == null || !newEmail.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")) {
            request.setAttribute("error", "正しいメールアドレスを入力してください。");
            request.getRequestDispatcher("/mypage/change/email.jsp").forward(request, response);
            return;
        }

        // DB更新処理
        UserDao dao = new UserDao();
        boolean updated = dao.updateEmailByUsername(username, newEmail);

        if (updated) {
            // Profile 画面にリダイレクト
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            request.setAttribute("error", "メールアドレスの更新に失敗しました。");
            request.getRequestDispatcher("mypage/change/email.jsp").forward(request, response);
        }
    }
}
