package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/baseball";
        String username = "root";
        String password = "root1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
