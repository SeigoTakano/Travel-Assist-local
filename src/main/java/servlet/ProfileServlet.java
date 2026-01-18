package servlet;

import java.io.IOException;

import bean.User;
import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // テスト用
        int loginUserId = 1;

        UserDao dao = new UserDao();
        User user = null;

        try {
            user = dao.getUserById(loginUserId);
            
            if (user != null) {
                System.out.println("===== DBからユーザー取得成功 =====");
                System.out.println("ID      : " + user.getId());
                System.out.println("Username: " + user.getUsername());
                System.out.println("Email   : " + user.getEmail());
            } else {
                System.out.println("ユーザーが見つかりません（ID=" + loginUserId + "）");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user != null) {
            request.setAttribute("userid", user.getId());
            request.setAttribute("email", user.getEmail());
            request.setAttribute("username", user.getUsername());
        }

        request.getRequestDispatcher("/mypage/profile.jsp")
               .forward(request, response);
    }
}