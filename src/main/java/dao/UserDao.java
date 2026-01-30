package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DBConnection.DBConnection;
import bean.User;

public class UserDao {

    // データ取得（全件）
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, email, password, username FROM users WHERE user_del IS NULL";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("username")
                );
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // データ取得（IDで検索）
    public User getUserById(int id) {
        String sql = "SELECT id, email, password, username FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("username")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // データ追加
    public boolean addUser(User user) {
        String sql = "INSERT INTO users(email, password, username) VALUES(?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getUsername());
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // データ更新
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET email=?, password=?, username=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getUsername());
            pstmt.setInt(4, user.getId());
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // データ削除
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
 // username 更新（ID基準）
    public boolean updateUsernameById(int id, String username) {
        String sql = "UPDATE users SET username=? WHERE id=? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // email 更新（ID基準）
    public boolean updateEmailById(int id, String email) {
        String sql = "UPDATE users SET email=? WHERE id=? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // password 更新（ID基準）
    public boolean updatePasswordById(int id, String password) {
        String sql = "UPDATE users SET password=? WHERE id=? AND user_del IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, password);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
 // 検索（ID / email / username の部分一致）
    public List<User> searchUsers(String keyword) {
        List<User> list = new ArrayList<>();

        String sql =
            "SELECT id, email, password, username " +
            "FROM users " +
            "WHERE user_del IS NULL " +
            "AND (" +
            "CAST(id AS CHAR) LIKE ? " +
            "OR email LIKE ? " +
            "OR username LIKE ?" +
            ")";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String like = "%" + keyword + "%";
            pstmt.setString(1, like);
            pstmt.setString(2, like);
            pstmt.setString(3, like);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("username")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    
    
    
 // 論理削除
    public boolean logicalDeleteUser(int id) {
        String sql = "UPDATE users SET user_del = NOW() WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
 // 管理者のみ取得
    public List<User> getManagerUsers() {
        List<User> list = new ArrayList<>();

        String sql =
            "SELECT id, email, password, username, manager_flag " +
            "FROM users " +
            "WHERE user_del IS NULL AND manager_flag = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("username"),
                    rs.getInt("manager_flag")
                );
                list.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
 // 管理者検索
    public List<User> searchManagerUsers(String keyword) {
        List<User> list = new ArrayList<>();

        String sql =
            "SELECT id, email, password, username, manager_flag " +
            "FROM users " +
            "WHERE user_del IS NULL " +
            "AND manager_flag = 1 " +
            "AND (" +
            "CAST(id AS CHAR) LIKE ? " +
            "OR email LIKE ? " +
            "OR username LIKE ?" +
            ")";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String like = "%" + keyword + "%";
            pstmt.setString(1, like);
            pstmt.setString(2, like);
            pstmt.setString(3, like);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("username"),
                    rs.getInt("manager_flag")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public User findById(int userId) {

        User user = null;

        String sql = "SELECT id, email, username FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();

             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("username")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }




}
