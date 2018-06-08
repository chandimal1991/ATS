
package com.ust.entity;

public class Order {
    
    private String Id;
    private String symbol;
    private String user;
    private String price;
    private String size;
    private String type;
    private String side;
    
    // Constructor
    public Order(String symbol, String user, String price, String size, 
            String type,String side) {
        this.symbol = symbol;
        this.user = user;
        this.price = price;
        this.size = size;
        this.type = type;
        this.side = side;
    }

    // getters
    public String getId() {
        return Id;
    }
    
     public String getSymbol() {
        return symbol;
    }

    public String getUser() {
        return user;
    }

    public String getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

  public String getSide() {
    return side;
  }
    
    

   
      
}
