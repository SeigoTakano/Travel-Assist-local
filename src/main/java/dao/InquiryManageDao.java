package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBConnection.DBConnection;
import bean.Inquiry;
 
public class InquiryManageDao {
 
    public List<Inquiry> getInquiries(String category, String keyword) {
        List<Inquiry> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT inquiry_number, email_address, name, inquiry_detail, inquiry_reply, create_date, update_date " +
            "FROM inquiry WHERE 1=1 "
        );
 
        // カテゴリがあれば条件追加
        if (category != null && !category.isEmpty()) {
            sql.append("AND inquiry_detail LIKE ? ");
        }
        // キーワードがあれば名前・メール・内容を横断検索
        if (keyword != null && !keyword.isEmpty()) {
            sql.append("AND (name LIKE ? OR email_address LIKE ? OR inquiry_detail LIKE ?) ");
        }
        sql.append("ORDER BY create_date DESC");
 
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
 
            int idx = 1;
            if (category != null && !category.isEmpty()) {
                pstmt.setString(idx++, "%" + category + "%");
            }
            if (keyword != null && !keyword.isEmpty()) {
                String kw = "%" + keyword + "%";
                pstmt.setString(idx++, kw);
                pstmt.setString(idx++, kw);
                pstmt.setString(idx++, kw);
            }
 
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Inquiry(
                        rs.getInt("inquiry_number"),
                        rs.getString("email_address"),
                        rs.getString("name"),
                        rs.getString("inquiry_detail"),
                        rs.getString("inquiry_reply"),
                        rs.getTimestamp("create_date"),
                        rs.getTimestamp("update_date")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
 