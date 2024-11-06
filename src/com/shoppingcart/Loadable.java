package com.shoppingcart;

import com.shoppingcart.cart.Cart;
import com.shoppingcart.db.DatabaseUtils;
import com.shoppingcart.product.Product;
import com.shoppingcart.stock.Stock;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public interface Loadable {
    default void loadFromDB(String option) {
        // Connecting to DB

        Properties props = new Properties();
        try {
            props.load(new FileInputStream("products.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("url");
        String user = props.getProperty("username");
        String password = props.getProperty("password");
        String sql = "SELECT * FROM %s".formatted(option.equals("stock") ? "shoppingsystem.products" :
                "shoppingsystem.cart");
        try(ResultSet rs = DatabaseUtils.executeQuery(sql, user, password)) {
            while(rs.next()) {
                String name = rs.getString(1).toUpperCase();
                double price = rs.getDouble(2);
                String isOnSale = rs.getString(3);
                int discountPercentage = rs.getInt(4);
                String hasWarranty = rs.getString(5);
                String category = rs.getString(6);
                int quantity = rs.getInt(7);
                SixParamFunction<String, Double, String, Integer, String, Integer> action= MenuOption.
                        getTypeOptionsMap().get(category.equals("groceries") ? 1 :
                                category.equals("electronics") ? 2 : 3);
                System.out.println(quantity);
                Product product = action.apply(name, price, isOnSale, discountPercentage, hasWarranty, quantity);
                if (option.equals("stock")) {
                    Stock.add(product);
                    continue;
                }
                Cart.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}