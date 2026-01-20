package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DBConnection.DBConnection;

/**
 * ユーザー認証および登録に関するデータベース操作を担当するクラス
 */
public class LoginDao {
    
    /**
     * ログイン認証を行い、成功した場合はユーザー名を返します。
     * @param email メールアドレス
     * @param password パスワード
     * @return 認証成功時はusername、失敗時はnull
     */
    public String getUsernameByLogin(String email, String password) {
        String sql = "SELECT username FROM users WHERE email = ? AND password = ? AND user_del IS NULL";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                }
            }
        } catch (Exception e) {
            // DBConnectionからの例外をまとめてキャッチ
            e.printStackTrace();
        }
        return null;
    }

    /**
     * メールアドレスが既にデータベースに存在するかチェックします。
     * @param email チェックするメールアドレス
     * @return 存在すればtrue、存在しなければfalse
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
     * ユーザーを新規登録します。
     * @param username ユーザー名
     * @param email メールアドレス
     * @param password パスワード
     * @return 登録成功ならtrue、失敗ならfalse
     */
    public boolean registerUser(String username, String email, String password) {
        String sql = "INSERT INTO users (username, email, password, create_user, update_user) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, username); // create_user
            pstmt.setString(5, username); // update_user
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}