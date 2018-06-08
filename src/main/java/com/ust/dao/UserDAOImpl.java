
package com.ust.dao;

import com.ust.entity.User;
import com.ust.util.DataBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAOImpl implements UserDAO{

    @Override
    public void save(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        String name = user.getName();
        String nic = user.getNic();
        String status = user.getStatus();
        try {
            String sql = "INSERT INTO user (nic,name,email,password,status)"
                    + "VALUES (?,?,?,?,?)";
            DataBase.updateData(sql, nic, name, email, password, status);

        }catch (Exception ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public User get(String PrimaryKey) {
        try {
            Connection con = DataBase.getCon();
            String sql = "SELECT * FROM user WHERE nic=" + PrimaryKey;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            User user = null;
            if (rs.next()) {
                String email = rs.getString(1);
                String password = rs.getString(2);
                String name = rs.getString(3);
                String nic = rs.getString(4);
                String status = rs.getString(5);
                user = new User(name,email,password,nic,status);
            }

            return user;

        } catch (ClassNotFoundException | SQLException e) {
        }

        return null;
    }  
    
}
