package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

import DBConnection.DBConnection;
import bean.User;

/**
 * ユーザー認証および登録に関するデータベース操作を担当するクラス
 */
public class LoginDao {

    /**
     * ログイン認証を行い、成功した場合はUserオブジェクトを返します。
     */
    public User login(String email, String password) {
        String sql = "SELECT id, username, email FROM users WHERE email = ? AND password = ? AND user_del IS NULL";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            // 入力されたパスワードをハッシュ化してDBと比較
            pstmt.setString(2, hashPassword(password));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * メールアドレスの重複チェック
     */
    public boolean isEmailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ユーザーを新規登録（パスワードをハッシュ化して保存）
     */
    public boolean registerUser(String username, String email, String password) {
        String sql = "INSERT INTO users (username, email, password, create_user, update_user) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            // パスワードをハッシュ化
            pstmt.setString(3, hashPassword(password));
            pstmt.setString(4, username);
            pstmt.setString(5, username);
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * パスワード更新（ハッシュ化して更新）
     */
    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE users SET password = ?, update_date = CURRENT_TIMESTAMP, update_user = ? WHERE email = ? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hashPassword(newPassword));
            pstmt.setString(2, "SYSTEM_RESET");
            pstmt.setString(3, email);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * パスワードをSHA-256でハッシュ化し、Base64形式の文字列で返す内部メソッド
     */
    private String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("ハッシュ化に失敗しました", e);
        }
    }
}