package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author khang
 */
public class DBConnect {

    public static Connection getConnection() {
        Connection conn;
        String server = "localhost:1433";
        String database = "DE"; 
        String user = "sa";
        String password = "123";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + server + ";databaseName=" + database + ";user=" + user + ";password=" + password + ";encrypt=true;trustServerCertificate=true;";
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            conn = null;
        }
        return conn;
    }
      
}
