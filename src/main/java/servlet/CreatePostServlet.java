package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import bean.Post;
import dao.PostDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/create_post")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
public class CreatePostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/create_post/create_post.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        // 1. セッションからログインユーザー名を取得
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        
        // 【修正】ログインしていない場合は、投稿させずにログイン画面へ飛ばす
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp"); // パスは環境に合わせてね
            return;
        }

        try {
            // 2. フォームデータの取得
            String title = request.getParameter("title");
            String impression = request.getParameter("comment");
            // ※ request.getParameter("username") は使わず、sessionの値を使います

            // 3. 画像ファイルの保存
            Part filePart = request.getPart("imageFile");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            
            String uploadPath = getServletContext().getRealPath("/img");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            filePart.write(uploadPath + File.separator + fileName);

            // 4. PostBeanへセット
            Post post = new Post();
            post.setTitle(title);
            post.setImpression(impression);
            
            // 【重要】すべてのユーザー項目にログインユーザー名をセット
            post.setUsername(loginUser);   // 投稿者表示用
            post.setCreateUser(loginUser); // システム作成者用
            post.setUpdateUser(loginUser); // システム更新者用
            
            post.setImagepass(fileName);

            // 5. DAOでDBに保存
            PostDao dao = new PostDao();
            boolean isSuccess = dao.create(post);

            if (isSuccess) {
                // 成功したら一覧表示サーブレット（またはJSP）へ
                response.sendRedirect(request.getContextPath() + "/read_post/read_post.jsp");
            } else {
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().println("<script>alert('データベースの保存に失敗しました。'); history.back();</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "エラーが発生しました。");
        }
    }
}