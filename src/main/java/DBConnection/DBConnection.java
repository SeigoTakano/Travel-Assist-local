package DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {

    public static Connection getConnection() throws SQLException {
        try {
            // context.xmlから設定を読み込むための定型文
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            
            // context.xmlの name="jdbc/travelassist" と一致させる
            DataSource ds = (DataSource) envContext.lookup("jdbc/travelassist");
            
            return ds.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
            throw new SQLException("データベースの設定が見つかりません。");
        }
    }
}