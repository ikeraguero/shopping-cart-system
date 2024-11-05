package com.shoppingcart.stock;

import com.shoppingcart.MenuOption;
import com.shoppingcart.SixParamFunction;
import com.shoppingcart.product.Product;
import org.postgresql.ds.PGConnectionPoolDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Stock {
    private static final HashMap<String, Integer> productQuantity = new HashMap<>();
    private static final List<Product> stock = new LinkedList<>();

    public Stock() {
    }

    //changed
    public static void addItemStock(Product item) {
        if(stock.stream()
                .anyMatch(o->o.getName().equals(item.getName())))
        {
            Product product = stock.stream()
                    .filter(o->o.getName().equals(item.getName()))
                    .toList()
                    .getFirst();

            product.setQuantity(product.getQuantity()+item.getQuantity());
            return;
        }
        stock.add(item);
    }


    //changed
    public static void removeItemStock(String name, int option) {
        Product product = getProduct(name);
        switch (option) {
            case 1:
                product.setQuantity(product.getQuantity()-1);
                if(product.getQuantity() == 0) stock.remove(product);
                break;
            case 2:
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
    public static void printStock(int sortType) {
        sortType = (sortType == 0 ? 1 : sortType);
        String previousCategory = null;
        Comparator<Product> comparator = (sortType == 1) ? Comparator.comparing(Product::getCategory)
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

    public static void loadDatabaseStock() {
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

        var dataSource = new PGConnectionPoolDataSource();
        dataSource.setUrl(url);


        try(Connection connection = dataSource.getConnection(
                user, password
        ); Statement statement = connection.createStatement()) {

            String sql = "SELECT * FROM shoppingsystem.products";
            ResultSet rs = statement.executeQuery(sql);

            ResultSetMetaData meta = rs.getMetaData();

            for(int i=1; i<=meta.getColumnCount(); i++) {
                System.out.printf("%-20s ", meta.getColumnName(i).toUpperCase());
            }
            while(rs.next()) {
                String name = rs.getString(1).toUpperCase();
                double price = rs.getDouble(2);
                String isOnSale = rs.getString(3);
                int discountPercentage = rs.getInt(4);
                String hasWarranty = rs.getString(5);
                String category = rs.getString(6);
                int quantity = rs.getInt(7);

                SixParamFunction<String, Double, String, Integer, String, Integer> action= MenuOption.getTypeOptionsMap().
                        get(category.equals("groceries") ? 1 : category.equals("electronics") ? 2 : 3);

                Product product = action.apply(name, price, isOnSale, discountPercentage, hasWarranty, quantity);
                stock.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
