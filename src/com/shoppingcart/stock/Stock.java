package com.shoppingcart.stock;

import com.shoppingcart.Loadable;
import com.shoppingcart.MenuOption;
import com.shoppingcart.SixParamFunction;
import com.shoppingcart.db.DatabaseUtils;
import com.shoppingcart.product.Product;
import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Stock implements Loadable {
    private static final List<Product> stock = new LinkedList<>();

    public Stock() {
    }

    //changed
    public static void addItemStock(Product item) {

        String name = item.getName();
        double price = item.getBasePrice();
        String isOnSale = item.isOnSale() ? "Y" : "N";
        int discountPercentage = item.getDiscountPercentage();
        String hasWarranty = item.hasWarranty() ? "Y" : "N";
        String category = item.getCategory();
        int quantity = item.getQuantity();

        Properties props = new Properties();
        try {
            props.load(new FileInputStream("products.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String user = props.getProperty("username");
        String password = props.getProperty("password");


        if(stock.stream()
                .anyMatch(o->o.getName().equals(item.getName())))
        {
            Product product = stock.stream()
                    .filter(o->o.getName().equals(item.getName()))
                    .toList()
                    .getFirst();

            product.setQuantity(product.getQuantity()+item.getQuantity());
            try {
                String sql = "UPDATE shoppingsystem.products SET quantity=? WHERE name=?";
                DatabaseUtils.executeUpdate(sql, user, password, product.getQuantity(), product.getName());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        try {
            String sql = "INSERT INTO shoppingsystem.products (name, price, isonsale, discountpercentage, " +
                    "haswarranty, category, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
            DatabaseUtils.executeUpdate(sql, user, password, name, price, isOnSale, discountPercentage, hasWarranty,
                    category, quantity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        stock.add(item);
    }


    //changed
    public static void removeItemStock(String name, String option) {
        Product product = getProduct(name);
        String sqlDelete = "DELETE FROM shoppingsystem.products WHERE name=?";
        String sqlUpdate = "UPDATE shoppingsystem.products SET quantity=? WHERE name=?";

        Properties props = new Properties();
        try {
            props.load(new FileInputStream("products.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String user = props.getProperty("username");
        String password = props.getProperty("password");


        switch (option) {
            case "singleDeletion":
                product.setQuantity(product.getQuantity()-1);
                if(product.getQuantity() == 0) stock.remove(product);
                try {
                    DatabaseUtils.executeUpdate(sqlUpdate, user, password, product.getQuantity(), product.getName());
                    if(product.getQuantity() == 0) {
                        DatabaseUtils.executeUpdate(sqlDelete, user, password, product.getName());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "fullDeletion":
                try {
                    DatabaseUtils.executeUpdate(sqlDelete, user, password, product.getName());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                stock.remove(product);
                break;
        }
    }

    public static boolean hasItem(String name) {
        for(Product item : stock) {
            if(item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static Product getProduct(String name) {
       return stock.stream()
               .filter(o->o.getName().equals(name))
               .toList()
               .getFirst();
    }

    public static Product getFirstItem(String name) {
        for(Product item : stock) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public static void searchItem(String itemName){
        for(Product item : stock) {
            if(item.getName().equals(itemName)) {
                System.out.println("\nItem found!");
                System.out.printf("%-20sR$%.2f (%d %s)\n", item.getName(),
                        item.getFinalPrice(), item.getQuantity(),
                        item.getQuantity() > 1 ? "units" : "unit");
                return;
            }
        }
        System.out.println("Item not found!");
    }

    // changed
    public static void printStock(String sortType) {
        sortType = (sortType.isEmpty() ? "byName" : "byPrice");
        String previousCategory = null;
        Comparator<Product> comparator = (sortType.equals("byName")) ? Comparator.comparing(Product::getCategory)
                .thenComparing(Product::getName)
                : Comparator.comparing(Product::getCategory)
                .thenComparing(Product::getFinalPrice).reversed();

        List<Product> sortedProducts = stock.stream()
                .sorted(comparator)
                .toList();

        printProducts(sortedProducts, previousCategory);
    }

    private static void printProducts(List<Product> products, String previousCategory) {
        for (Product product : products) {
            if(!product.getCategory().equals(previousCategory)) {
                previousCategory = product.getCategory();
                System.out.println();
                System.out.println(previousCategory.toUpperCase());;
            }
            System.out.print(product);
        }
    }

    public static boolean isEmpty() {
        return stock.isEmpty();
    }

    public static void add(Product product) {
        stock.add(product);
    }

}
