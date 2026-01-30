package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBConnection.DBConnection;
import bean.Post;

public class PostDao {

    // 投稿一覧をすべて取得
    public List<Post> findAll() {
        List<Post> postList = new ArrayList<>();
        String sql = "SELECT * FROM post ORDER BY post_number DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setPostNumber(rs.getInt("post_number"));
                post.setTitle(rs.getString("title"));
                post.setImpression(rs.getString("impression"));
                post.setUsername(rs.getString("username"));
                post.setImagepass(rs.getString("imagepass"));
                post.setPostDate(rs.getDate("post_date"));
                postList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return postList;
    }

    // 新規投稿を保存
    public boolean create(Post post) {
        // create_date, update_date は NOW() で自動入力
        String sql = "INSERT INTO post (title, impression, username, imagepass, post_date, create_date, create_user, update_date, update_user) " +
                     "VALUES (?, ?, ?, ?, CURDATE(), NOW(), ?, NOW(), ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setString(1, post.getTitle());
            pStmt.setString(2, post.getImpression());
            pStmt.setString(3, post.getUsername());
            pStmt.setString(4, post.getImagepass());
            pStmt.setString(5, post.getCreateUser()); // 作成者
            pStmt.setString(6, post.getUpdateUser()); // 更新者

            int result = pStmt.executeUpdate();
            return (result == 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ログインユーザーの投稿のみ取得
    public List<Post> findByUsername(String username) {
        List<Post> postList = new ArrayList<>();
        String sql = "SELECT * FROM post WHERE username = ? ORDER BY post_number DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setString(1, username);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                Post post = new Post();
                post.setPostNumber(rs.getInt("post_number"));
                post.setTitle(rs.getString("title"));
                post.setImpression(rs.getString("impression"));
                post.setUsername(rs.getString("username"));
                post.setImagepass(rs.getString("imagepass"));
                post.setPostDate(rs.getDate("post_date"));
                postList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postList;
    }

}