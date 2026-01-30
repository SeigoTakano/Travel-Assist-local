package servlet;

import java.io.IOException;
import java.util.regex.Pattern;

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

    // 現実的で厳しすぎないメール正規表現
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

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
        String currentEmail = request.getParameter("currentEmail");
        String newEmail = request.getParameter("newEmail");

        // ===== 入力チェック開始 =====

        if (currentEmail != null) currentEmail = currentEmail.trim();
        if (newEmail != null) newEmail = newEmail.trim();

        // ① 未入力チェック
        if (currentEmail == null || currentEmail.isEmpty()) {
            setErrorAndForward(request, response, "現在のメールアドレスを入力してください。");
            return;
        }
        if (newEmail == null || newEmail.isEmpty()) {
            setErrorAndForward(request, response, "新しいメールアドレスを入力してください。");
            return;
        }

        // ② 現在と新しいメールが同じ場合のチェック
        if (currentEmail.equalsIgnoreCase(newEmail)) {
            setErrorAndForward(request, response,
                "現在のメールアドレスと新しいメールアドレスは同じです。変更してください。");
            return;
        }

        // ③ 長さチェック（255文字まで）
        if (newEmail.length() > 255) {
            setErrorAndForward(request, response, "メールアドレスが長すぎます。");
            return;
        }

        // ④ 全角文字チェック
        if (!newEmail.matches("^[\\x00-\\x7F]+$")) {
            setErrorAndForward(request, response, "全角文字は使用できません。");
            return;
        }

        // ⑤ メール形式チェック
        if (!EMAIL_PATTERN.matcher(newEmail).matches()) {
            setErrorAndForward(request, response, "正しいメールアドレス形式で入力してください。");
            return;
        }

        // ⑥ 正規化（大文字 → 小文字）
        newEmail = newEmail.toLowerCase();

        // ===== 入力チェック終了 =====

        // DB更新処理
        UserDao dao = new UserDao();
        boolean updated = dao.updateEmailByUsername(username, newEmail);

        if (updated) {
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            setErrorAndForward(request, response, "メールアドレスの更新に失敗しました。");
        }
    }

    // エラー表示共通メソッド
    private void setErrorAndForward(
            HttpServletRequest request,
            HttpServletResponse response,
            String message)
            throws ServletException, IOException {

        request.setAttribute("error", message);
        // 現在入力した値を保持
        request.setAttribute("currentEmailValue", request.getParameter("currentEmail"));
        request.setAttribute("newEmailValue", request.getParameter("newEmail"));

        request.getRequestDispatcher("/mypage/change/email.jsp")
               .forward(request, response);
    }
}
