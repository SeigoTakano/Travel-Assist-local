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
    static {
        try {
            // 新しいMySQL Connector/Jのドライバクラス名
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private String url = "jdbc:mysql://localhost:3306/travelassist?useSSL=false&serverTimezone=Asia/Tokyo&characterEncoding=UTF-8";
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
    public int create(Plan plan) {
        String sql = "INSERT INTO plans (plan_name, departure, destination, start_date, end_date, budget) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            System.out.println("データベース接続成功");
            System.out.println("プラン名: " + plan.getPlanName());
            System.out.println("出発地: " + plan.getDeparture());
            System.out.println("目的地: " + plan.getDestination());
            System.out.println("開始日: " + plan.getStartDate());
            System.out.println("終了日: " + plan.getEndDate());
            System.out.println("予算: " + plan.getBudget());
            
            pstmt.setString(1, plan.getPlanName());
            pstmt.setString(2, plan.getDeparture());
            pstmt.setString(3, plan.getDestination());
            pstmt.setDate(4, plan.getStartDate());
            pstmt.setDate(5, plan.getEndDate());
            pstmt.setInt(6, plan.getBudget());
            
            int affectedRows = pstmt.executeUpdate();
            System.out.println("影響を受けた行数: " + affectedRows);

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        System.out.println("生成されたID: " + generatedId);
                        return generatedId;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLエラー: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("プラン作成失敗、0を返します");
        return 0; // 失敗した場合は0を返す
    }

    // IDでプランを取得
    public Plan findById(int id) {
        String sql = "SELECT * FROM plans WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Plan p = new Plan();
                p.setId(rs.getInt("id"));
                p.setPlanName(rs.getString("plan_name"));
                p.setDeparture(rs.getString("departure"));
                p.setDestination(rs.getString("destination"));
                p.setStartDate(rs.getDate("start_date"));
                p.setEndDate(rs.getDate("end_date"));
                p.setBudget(rs.getInt("budget"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}