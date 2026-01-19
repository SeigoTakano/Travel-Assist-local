package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import DBConnection.DBConnection;

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
            pstmt.setString(4, polyline); // 地図の線のデータ
            pstmt.setString(5, "なし");     // spot_details(とりあえず空)
            pstmt.setBoolean(6, isFavorite);
            pstmt.setInt(7, userId);       // searched_user
            pstmt.setTimestamp(8, now);    // searched_at
            pstmt.setInt(9, userId);       // updated_user
            pstmt.setTimestamp(10, now);   // updated_at
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}