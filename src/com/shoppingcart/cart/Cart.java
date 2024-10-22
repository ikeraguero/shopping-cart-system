package com.shoppingcart.cart;

import com.shoppingcart.product.Product;
import com.shoppingcart.stock.Stock;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Cart <T extends Product> {
    private static List<Product> cart = new LinkedList<>();

    public static void addToCart(String itemName) {
        if(!Stock.hasItem(itemName)) {
            System.out.println("Item not available!");
            return;
        }
        var item = Stock.getFirstItem(itemName);
        assert item != null;
        cart.add(item);
        Stock.removeItemStock(itemName, 1);
        System.out.println("Item successfully added to your cart!");
    }

    public static void removeFromCart(String name) {
        for(Product item : cart) {
            if(item.getName().equals(name)) {
                Product addItem = Cart.getProduct(name);
                Stock.addItemStock(addItem);
                cart.remove(item);
                System.out.println("Item successfully removed!");
                return;
            }
        }
        System.out.println("Item not found!");

    }

    private static void calculateTotal() {
        double total = 0;
        for(Product item : cart) {
            total += item.getFinalPrice();
            System.out.printf("""
                    %-20sR$%.2f
                    """, item.getName(), item.getFinalPrice());
        }
        System.out.println();
        System.out.printf("""
                %-20sR$%.2f
                %n""","TOTAL",  total);
    }

    private static Product getProduct(String itemName) {
        for(Product item : cart) {
            if(item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public static void sortCart(int sortOption) {
        switch (sortOption) {
            case 1:
                cart.sort((o1, o2)->o1.getName().compareTo(o2.getName()));
                break;
            case 2:
                cart.sort(Comparator.comparing(Product::getFinalPrice).reversed());
                break;
        }
    }

    public static void printCart(int sortOption) {
        sortCart(sortOption);
        System.out.println("\n=========== Your Cart =============");
        calculateTotal();
        System.out.println("====================================");
    }


    public static boolean isEmpty() {
        return cart.isEmpty();
    }
}
