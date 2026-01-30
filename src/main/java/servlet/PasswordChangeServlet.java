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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // --- 現在のパスワードチェック ---
        if (currentPassword == null || currentPassword.trim().isEmpty()) {
            setErrorAndForward(request, response, "現在のパスワードを入力してください。");
            return;
        }
        currentPassword = currentPassword.trim();

        // --- 新しいパスワードチェック ---
        if (newPassword == null || newPassword.trim().isEmpty()) {
            setErrorAndForward(request, response, "新しいパスワードを入力してください。");
            return;
        }
        newPassword = newPassword.trim();

        if (newPassword.length() < 8) {
            setErrorAndForward(request, response, "新しいパスワードは8文字以上で入力してください。");
            return;
        }

        if (!newPassword.matches("^[\\x00-\\x7F]+$")) {
            setErrorAndForward(request, response, "新しいパスワードに全角文字は使用できません。");
            return;
        }

        if (newPassword.length() > 255) {
            setErrorAndForward(request, response, "新しいパスワードが長すぎます。");
            return;
        }

        // --- 現在と同じパスワードかチェック ---
        if (currentPassword.equals(newPassword)) {
            setErrorAndForward(request, response, "現在のパスワードと同じです。新しいパスワードを入力してください。");
            return;
        }

        // --- 確認パスワードチェック ---
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            setErrorAndForward(request, response, "確認用パスワードを入力してください。");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            setErrorAndForward(request, response, "確認用パスワードと一致しません。");
            return;
        }

        // --- DB更新処理 ---
        UserDao dao = new UserDao();
        boolean updated = dao.updatePasswordByUsername(username, newPassword);

        if (updated) {
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            setErrorAndForward(request, response, "パスワードの更新に失敗しました。");
        }
    }

    private void setErrorAndForward(HttpServletRequest request, HttpServletResponse response,
                                    String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/mypage/change/password.jsp").forward(request, response);
    }
}
