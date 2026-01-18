package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Plan;

public class PlanDaoImpl {
    private String url = "jdbc:mysql://localhost:3306/TravelAssist"; // DB名を変更してください
    private String user = "root";
    private String password = "travelassist";

    // プラン一覧取得
    public List<Plan> findAll() {
        List<Plan> plans = new ArrayList<>();
        String sql = "SELECT * FROM plans ORDER BY start_date DESC";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Plan p = new Plan();
                p.setId(rs.getInt("id"));
                p.setPlanName(rs.getString("plan_name"));
                p.setDeparture(rs.getString("departure"));
                p.setDestination(rs.getString("destination"));
                p.setStartDate(rs.getDate("start_date"));
                p.setEndDate(rs.getDate("end_date"));
                p.setBudget(rs.getInt("budget"));
                plans.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return plans;
    }

    // 新規登録
    public boolean create(Plan plan) {
        String sql = "INSERT INTO plans (plan_name, departure, destination, start_date, end_date, budget) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plan.getPlanName());
            pstmt.setString(2, plan.getDeparture());
            pstmt.setString(3, plan.getDestination());
            pstmt.setDate(4, plan.getStartDate());
            pstmt.setDate(5, plan.getEndDate());
            pstmt.setInt(6, plan.getBudget());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}