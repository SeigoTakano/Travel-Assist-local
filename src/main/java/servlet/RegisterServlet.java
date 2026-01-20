package servlet;

import java.io.IOException;

import dao.LoginDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 新規ユーザー登録を制御するサーブレット
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. リクエストの文字エンコーディング設定（日本語入力対策）
        request.setCharacterEncoding("UTF-8");

        // 2. フォームデータの取得（JSPのinputタグのname属性と一致させる）
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // エラーメッセージを格納する変数
        String errorMessage = null;

        // 3. サーバー側での簡単なバリデーション（念のため）
        if (username == null || username.isEmpty() || 
            email == null || email.isEmpty() || 
            password == null || password.isEmpty()) {
            
            errorMessage = "全ての項目を入力してください。";
        } else if (password.length() > 10) {
            // テーブル定義 VARCHAR(10) に合わせた制限
            errorMessage = "パスワードは10文字以内で設定してください。";
        }

        // バリデーションエラーがある場合は登録処理をせずに戻す
        if (errorMessage != null) {
            request.setAttribute("error", errorMessage);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // 4. DAOの呼び出し
        LoginDao dao = new LoginDao();

        try {
            // メールアドレスの重複チェック
            if (dao.isEmailExists(email)) {
                request.setAttribute("error", "このメールアドレスは既に登録されています。");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // データベースへの登録実行
            boolean isSuccess = dao.registerUser(username, email, password);

            if (isSuccess) {
                // 5. 登録成功：ログイン画面へリダイレクト
                // URLパラメータを付与して、ログイン画面側でメッセージを表示できるようにする
                response.sendRedirect("login.jsp?success=registered");
            } else {
                // 登録失敗（DBエラーなど）
                request.setAttribute("error", "登録処理中にエラーが発生しました。");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    // GETリクエスト（直接URLを叩かれた場合など）は登録画面を表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }
}