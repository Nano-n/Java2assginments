package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sampleDB";
        String username = "root";
        String password = "****";

        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
