package com.ust.services;

import com.ust.entity.User;
import com.ust.util.JsonEncode;
import com.ust.entity.Order;
import com.ust.entity.Symbol;
import com.ust.util.DataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;

public class AdminAction {

  public static JSONArray viewBidBook(Symbol req) {

    String symbol = req.getName();

    try {
      String sql = "SELECT id,volume,price FROM " + symbol + "bid";
      ResultSet rs = DataBase.getData(sql);
      JSONArray result = JsonEncode.convertToJSON(rs);
      return result;

    } catch (Exception e) {
    }

    return null;
  }

  public static JSONArray viewAskBook(Symbol req) {
    String symbol = req.getName();
    try {
      String sql = "SELECT id,volume,price FROM " + symbol + "ask";
      ResultSet rs = DataBase.getData(sql);
      JSONArray result = JsonEncode.convertToJSON(rs);
      return result;
    } catch (Exception e) {
    }

    return null;
  }

  public static void addSymbol(String id, String name, String description)
          throws SQLException, ClassNotFoundException, Exception {
    try {
      String sql = "INSERT INTO symbol (id,name,description) "
              + "VALUES (?,?,?)";
      DataBase.updateData(sql, id, name, description);
    } catch (SQLException e) {
    }
  }

  public static String suspendUser(User req)
          throws SQLException, ClassNotFoundException, Exception {
    String nic = req.getNic();
    String status = "suspend";
    try {
      String sql = "UPDATE user SET status = ? where nic=?";
      DataBase.updateData(sql, status, nic);
      return "success";
    } catch (SQLException e) {
    }
    return "fail";
  }

  public static String activeUser(User req)
          throws SQLException, ClassNotFoundException, Exception {
    String nic = req.getNic();
    String status = "active";
    try {
      String sql = "UPDATE user SET status = ? where nic=?";
      DataBase.updateData(sql, status, nic);
      return "success";
    } catch (SQLException e) {
    }
    return "fail";
  }

  public static String addStocks(Order req)
          throws SQLException, ClassNotFoundException, Exception {
    
    String user = req.getUser();
    String symbol = req.getSymbol().toUpperCase();
    String quantity = req.getSize();
    String price = req.getPrice();
    double funds = (Integer.parseInt(quantity)) * (Double.parseDouble(price));
    
    try {
      String sql = "INSERT INTO stocks (user,symbol,volume,value) "
              + "VALUES (?,?,?,?)";
      DataBase.updateData(sql, user, symbol, quantity, price);
      
      String sql1 = "UPDATE user SET funds = funds - ?, "
              + "stocks = stocks + ? where nic = ?";
      DataBase.updateData(sql1, funds, funds, user);
      return "success";
      
    } catch (SQLException e) {
        //throw new RuntimeException(e);
    }
    return "fail";
  }

  public static String removeUser(User req)
          throws SQLException, ClassNotFoundException, Exception {
    String nic = req.getNic();
    try {

      String sql = "DELETE FROM orders WHERE user=?";
      String sql1 = "DELETE FROM stocks WHERE user=?";
      String sql2 = "DELETE FROM user WHERE nic=?";
      DataBase.updateData(sql, nic);
      DataBase.updateData(sql1, nic);
      DataBase.updateData(sql2, nic);

      return "success";

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
  }

}
