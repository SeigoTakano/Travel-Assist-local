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

    // --- 既存の読み込み処理 (GET) ---
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        PostDao dao = new PostDao();
        List<Post> postList = dao.findAll();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(postList));
        out.flush();
    }

    // --- ★追記：削除処理 (POST) ---
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // JSのbodyで送られてきた "postNumber" を取得
        String postNumStr = request.getParameter("postNumber");
        boolean success = false;

        if (postNumStr != null) {
            int postNumber = Integer.parseInt(postNumStr);
            PostDao dao = new PostDao();
            success = dao.delete(postNumber); // DAOにdeleteメソッドが必要です
        }

        // 結果をテキストで返す
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(success ? "success" : "fail");
        out.flush();
    }
}