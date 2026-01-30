package servlet;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson; // Gson ライブラリ必要

import bean.Post;
import dao.PostDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/my_post_data")
public class MyPostDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッション確認
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"ログインしてください\"}");
            return;
        }

        String username = (String) session.getAttribute("username");

        // DBからユーザーの投稿を取得
        PostDao postDao = new PostDao();
        List<Post> postList = postDao.findByUsername(username);

        // JSONで返す
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String json = new Gson().toJson(postList);
        response.getWriter().write(json);
    }
}
