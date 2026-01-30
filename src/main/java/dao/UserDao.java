package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import DBConnection.DBConnection;
import bean.User;

public class UserDao {

    // 1. ログイン (LoginDaoと共通のハッシュ化ロジックを使用)
    public User login(String email, String password) {
        String sql = "SELECT id, email, password, username, manager_flag, admin_flag FROM users " +
                     "WHERE email = ? AND password = ? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, hashPassword(password)); // ハッシュ化して比較
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

    // 2. 全ユーザー取得 (管理者画面用)
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, email, password, username, manager_flag, admin_flag FROM users WHERE user_del IS NULL";
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

    // 3. パスワード更新 (ハッシュ化対応 + update_date更新)
    public boolean updatePasswordByUsername(String username, String newPassword) {
        String sql = "UPDATE users SET password = ?, update_date = NOW(), update_user = ? WHERE username = ? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hashPassword(newPassword)); // ハッシュ化して保存
            pstmt.setString(2, username);
            pstmt.setString(3, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // 4. メールアドレス更新
    public boolean updateEmailByUsername(String username, String newEmail) {
        String sql = "UPDATE users SET email = ?, update_date = NOW(), update_user = ? WHERE username = ? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newEmail);
            pstmt.setString(2, username);
            pstmt.setString(3, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // 5. ユーザー名更新
    public boolean updateUsername(String currentUsername, String newUsername) {
        String sql = "UPDATE users SET username = ?, update_date = NOW(), update_user = ? WHERE username = ? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUsername);
            pstmt.setString(2, currentUsername);
            pstmt.setString(3, currentUsername);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // 6. 新規登録 (ハッシュ化 + 全カラム網羅)
    public boolean addUser(User user) {
        String sql = "INSERT INTO users(" +
                     "email, password, username, manager_flag, admin_flag, " +
                     "create_date, create_user, update_date, update_user" +
                     ") VALUES(?, ?, ?, 0, 0, NOW(), ?, NOW(), ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, hashPassword(user.getPassword())); // ハッシュ化
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getUsername()); // create_user
            pstmt.setString(5, user.getUsername()); // update_user
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // 7. ユーザー削除 (物理削除)
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // 共通メソッド: パスワードハッシュ化
    private String hashPassword(String password) {
        if (password == null || password.isEmpty()) return "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("ハッシュ化に失敗しました", e);
        }
    }
    
 // 8. メールアドレス更新 (ID指定・引数2つバージョン)
    public boolean updateEmailById(int id, String newEmail) {
        // 更新者は本人なので、本来はユーザー名を入れたいところですが、
        // 引数に合わせるため update_user には email を流用するか固定値を入れます
        String sql = "UPDATE users SET email = ?, update_date = NOW(), update_user = ? WHERE id = ? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newEmail);
            pstmt.setString(2, newEmail); // 更新者として新しいメアドを仮セット
            pstmt.setInt(3, id);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return false;
    }
}