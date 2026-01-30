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


@WebServlet("/manager_info_manage")
public class ManagerInfoManageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String keyword = request.getParameter("keyword");

        UserDao dao = new UserDao();
        List<User> userList;

        if (keyword == null || keyword.trim().isEmpty()) {
            // ★管理者全件
            userList = dao.getManagerUsers();
        } else {
            // ★管理者検索
            userList = dao.searchManagerUsers(keyword.trim());
        }

        request.setAttribute("userList", userList);

        request.getRequestDispatcher(
            "/manager_info_manage/manager_info_manage_content.jsp"
        ).forward(request, response);
    }
}
