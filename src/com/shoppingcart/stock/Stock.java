package com.shoppingcart.stock;

import com.shoppingcart.product.Product;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Stock {
    private static final List<Product> stock = new LinkedList<>();

    public Stock() {
    }

    public static void addItemStock(Product item) {
        stock.add(item);
    }

    public static void removeItemStock(String name, int option) {
        switch (option) {
            case 1:
                stock.remove(getFirstItem(name));
                break;
            case 2:
                Iterator<Product> iterator = stock.iterator();
                while(iterator.hasNext()) {
                    Product product = iterator.next();
                    if(product.getName().equals(name)) {
                        iterator.remove();
                    }
                }
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

    public static Product getFirstItem(String name) {
        for(Product item : stock) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public static void printStock() {
        for(Product item : stock) {
            System.out.println(item);
        }
    }
}
