package servlet;

import java.io.IOException;
import java.util.Properties;

import dao.LoginDao;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        
        LoginDao dao = new LoginDao();
        
        if (dao.isEmailExists(email)) {
            boolean isSent = sendResetEmail(email);
            if (isSent) {
                request.setAttribute("message", "パスワード再設定用のメールを送信しました。");
            } else {
                request.setAttribute("error", "メール送信に失敗しました。サーバーのログを確認してください。");
            }
        } else {
            request.setAttribute("error", "入力されたメールアドレスは登録されていません。");
        }
        
        request.getRequestDispatcher("login/forgot.jsp").forward(request, response);
    }

    private boolean sendResetEmail(String toEmail) {
        // 送信元のGmail設定
        final String fromEmail = "tseigo2003@gmail.com"; 
        final String appPassword = "rmzr nwke kniq doxy"; // Googleアプリパスワード
        
        // SMTPサーバーの設定
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // メールのセッションを作成（ここで session 変数を定義）
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            // 送信元（名前付き）
            message.setFrom(new InternetAddress(fromEmail, "Travel-Assist 運営チーム", "UTF-8"));
            // 宛先
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            
            // 件名
            message.setSubject("【Travel-Assist】パスワード再設定手続きのお知らせ", "UTF-8");
            
            // 再設定用リンクの作成
            String resetLink = "http://localhost:8080/TravelAssist_local/login/reset_password.jsp?email=" + toEmail;

            // 本文の構築
            StringBuilder sb = new StringBuilder();
            sb.append("Travel-Assistをご利用いただきありがとうございます。\n\n");
            sb.append("お客様のアカウントにて、パスワード再設定のリクエストを受け付けました。\n");
            sb.append("お手数ですが、以下のリンクをクリックして新しいパスワードの設定を完了してください。\n\n");
            sb.append("▼パスワード再設定ページ\n");
            sb.append(resetLink).append("\n\n");
            sb.append("※このURLの有効期限は24時間です。\n");
            sb.append("※このメールに心当たりがない場合は、本メールを破棄してください。\n\n");
            sb.append("--------------------------------------------------\n");
            sb.append("Travel-Assist 運営事務局\n");
            sb.append("--------------------------------------------------");

            message.setText(sb.toString(), "UTF-8");

            // メール送信実行
            Transport.send(message);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace(); // Eclipseのコンソールにエラー詳細を出力
            return false;
        }
    }
}