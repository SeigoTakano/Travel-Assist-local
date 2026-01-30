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

@WebServlet("/UsernameChangeServlet")
public class UserNameChangeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        request.setAttribute("username", loginUser.getUsername());
        request.getRequestDispatcher("/mypage/change/username.jsp").forward(request, response);
    }

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

        String currentUsername = loginUser.getUsername();
        String newUsername = request.getParameter("newUsername");

        // ① null or 空
        if (newUsername == null || newUsername.trim().isEmpty()) {
            setErrorAndForward(request, response,
                    "新しいユーザーネームを入力してください。",
                    currentUsername, "");
            return;
        }

        newUsername = newUsername.trim();

        // ② 同じ名前
        if (currentUsername.equalsIgnoreCase(newUsername)) {
            setErrorAndForward(request, response,
                    "現在のユーザーネームと同じです。変更してください。",
                    currentUsername, newUsername);
            return;
        }

        // ③ 文字種チェック
        if (!newUsername.matches("[A-Za-z0-9_]+")) {
            setErrorAndForward(request, response,
                    "ユーザーネームは英数字とアンダースコアのみです。",
                    currentUsername, newUsername);
            return;
        }

        UserDao dao = new UserDao();
        boolean updated = dao.updateUsernameById(loginUser.getId(), newUsername);

        if (updated) {
            loginUser.setUsername(newUsername); // ★ セッション同期
            session.setAttribute("loginUser", loginUser);
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            setErrorAndForward(request, response,
                    "ユーザーネームの更新に失敗しました。",
                    currentUsername, newUsername);
        }
    }

    private void setErrorAndForward(HttpServletRequest request, HttpServletResponse response,
                                    String message, String currentUsername, String newUsername)
            throws ServletException, IOException {

        request.setAttribute("error", message);
        request.setAttribute("username", currentUsername);
        request.setAttribute("newUsernameValue", newUsername);
        request.getRequestDispatcher("/mypage/change/username.jsp").forward(request, response);
    }
}
