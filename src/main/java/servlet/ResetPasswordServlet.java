package servlet;

import java.io.IOException;

import dao.LoginDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 文字化け防止
        request.setCharacterEncoding("UTF-8");

        // JSPのhiddenフィールドと入力欄から値を取得
        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");

        // 入力チェック（念のためサーバーサイドでも）
        if (email == null || email.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            request.setAttribute("error", "不正なリクエストです。もう一度やり直してください。");
            request.getRequestDispatcher("login/forgot.jsp").forward(request, response);
            return;
        }

        LoginDao dao = new LoginDao();
        
        // DB更新実行
        boolean isSuccess = dao.updatePassword(email, newPassword);

        if (isSuccess) {
            // 成功：メッセージを付けてログイン画面へリダイレクト（またはフォワード）
            // リダイレクト時にメッセージを渡すためセッションを使うか、完了画面へ飛ばす
            request.setAttribute("message", "パスワードを更新しました。新しいパスワードでログインしてください。");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        } else {
            // 失敗：エラーメッセージを戻す
            request.setAttribute("error", "パスワードの更新に失敗しました。時間をおいて再度お試しください。");
            request.setAttribute("email", email); // メールアドレスを保持させる
            request.getRequestDispatcher("login/reset_password.jsp").forward(request, response);
        }
    }
}