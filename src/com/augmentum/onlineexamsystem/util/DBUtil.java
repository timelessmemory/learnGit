package com.augmentum.onlineexamsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.augmentum.onlineexamsystem.exception.CustomDBException;

public class DBUtil {
    private static final String DRIVER = "jdbc.driver";
    private static final String URL = "jdbc.url";
    private static final String USER = "jdbc.user";
    private static final String PASSWORD = "jdbc.password";

    public static Connection getConnection() {
        try {
            Class.forName(JdbcPropertiesUtil.getProperty(DRIVER));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JdbcPropertiesUtil.getProperty(URL),
                    JdbcPropertiesUtil.getProperty(USER), JdbcPropertiesUtil.getProperty(PASSWORD));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CustomDBException();
        }
        return connection;
    }

    public static void close(Connection connection, PreparedStatement pStatement, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setAutoCommit(Connection connection, boolean flag) {
        try {
            connection.setAutoCommit(flag);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void commit(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String convertSpecialCharacter(String condition) {
        if (condition != null) {
            condition = condition.replace("\\", "\\\\");
            condition = condition.replace("%", "\\%");
            condition = condition.replace("_", "\\_");
        }
        return condition;
    }
}
