package servlet;

import java.io.IOException;

import bean.User;
import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




@WebServlet("/manager_update")
public class ManagerUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        String email = request.getParameter("email");
        String username = request.getParameter("username");

        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setUsername(username);

        UserDao dao = new UserDao();
        dao.updateUser(user);

        response.sendRedirect("manager_info_manage");
    }
}
