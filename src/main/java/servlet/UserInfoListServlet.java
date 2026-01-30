package servlet;

import java.io.IOException;
import java.util.List;

import bean.User;
import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user_info_list")
public class UserInfoListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String keyword = request.getParameter("keyword");
    	
        // DAO呼び出し
        UserDao dao = new UserDao();
        List<User> userList = dao.getAllUsers();
        
        if (keyword == null || keyword.trim().isEmpty()) {
            // 検索なし → 全件
            userList = dao.getAllUsers();
        } else {
            // 検索あり
            userList = dao.searchUsers(keyword.trim());
        }

        // JSPに渡す
        request.setAttribute("userList", userList);

        // JSPへフォワード
        request.getRequestDispatcher(
            "/user_info_list/user_info_list.jsp"
        ).forward(request, response);
    }
    

}
