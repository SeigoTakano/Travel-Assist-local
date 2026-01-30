package servlet;

import java.io.IOException;
import java.util.List;

import bean.Postmanage;
import dao.PostDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/post")
public class PostManageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PostDao dao = new PostDao();
        List<Postmanage> postList = dao.findAll();

        request.setAttribute("postList", postList);
        request.getRequestDispatcher("/post_manage/post_manage.jsp").forward(request, response);
    }
}
