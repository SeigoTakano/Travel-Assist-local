package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/read_post") // ブラウザからはこのURLでアクセスする
public class ReadPostPageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // ただJSPへ飛ばす（フォワードする）だけ！
        // これにより、直接JSPファイルを指定しなくても画面が開けるようになります
        request.getRequestDispatcher("/read_post/read_post.jsp").forward(request, response);
    }
}