package servlet;


import java.io.IOException;
import java.util.List;

import bean.Postmanage;
import bean.User;
import dao.PostDao;
import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user_detail")
public class UserDetailServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ① パラメータ取得
        int userId = Integer.parseInt(request.getParameter("id"));

        // ② DAO
        UserDao userDao = new UserDao();
        PostDao postDao = new PostDao();

        // ③ DBから取得
        User user = userDao.findById(userId);
        List<Postmanage> postList = postDao.findByUserId(userId);

        // ④ JSPへ渡す
        request.setAttribute("user", user);
        request.setAttribute("postList", postList);

        // ⑤ JSPへ
        request.getRequestDispatcher("/user_detail/user_detail.jsp")
               .forward(request, response);
    }
}
