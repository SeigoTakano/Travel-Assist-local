package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import bean.Post;
import dao.PostDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/read_post_data")
public class ReadPostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // DAOで投稿一覧を取得
        PostDao dao = new PostDao();
        List<Post> postList = dao.findAll();
        
        // JSON形式でレスポンスを返す設定
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // GsonライブラリでリストをJSON文字列に変換
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(postList));
        out.flush();
    }
}