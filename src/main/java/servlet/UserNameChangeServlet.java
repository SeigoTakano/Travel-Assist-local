package servlet;

import java.io.IOException;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UsernameChangeServlet")
public class UserNameChangeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GET: 初回アクセス用
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return;
        }

        // 現在のユーザーネームを username にセット
        String username = (String) session.getAttribute("username");
        request.setAttribute("username", username);

        request.getRequestDispatcher("/mypage/change/username.jsp").forward(request, response);
    }

    // POST: 送信時
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return;
        }

        String currentUsername = (String) session.getAttribute("username");
        String newUsername = request.getParameter("newUsername");

        // ① null または空文字チェック（最優先）
        if (newUsername == null || newUsername.trim().isEmpty()) {
            setErrorAndForward(request, response, "新しいユーザーネームを入力してください。", currentUsername, "");
            return;
        }

        newUsername = newUsername.trim();

        // ② 現在と同じ名前チェック
        if (currentUsername.equalsIgnoreCase(newUsername)) {
            setErrorAndForward(request, response, "現在のユーザーネームと同じです。変更してください。", currentUsername, newUsername);
            return;
        }

        // ③ 英数字とアンダースコアのみチェック
        if (!newUsername.matches("[A-Za-z0-9_]+")) {
            setErrorAndForward(request, response, "ユーザーネームは英数字とアンダースコアのみです。", currentUsername, newUsername);
            return;
        }

        // ④ DB更新
        UserDao dao = new UserDao();
        boolean updated = dao.updateUsername(currentUsername, newUsername);

        if (updated) {
            session.setAttribute("username", newUsername);
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            setErrorAndForward(request, response, "ユーザーネームの更新に失敗しました。", currentUsername, newUsername);
        }
    }

    private void setErrorAndForward(HttpServletRequest request, HttpServletResponse response,
                                    String message, String currentUsername, String newUsername)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.setAttribute("username", currentUsername); // 現在のユーザーネーム
        request.setAttribute("newUsernameValue", newUsername); // 新しいユーザーネーム入力値
        request.getRequestDispatcher("/mypage/change/username.jsp").forward(request, response);
    }
}
