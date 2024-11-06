package com.shoppingcart.cart;

import com.shoppingcart.Loadable;
import com.shoppingcart.MenuOption;
import com.shoppingcart.SixParamFunction;
import com.shoppingcart.db.DatabaseUtils;
import com.shoppingcart.product.Product;
import com.shoppingcart.stock.Stock;
import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


public class Cart <T extends Product> implements Loadable {
    private static List<Product> cart = new LinkedList<>();

    //changed
    public static void addToCart(String itemName) {
        if(!Stock.hasItem(itemName)) {
            System.out.println("Item not available in stock!");
            return;
        }

        Product product = Stock.getProduct(itemName);

        String name = product.getName();
        double price = product.getBasePrice();
        String isOnSale = product.isOnSale() ? "Y" : "N";
        int discountPercentage = product.getDiscountPercentage();
        String hasWarranty = product.hasWarranty() ? "Y" : "N";
        String category = product.getCategory();
        int quantity = 1;

        Properties props = new Properties();
        try {
            props.load(new FileInputStream("products.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("url");
        String user = props.getProperty("username");
        String password = props.getProperty("password");

        if(hasItem(itemName)) {
            // Updating cart
            Product cartProduct = cart.stream()
                            .filter(o -> o.getName().equals(itemName))
                            .toList()
                            .getFirst();

            String sql = "UPDATE shoppingsystem.cart SET quantity=? WHERE name=?";
            cartProduct.setQuantity(cartProduct.getQuantity()+1);

            try {
                DatabaseUtils.executeUpdate(sql, user, password, cartProduct.getQuantity(), cartProduct.getName());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //Updating stock
            Stock.removeItemStock(itemName, 1);
            return;
        }


        String sql = "INSERT INTO shoppingsystem.cart (name, price, isonsale, discountpercentage, " +
                "haswarranty, category, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            DatabaseUtils.executeUpdate(sql, user, password, name, price, isOnSale, discountPercentage,
                    hasWarranty, category, quantity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        SixParamFunction<String, Double, String, Integer, String, Integer> action = MenuOption.getTypeOptionsMap()
                .get(product.getCategory().equals("groceries") ? 1 :
                        product.getCategory().equals("electronics") ? 2 : 3);

        Product addProduct = action.apply(product.getName(), product.getBasePrice(), product.isOnSale() ? "Y" : "N",
                product.getDiscountPercentage(), product.hasWarranty() ? "Y" : "N", 1);

        cart.add(addProduct);
        Stock.removeItemStock(itemName, 1);
    }

    //changed
    public static void removeFromCart(String itemName) {
        String sqlUpdate = "UPDATE shoppingsystem.cart SET quantity=? WHERE name=?";
        String sqlDelete = "DELETE FROM shoppingsystem.cart WHERE name=?";
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("products.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("url");
        String user = props.getProperty("username");
        String password = props.getProperty("password");

        var dataSource = new PGConnectionPoolDataSource();
        dataSource.setUrl(url);


        if(!hasItem(itemName)) {
            System.out.println("Item not found in your cart!");
            return;
        }
        // Updating cart
        Product product = getItem(itemName);
        product.setQuantity(product.getQuantity()-1);
        if(product.getQuantity() == 0) cart.remove(product);
        try {
            DatabaseUtils.executeUpdate(sqlUpdate, user, password, product.getQuantity(), product.getName());
            if(product.getQuantity() == 0) {
                DatabaseUtils.executeUpdate(sqlDelete, user, password, product.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Updating stock
        SixParamFunction<String, Double, String, Integer, String, Integer> action= MenuOption.getTypeOptionsMap().
                get(product.getCategory().equals("groceries") ? 1 : product.getCategory().equals("electronics") ? 2 : 3);

        Product stockProduct = action.apply(product.getName(), product.getBasePrice(), product.isOnSale() ? "Y" : "N",
                product.getDiscountPercentage(), product.hasWarranty() ? "Y" : "N", 1);

        Stock.addItemStock(stockProduct);


    }

    private static void calculateTotal() {
        Double[] prices = cart.stream()
                .map(o->o.getFinalPrice()*o.getQuantity())
                .toArray(Double[]::new);

        double total = Arrays.stream(prices)
                .reduce(0.0, Double::sum);

        System.out.printf("\n%-20sR$%.2f%n", "TOTAL", total);
    }

    public static void printCart() {
        System.out.println("\n=========== Your Cart =============");
        Comparator<Product> comparator = Comparator.comparing(Product::getFinalPrice).reversed();
        cart.sort(comparator);
        cart.forEach((product) -> {
                    for(int i=0; i<product.getQuantity(); i++) System.out.printf("%-20sR$%.2f%n", product.getName(),
                            product.getFinalPrice());
                });
        calculateTotal();
    }

    //new
    public static boolean hasItem(String itemName) {
        for(Product product : cart) {
            if(product.getName().equals(itemName)) {
                return true;
            }
        }
            return false;
    }

    //new
    public static Product getItem(String itemName) {
        return cart.stream()
                .filter(o -> o.getName().equals(itemName))
                .toList()
                .getFirst();
    }

    public static boolean isEmpty() {
        return cart.isEmpty();
    }

    public static void add(Product product) {
        cart.add(product);
    }
}
