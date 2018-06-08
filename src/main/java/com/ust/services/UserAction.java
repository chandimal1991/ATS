package com.ust.services;

import com.ust.command.LoginReq;
import com.ust.command.LoginResponse;
import com.ust.command.RegisterResponse;
import com.ust.command.RegistrationReq;
import com.ust.dao.UserDAOImpl;
import com.ust.entity.User;
import com.ust.util.DataBase;
import com.ust.util.JsonEncode;
import com.ust.validation.PasswordValidation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;

public class UserAction {

  // user save method
  public static RegisterResponse RegisterUser(RegistrationReq req) throws SQLException,
          ClassNotFoundException,
          Exception {

    
    User user = new UserDAOImpl().get(req.getNic());
    if(user != null){
          return new RegisterResponse("User_Exist");
      }
      else if(!(req.getEmail().contains("@")) || !(req.getEmail().contains("."))){
          return new RegisterResponse("Invalid_Email");
      }
      
      else if(!PasswordValidation.validate(req.getPassword())){
          return new RegisterResponse("Invalid_Password");
      }
      
      else{
          user = new User(req.getName(),req.getEmail(),req.getPassword(),req.getNic(),req.getStatus());
          new UserDAOImpl().save(user);
          return new RegisterResponse("User_Registered");
      }

  }

  // user login method
  public static LoginResponse loginUser(LoginReq req) {

    String nic = req.getNic();
    String password = req.getPassword();
    
    User user = new UserDAOImpl().get(req.getNic());
    
    if(req.getNic().equals("admin")&& req.getPassword().equals("admin")){
        return new LoginResponse("Admin Login");
    }
    
    else if(user == null){
        return new LoginResponse("User Doesnt Exist");
    }
    
    else if(!(user.getPassword().equals(req.getPassword()))){
        System.out.println(req.getPassword()+"+"+user.getPassword());
        return new LoginResponse("Incorrect Password");
    }
    
    else{
        return new LoginResponse("Login Success");
    }
    
  }

  public static JSONArray displayUser() {

    try {
      Connection con = DataBase.getCon();
      String sql = "SELECT * FROM user";
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      JSONArray result = JsonEncode.convertToJSON(rs);
      return result;

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

  public static String addFunds(User req) throws SQLException, ClassNotFoundException {

    Connection con;
    PreparedStatement ps = null;
    String nic = req.getNic();
    double funds = Double.parseDouble(req.getFunds());
    System.out.println(nic);

    try {

      con = DataBase.getCon();
      String sql = "UPDATE user SET funds = funds + ? "
              + "where nic=?";
      ps = con.prepareStatement(sql);
      ps.setDouble(1, funds);
      ps.setString(2, nic);
      ps.executeUpdate();
      return "success";

    } catch (ClassNotFoundException | SQLException e) {
        throw new RuntimeException(e);
    } finally {
      if (ps != null) {
        ps.close();
      }
    }
  }

  public static String withdrawFunds(User req) throws SQLException, ClassNotFoundException {

    Connection con;
    PreparedStatement ps = null;
    String nic = req.getNic();
    double funds = Double.parseDouble(req.getFunds());

    try {

      con = DataBase.getCon();
      String sql = "UPDATE user SET funds = funds - ? "
              + "where nic=?";
      ps = con.prepareStatement(sql);
      ps.setDouble(1, funds);
      ps.setString(2, nic);
      ps.executeUpdate();
      return "success";

    } catch (ClassNotFoundException | SQLException e) {
        throw new RuntimeException(e);
    } finally {
      if (ps != null) {
        ps.close();
      }
    }
  }

  public static JSONArray getNetworth(User user) {

    String nic = user.getNic();

    try {
      Connection con = DataBase.getCon();
      String sql = "SELECT * FROM user where nic =" + nic;
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      JSONArray result = JsonEncode.convertToJSON(rs);
      return result;

    } catch (Exception e) {
        throw new RuntimeException(e);
    }

  }

  public static JSONArray displayStockHoldings(User req) {

    String user = req.getNic();

    try {
      Connection con = DataBase.getCon();
      String sql = "SELECT * FROM stocks where user = " + user;
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      JSONArray result = JsonEncode.convertToJSON(rs);
      return result;

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }
}
