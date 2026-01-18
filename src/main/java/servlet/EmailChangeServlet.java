package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmailChangeServlet")
public class EmailChangeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String currentEmail = request.getParameter("currentEmail");
        String newEmail = request.getParameter("newEmail");

        // 簡単なバリデーション
        if (newEmail == null || !newEmail.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")) {
            request.setAttribute("error", "正しいメールアドレスを入力してください。");
            request.getRequestDispatcher("emailChange.jsp").forward(request, response);
            return;
        }

        // TODO: データベース更新処理
        // 例: UserDao.updateEmail(userId, newEmail);

        response.sendRedirect("profile.jsp");
    }
}
