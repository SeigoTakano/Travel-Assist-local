package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DBConnection.DBConnection;
import bean.Schedule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/records")
public class RecordsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Schedule> recordsList = new ArrayList<>();

        String sql = """
            SELECT title, date
            FROM schedule
            WHERE `delete` = false
            ORDER BY date
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Schedule s = new Schedule();
                s.setTitle(rs.getString("title"));
                s.setDate(rs.getInt("date"));
                recordsList.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("recordsList", recordsList);
        request.getRequestDispatcher("/mypage/records.jsp")
               .forward(request, response);
    }
}
