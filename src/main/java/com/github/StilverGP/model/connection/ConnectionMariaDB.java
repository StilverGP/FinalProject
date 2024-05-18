package com.github.StilverGP.model.connection;

import com.github.StilverGP.App;
import com.github.StilverGP.utils.XMLManager;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMariaDB {
    private final static String FILE="connection.xml";
    private static ConnectionMariaDB _instance;
    private static Connection conn;

    private ConnectionMariaDB() {
        InputStream inputStream = App.class.getResourceAsStream(FILE);
        ConnectionProperties properties = (ConnectionProperties) XMLManager.readXML(new ConnectionProperties(), inputStream);
        String url = properties.getURL();
        try {
            conn = DriverManager.getConnection(url, properties.getUser(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            conn=null;
        }
    }

    public static Connection getConnection() {
        if(_instance==null) {
            _instance = new ConnectionMariaDB();
        }
        return conn;
    }

    public static void closeConnection() {
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
