package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import bean.Plan;
import dao.PlanDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/plans")
public class PlanServlet extends HttpServlet {
    private PlanDaoImpl dao = new PlanDaoImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("new".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/createPlan.jsp").forward(request, response);
        } else {
            List<Plan> list = dao.findAll();
            request.setAttribute("planList", list);
            request.getRequestDispatcher("/WEB-INF/views/planList.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Plan plan = new Plan();
        plan.setPlanName(request.getParameter("planName"));
        plan.setDeparture(request.getParameter("departure"));
        plan.setDestination(request.getParameter("destination"));
        plan.setStartDate(Date.valueOf(request.getParameter("startDate")));
        plan.setEndDate(Date.valueOf(request.getParameter("endDate")));
        plan.setBudget(Integer.parseInt(request.getParameter("budget")));

        if (dao.create(plan)) {
            response.sendRedirect("plans"); // 成功したら一覧へ
        } else {
            response.sendRedirect("plans?action=new&error=1");
        }
    }
}