package servlet;

@WebServlet("/admin/menu")
public class AdminMenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // 単体JSPへ直接forward
        request.getRequestDispatcher("/admin_menu/admin_menu.jsp")
               .forward(request, response);
    }
}
