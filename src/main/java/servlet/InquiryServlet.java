package servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import bean.Inquiry;
import dao.InquiryDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/inquiry")
public class InquiryServlet extends HttpServlet {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/inquiry/inquiry.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String select = request.getParameter("select");
        String name   = request.getParameter("name");
        String email  = request.getParameter("email");
        String detail = request.getParameter("detail");

        Map<String, String> errors = new HashMap<>();

        // バリデーション
        if (select == null || select.isEmpty()) {
            errors.put("select", "お問い合わせ項目を選択してください。");
        }
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "お名前を入力してください。");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "メールアドレスを入力してください。");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            errors.put("email", "正しいメールアドレス形式で入力してください。");
        }
        if (detail == null || detail.trim().isEmpty()) {
            errors.put("detail", "お問い合わせ内容を入力してください。");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/inquiry/inquiry.jsp").forward(request, response);
            return;
        }

        // Inquiry Bean 作成
        Inquiry inquiry = new Inquiry();
        inquiry.setEmail(email);
        inquiry.setName(name);
        inquiry.setMessage(select + "： " + detail);

        Timestamp now = new Timestamp(System.currentTimeMillis());
        inquiry.setCreatedAt(now);
        inquiry.setUpdatedAt(now);

        InquiryDao dao = new InquiryDao();
        boolean saved = dao.insertInquiry(inquiry);

        if (saved) {
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            errors.put("save", "お問い合わせの保存に失敗しました。");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/inquiry/inquiry.jsp").forward(request, response);
        }
    }
}
