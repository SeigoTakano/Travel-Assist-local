package servlet;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/user/list")
public class UserListServlet extends HttpServlet {
	
	pageContext.request.contextPath
	

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {


        RequestDispatcher rd =
            request.getRequestDispatcher(
                "/user_info_list/user_info_list.jsp");
        rd.forward(request, response);
    }
}
