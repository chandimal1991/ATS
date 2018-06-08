package com.ust.services;

import com.ust.entity.Order;
import com.ust.util.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class OrderAction {

    public static String orderPlace(Order req) {

        // 1.collect post parameters from request object
        //TODO seed
        System.out.println("orderplace");
        int id = new Random().nextInt(100000) + 1;
        String symbol = req.getSymbol();
        String user = req.getUser();
        String prize = req.getPrice();
        double price = Double.parseDouble(req.getPrice());
        String quantity = req.getSize(); // change data type
        int size = Integer.parseInt(req.getSize());
        double volume = Double.parseDouble(req.getSize());
        String type = req.getType();
        String side = req.getSide();
        Date currentDate = new Date();
        String time = DateFormat.getInstance().format(currentDate);
        double funds = 0.0;
        int shares = 0;
        
        //String time = date.toString();

        // 2. place an order
        try {
            Connection con = DataBase.getCon();
            if (type.equalsIgnoreCase("bid")) {
                String sql = "SELECT * FROM user WHERE nic=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, user);
                ResultSet rs = ps.executeQuery();
                // while
                if (rs.next()) {
                    funds = rs.getDouble(5);
                }
                if (funds >= (price * volume)) {
                    String sql1 = "INSERT INTO orders (id,symbol,user,price,size"
                            + ",type,time) VALUES (?,?,?,?,?,?,?)";
                    PreparedStatement ps1 = con.prepareStatement(sql1);
                    ps1.setInt(1, id);
                    ps1.setString(2, symbol);
                    ps1.setString(3, user);
                    ps1.setDouble(4, price);
                    ps1.setInt(5, size);
                    ps1.setString(6, type);
                    ps1.setString(7, time);
                    ps1.executeUpdate();

                    OrderAction.updateOrderBook(symbol, type, id, user, 
                            size, price, time);
                    OrderAction.executeLimitOrder(id, symbol, user, 
                            price, size, type, side);
                    SymbolAction.updateSymbol(symbol);
                    SymbolAction.displaySymbol();
                    return "success";
                } else {
                    return "buy order fail";
                }
            } else {
                String sql = "SELECT * FROM stocks WHERE user=? AND symbol=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, user);
                ps.setString(2, symbol);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    shares = rs.getInt(3);
                }
                if (shares >= volume) {
                    String sql1 = "INSERT INTO orders (id,symbol,user,price,size,"
                            + "type,time) VALUES (?,?,?,?,?,?,?)";
                    PreparedStatement ps1 = con.prepareStatement(sql1);
                    ps1.setInt(1, id);
                    ps1.setString(2, symbol);
                    ps1.setString(3, user);
                    ps1.setDouble(4, price);
                    ps1.setInt(5, size);
                    ps1.setString(6, type);
                    ps1.setString(7, time);
                    ps1.executeUpdate();

                    OrderAction.updateOrderBook(symbol, type, id, 
                            user, size, price, time);
                    OrderAction.executeLimitOrder(id, symbol, user, 
                            price, size, type, side);
                    SymbolAction.updateSymbol(symbol);
                    SymbolAction.displaySymbol();
                    return "success";
                } else {
                    return "sell order fail";
                }

            }

        } catch (ClassNotFoundException | SQLException e) {
        }

        return "success";
    }

    public static void updateOrderBook(String symbol, String type, int id,
            String user, int volume, Double price, String time) {

        String table = symbol.toLowerCase() + type;

        try {
            Connection con = DataBase.getCon();
            String sql = "INSERT INTO " + table + " (id,user,volume,price,time) "
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, user);
            ps.setInt(3, volume);
            ps.setDouble(4, price);
            ps.setString(5, time);
            ps.executeUpdate();

            if (type.equalsIgnoreCase("ask")) {
                String sql1 = "CREATE TABLE duplicate AS SELECT * FROM " 
                        + table + ""
                        + " ORDER BY price,time";
                PreparedStatement ps1 = con.prepareStatement(sql1);
                ps1.execute();
            } else {
                String sql1 = "CREATE TABLE duplicate AS SELECT * FROM " 
                        + table + ""
                        + " ORDER BY price DESC,time ASC";
                PreparedStatement ps1 = con.prepareStatement(sql1);
                ps1.execute();
            }

            String sql2 = "DROP TABLE " + table;
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.execute();

            String sql3 = "RENAME TABLE duplicate TO " + table;
            PreparedStatement ps3 = con.prepareStatement(sql3);
            ps3.execute();

        } catch (ClassNotFoundException | SQLException e) {
        }

    }

    public static void executeLimitOrder(int id, String symbol,
            String user, Double price,
            int size, String type, String side) {

        double bid;
        double ask = 0.0;
        int bidvolume;
        int askvolume = 0;
        int orderId = 0;
        String biduser = "";
        String askuser = "";
        double bidvalue = 0.0;
        double askvalue = 0.0;
        // check whether order is bid or ask
        if (type.equalsIgnoreCase("bid") && side.equalsIgnoreCase("limit")) {
            bid = price;
            bidvolume = size;
            // identify correct order book
            String orderBook = symbol.toLowerCase() + "ask";
            String orderBook1 = symbol.toLowerCase() + "bid";
            // check whether any ask orders below the bid price
            try {
                Connection con = DataBase.getCon();
                String sql = "SELECT * FROM " + orderBook + " LIMIT 1";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    orderId = rs.getInt(1);
                    askuser = rs.getString(2);
                    String quantity = rs.getString(3);
                    ask = rs.getDouble(4);
                    askvolume = Integer.parseInt(quantity);
                }
                // check whether any ask orders below the bid price
                if (bid >= ask) {

                    if (bidvolume == askvolume && !askuser.equals(user)) {

                        // update buy user stockholdings
                        String sql1 = "UPDATE stocks SET volume = volume + ? "
                                + "where user=? AND symbol=?";
                        PreparedStatement ps1 = con.prepareStatement(sql1);
                        ps1.setInt(1, bidvolume);
                        ps1.setString(2, user);
                        ps1.setString(3, symbol);
                        ps1.executeUpdate();

                        // update sell user stockholdings
                        String sql2 = "UPDATE stocks SET volume = volume - ? "
                                + "where user=? AND symbol=?";
                        PreparedStatement ps2 = con.prepareStatement(sql2);
                        ps2.setInt(1, askvolume);
                        ps2.setString(2, askuser);
                        ps2.setString(3, symbol);
                        ps2.executeUpdate();

                        // update sell user funds
                        FundsAction.sellUpdate(askuser, askvolume, ask);
                        // update buy user funds
                        FundsAction.buyUpdate(user, bidvolume, ask);

                        // update last trading values and quantity
                        String sql3 = "UPDATE symbol SET last = ?,"
                                + "lastQuantity = ? where name=?";
                        PreparedStatement ps3 = con.prepareStatement(sql3);
                        ps3.setDouble(1, ask);
                        ps3.setInt(2, bidvolume);
                        ps3.setString(3, symbol);
                        ps3.executeUpdate();

                        // update user portfolio values
                        String sql4 = "UPDATE stocks SET value = ? where symbol=?";
                        PreparedStatement ps4 = con.prepareStatement(sql4);
                        ps4.setDouble(1, ask);
                        ps4.setString(2, symbol);
                        ps4.executeUpdate();

                        // calculate buyer & seller stocks values
                        FundsAction.userStocksValue(user, askuser);

                        String sql5 = "DELETE FROM " + orderBook + " WHERE price=?";
                        PreparedStatement ps5 = con.prepareStatement(sql5);
                        ps5.setDouble(1, ask);
                        ps5.executeUpdate();

                        String sql6 = "DELETE FROM " + orderBook1 + " WHERE price=?";
                        PreparedStatement ps6 = con.prepareStatement(sql6);
                        ps6.setDouble(1, bid);
                        ps6.executeUpdate();

                    } else if (bidvolume > askvolume && !askuser.equals(user)) {
                        // update buy user stockholdings
                        String sql1 = "UPDATE stocks SET volume = volume + ? where "
                                + "user=? AND symbol=?";
                        PreparedStatement ps1 = con.prepareStatement(sql1);
                        ps1.setInt(1, askvolume);
                        ps1.setString(2, user);
                        ps1.setString(3, symbol);
                        ps1.executeUpdate();

                        // update sell user stockholdings
                        String sql2 = "UPDATE stocks SET volume = volume - ? where "
                                + "user=? AND symbol=?";
                        PreparedStatement ps2 = con.prepareStatement(sql2);
                        ps2.setInt(1, askvolume);
                        ps2.setString(2, askuser);
                        ps2.setString(3, symbol);
                        ps2.executeUpdate();

                        // update sell user funds
                        FundsAction.sellUpdate(askuser, askvolume, ask);
                        // update buy user funds
                        FundsAction.buyUpdate(user, askvolume, ask);

                        // update last trading values and quantity
                        String sql3 = "UPDATE symbol SET last = ?,lastQuantity = ? "
                                + "where name=?";
                        PreparedStatement ps3 = con.prepareStatement(sql3);
                        ps3.setDouble(1, ask);
                        ps3.setInt(2, askvolume);
                        ps3.setString(3, symbol);
                        ps3.executeUpdate();

                        // update user portfolio values
                        String sql4 = "UPDATE stocks SET value = ? where symbol=?";
                        PreparedStatement ps4 = con.prepareStatement(sql4);
                        ps4.setDouble(1, ask);
                        ps4.setString(2, symbol);
                        ps4.executeUpdate();

                        // calculate buyer & seller stocks values
                        FundsAction.userStocksValue(user, askuser);

                        // update order book // change database volume data type
                        String sql5 = "UPDATE " + orderBook1 + " SET volume = volume - ? "
                                + "WHERE id=?";
                        PreparedStatement ps5 = con.prepareStatement(sql5);
                        ps5.setInt(1, askvolume);
                        ps5.setInt(2, id);
                        ps5.executeUpdate();

                        String sql6 = "DELETE FROM " + orderBook + " WHERE price=?";
                        PreparedStatement ps6 = con.prepareStatement(sql6);
                        ps6.setDouble(1, bid);
                        ps6.executeUpdate();
                    }

                } else {

                }

            } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            }

        // if order is market
        } else if (type.equalsIgnoreCase("bid") && side.equalsIgnoreCase("market")) {
            bid = price;
            bidvolume = size;
            // identify correct order book
            String orderBook = symbol.toLowerCase() + "ask";
            String orderBook1 = symbol.toLowerCase() + "bid";

            try {
                Connection con = DataBase.getCon();
                String sql = "SELECT * FROM " + orderBook + " LIMIT 1";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    askuser = rs.getString(2);
                    String quantity = rs.getString(3);
                    ask = rs.getDouble(4);
                    askvolume = Integer.parseInt(quantity);
                }

                if (bidvolume == askvolume && !askuser.equals(user)) {
                    String sql1 = "UPDATE stocks SET volume = volume + ? "
                            + "where user=? AND symbol=?";
                    PreparedStatement ps1 = con.prepareStatement(sql1);
                    ps1.setInt(1, bidvolume);
                    ps1.setString(2, user);
                    ps1.setString(3, symbol);
                    ps1.executeUpdate();

                    // update sell user stockholdings
                    String sql2 = "UPDATE stocks SET volume = volume - ? "
                            + "where user=? AND symbol=?";
                    PreparedStatement ps2 = con.prepareStatement(sql2);
                    ps2.setInt(1, askvolume);
                    ps2.setString(2, askuser);
                    ps2.setString(3, symbol);
                    ps2.executeUpdate();

                    // update sell user funds
                    FundsAction.sellUpdate(askuser, askvolume, ask);
                    // update buy user funds
                    FundsAction.buyUpdate(user, bidvolume, ask);
                    // update last trading values and quantity

                    // update last trading values and quantity
                    String sql3 = "UPDATE symbol SET last = ?,"
                            + "lastQuantity = ? where name=?";
                    PreparedStatement ps3 = con.prepareStatement(sql3);
                    ps3.setDouble(1, ask);
                    ps3.setInt(2, bidvolume);
                    ps3.setString(3, symbol);
                    ps3.executeUpdate();

                    // update user portfolio values
                    String sql4 = "UPDATE stocks SET value = ? where symbol=?";
                    PreparedStatement ps4 = con.prepareStatement(sql4);
                    ps4.setDouble(1, ask);
                    ps4.setString(2, symbol);
                    ps4.executeUpdate();

                    // calculate buyer & seller stocks values
                    FundsAction.userStocksValue(user, askuser);

                    String sql5 = "DELETE FROM " + orderBook + " WHERE id=?";
                    PreparedStatement ps5 = con.prepareStatement(sql5);
                    ps5.setInt(1, orderId);
                    ps5.executeUpdate();

                    String sql6 = "DELETE FROM " + orderBook1 + " WHERE id=?";
                    PreparedStatement ps6 = con.prepareStatement(sql6);
                    ps6.setInt(1,id);
                    ps6.executeUpdate();

                }

            } catch (ClassNotFoundException | SQLException e) {

            }

        }
    }

}
