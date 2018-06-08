
package com.ust.main;

import com.ust.entity.User;
import com.ust.services.AdminAction;
import com.ust.entity.Order;
import com.ust.entity.Symbol;
import com.ust.services.SymbolAction;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class AdminServiceImpl implements AdminService{
    
    @Override
    public CompletableFuture<String> userSuspend(User req)
            throws SQLException,ClassNotFoundException,Exception{
        return CompletableFuture.completedFuture(AdminAction.suspendUser(req));
    }
    @Override
    public CompletableFuture<String> userActive(User req)
            throws SQLException,ClassNotFoundException,Exception{
        return CompletableFuture.completedFuture(AdminAction.activeUser(req));
    }
    @Override
    public CompletableFuture<String> userRemove(User req)
            throws SQLException,ClassNotFoundException,Exception{
        return CompletableFuture.completedFuture(AdminAction.removeUser(req));
    }
    
    @Override
    public CompletableFuture<String> addStocks (Order req)
            throws SQLException,ClassNotFoundException,Exception{
        return CompletableFuture.completedFuture(AdminAction.addStocks(req));
    }
    
     @Override
     public CompletableFuture<String> deleteSymbol(Symbol req) 
             throws SQLException,ClassNotFoundException {
        return CompletableFuture.completedFuture(SymbolAction.deleteSymbol(req)); 
    }
    @Override
     public CompletableFuture<String> editSymbol(Symbol req) 
             throws SQLException,ClassNotFoundException {
        return CompletableFuture.completedFuture(SymbolAction.editSymbol(req)); 
    }
    @Override
    public CompletableFuture<String> addSymbol(Symbol req) 
            throws SQLException,ClassNotFoundException {
        return CompletableFuture.completedFuture(SymbolAction.addSymbol(req)); 
    }
    
}
