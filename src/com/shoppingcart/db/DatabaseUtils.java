package com.shoppingcart.db;

import org.postgresql.ds.PGConnectionPoolDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class DatabaseUtils {
    private static final PGConnectionPoolDataSource dataSource = new PGConnectionPoolDataSource();

    static {
            Properties props = new Properties();
        try {
            props.load(new FileInputStream("products.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            String url = props.getProperty("url");
            dataSource.setUrl(url);
    }

    public static Connection getConnection(String user, String password) throws SQLException {
        return dataSource.getConnection(user, password);
    }

    public static int executeUpdate(String sql, String user, String password, Object... params)
            throws SQLException {
        try(Connection connection = dataSource.getConnection(user, password);
            PreparedStatement ps = connection.prepareStatement(sql)) {
        //set params
        for(int i=0; i<params.length; i++) {
            ps.setObject(i+1, params[i]);
        }

        return ps.executeUpdate();
        }
    }

    public static ResultSet executeQuery(String sql, String user, String password, Object... params)
            throws SQLException {
        Connection connection = dataSource.getConnection(user, password);
        PreparedStatement ps = connection.prepareStatement(sql);

            for(int i=0; i<params.length; i++) {
                ps.setObject(i+1, params[i]);
            }
            return ps.executeQuery();
        }
    }
    //getConnection
    //executeUpdate
    //exectueQuery
