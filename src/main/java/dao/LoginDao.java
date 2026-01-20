package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DBConnection.DBConnection; // あなたの環境の接続クラス

public class LoginDao {
    
    /**
     * ログインチェック
     */
    public boolean loginCheck(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND user_del IS NULL";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // レコードがあればtrue
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * メールアドレスの重複チェック
     * @return 既に存在すればtrue
     */
    public boolean isEmailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ユーザー新規登録
     */
    public boolean registerUser(String username, String email, String password) {
        // テーブル定義に基づき、create_user, update_user を含める
        // create_date, update_date はDBのDEFAULT CURRENT_TIMESTAMPに任せる
        String sql = "INSERT INTO users (username, email, password, create_user, update_user) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, username); // create_user (登録者名)
            pstmt.setString(5, username); // update_user (更新者名)
            
            int result = pstmt.executeUpdate();
            return result > 0; // 1行以上挿入されれば成功
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}