package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import DBConnection.DBConnection;
import bean.Route; 

public class RouteDao {

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
            e.printStackTrace();
            return false;
        }
    }

    // ★ 削除メソッド
    public boolean deleteRoute(int routeId) {
        String sql = "DELETE FROM route_history WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, routeId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Route> getRoutesByUserId(int userId) {
        List<Route> routeList = new ArrayList<>();
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
            e.printStackTrace();
        }
        return routeList;
    }
    
    public List<Route> getRecentRoutes(int userId, int limit) {
        List<Route> routeList = new ArrayList<>();
        // カラム名を id -> route_number に修正
        String sql = "SELECT * FROM route_history WHERE user_id = ? ORDER BY searched_at DESC LIMIT ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setInt(2, limit);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Route route = new Route();
                    // ここを修正：rs.getInt("id") ではなく "route_number"
                    route.setId(rs.getInt("route_number")); 
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
            e.printStackTrace();
        }
        return routeList;
    }
}