
package com.ust.main;

import com.ust.command.LoginReq;
import com.ust.command.LoginResponse;
import com.ust.command.RegisterResponse;
import com.ust.command.RegistrationReq;
import com.ust.entity.User;
import com.ust.services.UserAction;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import org.json.JSONArray;

public class UserServiceImpl implements UserService{
    
     @Override
    public CompletableFuture<RegisterResponse> userRegistration(RegistrationReq req) 
            throws SQLException,ClassNotFoundException,Exception{
        return CompletableFuture.completedFuture(UserAction.RegisterUser(req));
    }
    @Override
    public CompletableFuture<LoginResponse> userLogin(LoginReq req){
        return CompletableFuture.completedFuture(UserAction.loginUser(req));
    }
    
     @Override
    public CompletableFuture<String> addFunds (User req)
            throws SQLException,ClassNotFoundException{
        return CompletableFuture.completedFuture(UserAction.addFunds(req));
    }
    
    @Override
    public CompletableFuture<String> withdrawFunds (User req)
            throws SQLException,ClassNotFoundException{
        return CompletableFuture.completedFuture(UserAction.withdrawFunds(req));
    }
    
    @Override
     public CompletableFuture<JSONArray> getNetworth(User req){
        return CompletableFuture.completedFuture(UserAction.getNetworth(req)); 
    }
    
}
