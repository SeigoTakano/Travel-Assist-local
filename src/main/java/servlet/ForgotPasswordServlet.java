package servlet;

import java.io.IOException;
import java.util.Properties;

import dao.LoginDao;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
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
                request.setAttribute("error", "メール送信に失敗しました。");
            }
        } else {
            request.setAttribute("error", "入力されたメールアドレスは登録されていません。");
        }
        
        request.getRequestDispatcher("login/forgot.jsp").forward(request, response);
    }

    private boolean sendResetEmail(String toEmail) {
        // ★ここに自分のGmailと16桁のアプリパスワードを入れる
        final String fromEmail = "tseigo2003@gmail.com"; 
        final String password = "rmzr nwke kniq doxy";   
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // jakarta.mail の Authenticator を使用
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // jakarta.mail の Message/MimeMessage を使用
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("【Travel-Assist】パスワード再設定のご案内", "UTF-8");
            
            String resetLink = "http://localhost:8080/TravelAssist_local/login/reset_password.jsp?email=" + toEmail;
            message.setText("以下のリンクからパスワードの再設定を行ってください。\n\n" + resetLink, "UTF-8");

            // jakarta.mail の Transport を使用
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}