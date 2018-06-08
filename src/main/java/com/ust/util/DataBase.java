package com.ust.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    static Connection con = null;
    static String url = "jdbc:mysql://localhost:3306/stockexchange";

    public static Connection getCon() throws SQLException, 
            ClassNotFoundException {
        if (con == null) {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, "root", "");
        }
        return con;
    }

    public static ResultSet getData(String sql) throws Exception {
        Connection con = DataBase.getCon();
        Statement stmt = con.createStatement();
        return stmt.executeQuery(sql);
    }

    public static void updateData(String sql, String arg01) throws Exception {
        Connection con = DataBase.getCon();
        PreparedStatement ps;
        ps = con.prepareStatement(sql);
        ps.setString(1, arg01);
        ps.executeUpdate();
    }

    public static void updateData(String sql, String arg01, String arg02) 
            throws Exception {
        Connection con = DataBase.getCon();
        PreparedStatement ps;
        ps = con.prepareStatement(sql);
        ps.setString(1, arg01);
        ps.setString(2, arg02);
        ps.executeUpdate();
    }

    public static void updateData(String sql, String arg01, 
            String arg02, String arg03) throws Exception {
        Connection con = DataBase.getCon();
        PreparedStatement ps;
        ps = con.prepareStatement(sql);
        ps.setString(1, arg01);
        ps.setString(2, arg02);
        ps.setString(3, arg03);
        ps.executeUpdate();
    }

    public static void updateData(String sql, double arg01, double arg02, 
            String arg03) throws Exception {
        Connection con = DataBase.getCon();
        PreparedStatement ps;
        ps = con.prepareStatement(sql);
        ps.setDouble(1, arg01);
        ps.setDouble(2, arg02);
        ps.setString(3, arg03);
        ps.executeUpdate();
    }

    public static void updateData(String sql, String arg01, String arg02, 
            String arg03, String arg04) throws Exception {
        Connection con = DataBase.getCon();
        PreparedStatement ps;
        ps = con.prepareStatement(sql);
        ps.setString(1, arg01);
        ps.setString(2, arg02);
        ps.setString(3, arg03);
        ps.setString(4, arg04);
        ps.executeUpdate();
    }

    public static void updateData(String sql, String arg01, String arg02, 
            String arg03, String arg04, String arg05) throws Exception {
        Connection con = DataBase.getCon();
        PreparedStatement ps;
        ps = con.prepareStatement(sql);
        ps.setString(1, arg01);
        ps.setString(2, arg02);
        ps.setString(3, arg03);
        ps.setString(4, arg04);
        ps.setString(5, arg05);
        ps.executeUpdate();
    }

}
