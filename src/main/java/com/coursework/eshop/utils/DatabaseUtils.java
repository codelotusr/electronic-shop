package com.coursework.eshop.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
    public static Connection connectDb() {
        Connection connection = null;
        String DB_URL = "jdbc:mysql://localhost:3306/eshop";
        String USER = "root";
        String PASS = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void disconnectFromDb(Connection connection, Statement statement) throws SQLException {
        try {
            if (connection != null && statement != null) {
                connection.close();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
