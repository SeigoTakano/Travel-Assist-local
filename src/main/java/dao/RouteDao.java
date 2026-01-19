package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import DBConnection.DBConnection;
// Route情報を入れるためのBean/Modelクラス（後述）
import bean.Route; 

public class RouteDao {

    // --- 保存用メソッド (既存のもの) ---
    public boolean insertRoute(int userId, String start, String end, String polyline, boolean isFavorite) {
        String sql = "INSERT INTO route_history (user_id, start_name, end_name, route_polyline, spot_details, is_favorite, searched_user, searched_at, updated_user, updated_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Timestamp now = new Timestamp(System.currentTimeMillis());

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setString(2, start);
            pstmt.setString(3, end);
            pstmt.setString(4, polyline); 
            pstmt.setString(5, "なし");
            pstmt.setBoolean(6, isFavorite);
            pstmt.setInt(7, userId);
            pstmt.setTimestamp(8, now);
            pstmt.setInt(9, userId);
            pstmt.setTimestamp(10, now);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("【SQLエラー】保存に失敗したぜ: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // --- ★今回追加する：ログインユーザー専用の取得メソッド ---
    public List<Route> getRoutesByUserId(int userId) {
        List<Route> routeList = new ArrayList<>();
        // WHERE user_id = ? でログインユーザーだけに絞り込み、最新順(DESC)で並べる
        String sql = "SELECT * FROM route_history WHERE user_id = ? ORDER BY searched_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Route route = new Route();
                    route.setId(rs.getInt("id"));
                    route.setUserId(rs.getInt("user_id"));
                    route.setStartName(rs.getString("start_name"));
                    route.setEndName(rs.getString("end_name"));
                    route.setPolyline(rs.getString("route_polyline"));
                    route.setFavorite(rs.getBoolean("is_favorite"));
                    route.setSearchedAt(rs.getTimestamp("searched_at"));
                    
                    routeList.add(route);
                }
            }
        } catch (SQLException e) {
            System.err.println("【SQLエラー】データ取得に失敗したぜ: " + e.getMessage());
            e.printStackTrace();
        }
        return routeList;
    }
}