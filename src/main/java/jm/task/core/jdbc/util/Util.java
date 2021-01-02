package jm.task.core.jdbc.util;
import java.sql.*;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/task";
    private static final String user = "root";
    private static final String password = "Aventador10";
    private static final String driver = "com.mysql.jdbc.Driver";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("connection error");
        }
        return connection;
    }

}
