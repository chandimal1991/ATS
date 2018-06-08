
package com.ust.main;

import com.ust.command.LoginReq;
import com.ust.command.LoginResponse;
import com.ust.command.RegisterResponse;
import com.ust.command.RegistrationReq;
import com.ust.entity.User;
import com.ustack.common.services.ServiceEndpoint;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import org.json.JSONArray;

@ServiceEndpoint
public interface UserService {
    
    CompletableFuture<RegisterResponse> userRegistration(RegistrationReq req) throws SQLException,ClassNotFoundException,Exception;
    CompletableFuture<LoginResponse> userLogin(LoginReq req);
    CompletableFuture<String> addFunds(User req) throws SQLException,ClassNotFoundException;
    CompletableFuture<String> withdrawFunds(User req) throws SQLException,ClassNotFoundException;
    CompletableFuture<JSONArray> getNetworth(User req);
      
}
