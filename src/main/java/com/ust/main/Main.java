
package com.ust.main;
import com.ust.entity.User;
import com.ust.entity.Order;
import com.ust.entity.Symbol;
import com.ustack.common.services.ServiceEndpoint;
import java.util.concurrent.CompletableFuture;
import org.json.JSONArray;

@ServiceEndpoint
public interface Main {
    
    CompletableFuture<String> orderPlace(Order req);
    CompletableFuture<JSONArray> displaySymbol(String req);
    CompletableFuture<JSONArray> displayStocksHoldings(User req);
    CompletableFuture<JSONArray> displayUser(String req);
    CompletableFuture<JSONArray> orderbookBuy(Symbol req);
    CompletableFuture<JSONArray> orderbookSell(Symbol req);
}
