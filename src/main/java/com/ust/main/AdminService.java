
package com.ust.main;

import com.ust.entity.User;
import com.ust.entity.Order;
import com.ust.entity.Symbol;
import com.ustack.common.services.ServiceEndpoint;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@ServiceEndpoint
public interface AdminService {
    
    CompletableFuture<String> userSuspend(User req) throws SQLException,ClassNotFoundException,Exception;
    CompletableFuture<String> userActive(User req) throws SQLException,ClassNotFoundException,Exception;
    CompletableFuture<String> userRemove(User req) throws SQLException,ClassNotFoundException,Exception;
    CompletableFuture<String> addStocks(Order req) throws SQLException,ClassNotFoundException,Exception;
    CompletableFuture<String> deleteSymbol(Symbol req) throws SQLException,ClassNotFoundException;
    CompletableFuture<String> editSymbol(Symbol req) throws SQLException,ClassNotFoundException;
    CompletableFuture<String> addSymbol(Symbol req) throws SQLException,ClassNotFoundException;
    
}
