
package com.ust.main;

import com.ust.entity.User;
import com.ust.services.AdminAction;
import com.ust.entity.Order;
import com.ust.services.OrderAction;
import com.ust.entity.Symbol;
import com.ust.services.SymbolAction;
import com.ust.services.UserAction;
import java.util.concurrent.CompletableFuture;
import org.json.JSONArray;

public class MainImpl implements Main {
   
    
    @Override
    public CompletableFuture<String> orderPlace(Order req){
        return CompletableFuture.completedFuture(OrderAction.orderPlace(req));
    }
    
    
   
    
    @Override
     public CompletableFuture<JSONArray> displaySymbol(String req){
        return CompletableFuture.completedFuture(SymbolAction.displaySymbol());
        
    }
    @Override
     public CompletableFuture<JSONArray> displayUser(String req){
        return CompletableFuture.completedFuture(UserAction.displayUser());
        
    }
    @Override
     public CompletableFuture<JSONArray> displayStocksHoldings(User req){
        return CompletableFuture.completedFuture(UserAction.displayStockHoldings(req));
        
    }
    @Override
     public CompletableFuture<JSONArray> orderbookBuy(Symbol req){
        return CompletableFuture.completedFuture(AdminAction.viewBidBook(req));
        
    }
    @Override
     public CompletableFuture<JSONArray> orderbookSell(Symbol req){
        return CompletableFuture.completedFuture(AdminAction.viewAskBook(req)); 
    }
    
   
      
}
