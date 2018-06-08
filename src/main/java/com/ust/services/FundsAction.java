package com.ust.services;

import com.ust.util.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FundsAction {

  public static void buyUpdate(String user, int volume, double price) {
    double value = volume * price;
    try {
      Connection con = DataBase.getCon();
      String sql = "UPDATE user SET funds = funds - ? where nic=?";
      PreparedStatement ps = con.prepareStatement(sql);
      ps.setDouble(1, value);
      ps.setString(2, user);
      ps.executeUpdate();
    } catch (ClassNotFoundException | SQLException e) {
    }
  }

  public static void sellUpdate(String user, int volume, double price) {
    double value = volume * price;
    try {
      Connection con = DataBase.getCon();
      String sql = "UPDATE user SET funds = funds + ? where nic=?";
      PreparedStatement ps = con.prepareStatement(sql);
      ps.setDouble(1, value);
      ps.setString(2, user);
      ps.executeUpdate();
    } catch (ClassNotFoundException | SQLException e) {
    }
  }

  public static void userStocksValue(String buyer, String seller) {

    double buyerValue = 0.0;
    double sellerValue = 0.0;

    try {
      Connection con = DataBase.getCon();
      String sql = "SELECT * FROM stocks WHERE user = ?";
      PreparedStatement ps = con.prepareStatement(sql);
      ps.setString(1, buyer);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        int volume = rs.getInt(3);
        double price = rs.getDouble(4);
        buyerValue = +(volume * price);
      }

      String sql1 = "SELECT * FROM stocks WHERE user = ?";
      PreparedStatement ps1 = con.prepareStatement(sql1);
      ps1.setString(1, seller);
      ResultSet rs1 = ps1.executeQuery();
      while (rs1.next()) {
        int volume = rs1.getInt(3);
        double price = rs1.getDouble(4);
        sellerValue = +(volume * price);
      }

      String sql2 = "UPDATE user SET stocks = ? where nic=?";
      PreparedStatement ps2 = con.prepareStatement(sql2);
      ps2.setDouble(1, buyerValue);
      ps2.setString(2, buyer);
      ps2.executeUpdate();

      String sql3 = "UPDATE user SET stocks = ? where nic=?";
      PreparedStatement ps3 = con.prepareStatement(sql3);
      ps3.setDouble(1, sellerValue);
      ps3.setString(2, seller);
      ps3.executeUpdate();

    } catch (ClassNotFoundException | SQLException e) {

    }

  }

}
