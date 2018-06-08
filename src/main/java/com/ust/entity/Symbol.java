package com.ust.entity;

public class Symbol {

  private String id;
  private String name;
  private String description;
  private double bid;
  private int bidQuantity;
  private double ask;
  private int askQuantity;
  private double last;
  private int lastQuantity;

  public Symbol(String id, String name, String description, double bid, 
          int bidQuantity, double ask, int askQuantity, double last, int lastQuantity) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.bid = bid;
    this.bidQuantity = bidQuantity;
    this.ask = ask;
    this.askQuantity = askQuantity;
    this.last = last;
    this.lastQuantity = lastQuantity;
  }

  

  // getters
  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

}
