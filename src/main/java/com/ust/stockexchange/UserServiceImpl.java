
package com.ust.stockexchange;

import com.ust.command.RegistrationReq;
import java.util.concurrent.CompletableFuture;

public class UserServiceImpl implements UserService{
    
    @Override
    public CompletableFuture<String> registerUser(RegistrationReq req){
        return CompletableFuture.completedFuture("Registered");
    }
}
