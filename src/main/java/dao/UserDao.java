package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBConnection.DBConnection;
import bean.User;

public class UserDao {

    // 1. ログイン (管理者・マネージャーフラグ取得)
    public User login(String email, String password) {
        String sql = "SELECT id, email, password, username, manager_flag, admin_flag FROM users " +
                     "WHERE email = ? AND password = ? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"), rs.getString("email"), rs.getString("password"),
                        rs.getString("username"), rs.getBoolean("manager_flag"), rs.getBoolean("admin_flag")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // 2. 全ユーザー取得 (フラグ込み)
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, email, password, username, manager_flag, admin_flag FROM users";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new User(
                    rs.getInt("id"), rs.getString("email"), rs.getString("password"),
                    rs.getString("username"), rs.getBoolean("manager_flag"), rs.getBoolean("admin_flag")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // 3. パスワード更新
    public boolean updatePasswordByUsername(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // 4. メールアドレス更新 (★EmailChangeServletのために追加)
    public boolean updateEmailByUsername(String username, String newEmail) {
        String sql = "UPDATE users SET email = ? WHERE username = ? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newEmail);
            pstmt.setString(2, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // 5. ユーザー名更新 (プロフィール編集用などに)
    public boolean updateUsername(String currentUsername, String newUsername) {
        String sql = "UPDATE users SET username = ? WHERE username = ? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUsername);
            pstmt.setString(2, currentUsername);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // 6. 新規登録 (初期値は一般ユーザー)
    public boolean addUser(User user) {
        String sql = "INSERT INTO users(email, password, username, manager_flag, admin_flag, create_date) " +
                     "VALUES(?, ?, ?, 0, 0, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getUsername());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // 7. ユーザー削除
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}