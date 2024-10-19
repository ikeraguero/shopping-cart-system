package com.shoppingcart.stock;

import java.util.HashMap;

public class Stock {
    private static final HashMap<String, Integer> stock = new HashMap<>();

    public Stock() {
    }

    public static void addNewItemToStock(String key, int inventory) {
        if(stock.containsKey(key)) {
            stock.put(key, stock.get(key) + inventory);
            System.out.println(stock);
            return;
        }
        stock.put(key, inventory);
        System.out.println(stock);
    }

    public static void updateStockQuantity(String operation, String key) {
        if(!stock.containsKey(key)) {
            stock.put(key, 1);
            System.out.println(stock);
            return;
        }
         stock.put(key, stock.get(key) + (operation.equals("increase") ? + 1 : -1));
         if(stock.get(key) == 0) {
             stock.remove(key);
         }
             System.out.println(stock);
    }

    public static boolean accessKey(String key) {
        return stock.containsKey(key);
    }
}
