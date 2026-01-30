package servlet;

import java.io.IOException;
import java.util.regex.Pattern;

import bean.User;
import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/EmailChangeServlet")
public class EmailChangeServlet extends HttpServlet {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

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

        String currentEmail = request.getParameter("currentEmail");
        String newEmail = request.getParameter("newEmail");

        if (currentEmail != null) currentEmail = currentEmail.trim();
        if (newEmail != null) newEmail = newEmail.trim();

        if (currentEmail == null || currentEmail.isEmpty()) {
            setError(request, response, "現在のメールアドレスを入力してください。");
            return;
        }

        if (newEmail == null || newEmail.isEmpty()) {
            setError(request, response, "新しいメールアドレスを入力してください。");
            return;
        }

        if (currentEmail.equalsIgnoreCase(newEmail)) {
            setError(request, response,
                    "現在のメールアドレスと新しいメールアドレスは同じです。変更してください。");
            return;
        }

        if (newEmail.length() > 255) {
            setError(request, response, "メールアドレスが長すぎます。");
            return;
        }

        if (!newEmail.matches("^[\\x00-\\x7F]+$")) {
            setError(request, response, "全角文字は使用できません。");
            return;
        }

        if (!EMAIL_PATTERN.matcher(newEmail).matches()) {
            setError(request, response, "正しいメールアドレス形式で入力してください。");
            return;
        }

        newEmail = newEmail.toLowerCase();

        UserDao dao = new UserDao();
        boolean updated = dao.updateEmailById(loginUser.getId(), newEmail);

        if (updated) {
            loginUser.setEmail(newEmail); // ★ セッション同期
            session.setAttribute("loginUser", loginUser);
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            setError(request, response, "メールアドレスの更新に失敗しました。");
        }
    }

    private void setError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {

        request.setAttribute("error", message);
        request.setAttribute("currentEmailValue", request.getParameter("currentEmail"));
        request.setAttribute("newEmailValue", request.getParameter("newEmail"));
        request.getRequestDispatcher("/mypage/change/email.jsp").forward(request, response);
    }
}
