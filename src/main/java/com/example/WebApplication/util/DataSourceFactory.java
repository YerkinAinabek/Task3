package com.example.WebApplication.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DataSourceFactory {
    private static final String RESOURCE_PATH = "C:\\Projects\\Task3Ultimate\\src\\main\\resources\\mysql.properties";
    private static Connection connection = null;

    private DataSourceFactory() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try (FileInputStream fileInputStream = new FileInputStream(RESOURCE_PATH)) {
                Properties prop = new Properties();
                prop.load(fileInputStream);
                Class.forName(prop.getProperty("driver"));
                connection = DriverManager.getConnection(
                        prop.getProperty("database.url"),
                        prop.getProperty("database.username"),
                        prop.getProperty("database.password")
                );
            } catch (SQLException | IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }
}
