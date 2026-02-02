package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DBConnection.DBConnection;
import bean.Inquiry;

public class InquiryDao {

    public boolean insertInquiry(Inquiry inquiry) {

        String sql = """
            INSERT INTO inquiry
            (email, name, inquiry_detail, create_date, create_user, update_date, update_user)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, inquiry.getEmail());
            ps.setString(2, inquiry.getName());
            ps.setString(3, inquiry.getMessage()); // ★ message

            ps.setTimestamp(4, inquiry.getCreatedAt());
            ps.setString(5, inquiry.getName());     // create_user は name を流用
            ps.setTimestamp(6, inquiry.getUpdatedAt());
            ps.setString(7, inquiry.getName());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
