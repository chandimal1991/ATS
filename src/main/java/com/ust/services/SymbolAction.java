
package com.ust.services;

import com.ust.util.JsonEncode;
import com.ust.entity.Symbol;
import com.ust.util.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;

public class SymbolAction {
  
    
    public static void updateSymbol(String symbol){
        
        String bidTable = symbol.toLowerCase()+"bid";
        String askTable = symbol.toLowerCase()+"ask";
        String bid="0.00";
        String bidQuantity="0";
        String ask="0.00";
        String askQuantity="0";
        
        try{
            Connection con = DataBase.getCon();
            String sql = "SELECT * FROM "+bidTable+" LIMIT 1";
            Statement stmt  = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                bidQuantity = rs.getString(3);
                bid = rs.getString(4);
                
            }
            
            String sql1 = "SELECT * FROM "+askTable+" LIMIT 1";
            Statement stmt1  = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery(sql1);
            while(rs1.next()){
                askQuantity = rs1.getString(3);
                ask = rs1.getString(4);   
            }
            
            
            String sql2 = "UPDATE symbol SET bid=?,bidQuantity=?,ask=?,"
                    + "askQuantity=? WHERE name=?";
            PreparedStatement ps = con.prepareStatement(sql2);
            ps.setString(1, bid);
	        ps.setString(2, bidQuantity);
            ps.setString(3, ask);
            ps.setString(4, askQuantity);
            ps.setString(5, symbol);
            ps.executeUpdate();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static JSONArray displaySymbol(){
        
        try{
            Connection con = DataBase.getCon();
            String sql = "SELECT * FROM symbol";
            Statement stmt  = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            JSONArray result = JsonEncode.convertToJSON(rs);
            return result;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;     
    }
    
    public static String deleteSymbol(Symbol req) throws SQLException,
            ClassNotFoundException{
        Connection con = null;
        PreparedStatement ps = null;
        String symbol = req.getName();
        try{
            con = DataBase.getCon();
            String sql = "DELETE FROM symbol WHERE name = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1,symbol);
            ps.executeUpdate();
            
            String sql1 = "DROP TABLE "+symbol.toLowerCase()+"ask";
            ps = con.prepareStatement(sql1);
            ps.executeUpdate();
            
            String sql2 = "DROP TABLE "+symbol.toLowerCase()+"bid";
            ps = con.prepareStatement(sql2);
            ps.executeUpdate();
            
            return "success";
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(ps != null){
                ps.close();
            }
        }
        return "fail";
    }
    
    public static String editSymbol(Symbol req) throws SQLException,
            ClassNotFoundException{
      Connection con = null;
      PreparedStatement ps = null;
      String symbol = req.getId();
      String name = req.getName();
      String description = req.getDescription();
      
      try{
        con = DataBase.getCon();
        if(description.equals("") && !name.equals("")){
          String sql = "UPDATE symbol SET name=? WHERE name=?";
          ps = con.prepareStatement(sql);
          ps.setString(1,name);
          ps.setString(2,symbol);
          ps.executeUpdate();
          return "success";
        }
        else if(!description.equals("") && name.equals("")){
          String sql = "UPDATE symbol SET description=? WHERE name=?";
          ps = con.prepareStatement(sql);
          ps.setString(1,description);
          ps.setString(2,symbol);
          ps.executeUpdate();
          return "success";
        }
        else if(!description.equals("") && !name.equals("")){
          String sql = "UPDATE symbol SET name=?,description=?"
                    + "WHERE name=?";
            ps = con.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,description);
            ps.setString(3,symbol);
            ps.executeUpdate();
            return "success";
        }
        else{
          return "fail";
        }
          
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(ps != null){
                ps.close();
            }
        }
      return "update fail";
    }
    
    public static String addSymbol(Symbol req) throws SQLException,
            ClassNotFoundException{
      Connection con = null;
      PreparedStatement ps = null;
      String id = req.getId();
      String name = req.getName();
      String description = req.getDescription();
      try{
        con = DataBase.getCon();
        if(!description.equals("") && !name.equals("") && !id.equals("")){
          String sql = "INSERT INTO symbol (id,name,description) VALUES (?,?,?)";
          ps = con.prepareStatement(sql);
          ps.setString(1,id);
          ps.setString(2,name);
          ps.setString(3,description);
          ps.executeUpdate();
          
          String sql1 = "CREATE TABLE "+name.toLowerCase()+"bid (\n" +
                        "    id int,\n" +
                        "    user varchar(12),\n" +
                        "    volume varchar(20),\n" +
                        "    price varchar(20),\n" +
                        "    time varchar(20) \n" +
                        ");";
          ps = con.prepareStatement(sql1);
          ps.executeUpdate();
          
          String sql2 = "CREATE TABLE "+name.toLowerCase()+"ask (\n" +
                        "    id int,\n" +
                        "    user varchar(12),\n" +
                        "    volume varchar(20),\n" +
                        "    price varchar(20),\n" +
                        "    time varchar(20) \n" +
                        ");";
          ps = con.prepareStatement(sql2);
          ps.executeUpdate();
          return "success";
        }
        else{
          return "fail";
        }
          
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(ps != null){
                ps.close();
            }
        }
      return "fail add symbol";
    }  
  
}
