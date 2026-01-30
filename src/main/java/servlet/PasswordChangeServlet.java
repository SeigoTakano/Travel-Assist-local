package servlet;

import java.io.IOException;

import bean.User;
import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PasswordChangeServlet")
public class PasswordChangeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

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

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (currentPassword == null || currentPassword.trim().isEmpty()) {
            setError(request, response, "現在のパスワードを入力してください。");
            return;
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            setError(request, response, "新しいパスワードを入力してください。");
            return;
        }

        if (newPassword.length() < 8) {
            setError(request, response, "新しいパスワードは8文字以上で入力してください。");
            return;
        }

        if (!newPassword.matches("^[\\x00-\\x7F]+$")) {
            setError(request, response, "新しいパスワードに全角文字は使用できません。");
            return;
        }

        if (newPassword.length() > 255) {
            setError(request, response, "新しいパスワードが長すぎます。");
            return;
        }

        if (currentPassword.equals(newPassword)) {
            setError(request, response,
                    "現在のパスワードと同じです。新しいパスワードを入力してください。");
            return;
        }

        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            setError(request, response, "確認用パスワードを入力してください。");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            setError(request, response, "確認用パスワードと一致しません。");
            return;
        }

        UserDao dao = new UserDao();
        boolean updated = dao.updatePasswordById(loginUser.getId(), newPassword);

        if (updated) {
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            setError(request, response, "パスワードの更新に失敗しました。");
        }
    }

    private void setError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {

        request.setAttribute("error", message);
        request.getRequestDispatcher("/mypage/change/password.jsp").forward(request, response);
    }
}
