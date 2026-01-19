package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/TravelAssist?useSSL=false&serverTimezone=Asia/Tokyo";
    private static final String USER = "root";
    private static final String PASSWORD = "travelassist";

    // 接続用のメソッド
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ★これが必要です！プログラムを実行するためのメインメソッド
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("データベース「TravelAssist」への接続に成功しました！");
            }
        } catch (SQLException e) {
            System.err.println("接続に失敗しました。");
            e.printStackTrace();
        }
    }
}