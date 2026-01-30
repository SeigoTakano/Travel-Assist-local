package servlet;
 
import java.io.IOException;
import java.util.List;

import bean.Inquiry;
import dao.InquiryManageDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 
@WebServlet("/inquiry_manage")
public class InquiryManageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        // パラメータ取得
        String category = request.getParameter("category");
        String keyword = request.getParameter("keyword");
 
        InquiryManageDao dao = new InquiryManageDao();
        List<Inquiry> inquiryList = dao.getInquiries(category, keyword);
 
        // JSPにデータをセット
        request.setAttribute("inquiryList", inquiryList);
        request.setAttribute("selectedCategory", category);
        request.setAttribute("keyword", keyword);
 
        request.getRequestDispatcher("/inquiry_manage/inquiry_manage.jsp").forward(request, response);
    }
}
 