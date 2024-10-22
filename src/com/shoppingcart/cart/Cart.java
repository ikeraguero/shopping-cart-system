package com.shoppingcart.cart;

import com.shoppingcart.product.Product;
import com.shoppingcart.stock.Stock;

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
    }

    public static void removeFromCart(String name) {
        for(Product item : cart) {
            if(item.getName().equals(name)) {
                Product addItem = Cart.getProduct(name);
                Stock.addItemStock(addItem);
                cart.remove(item);
            }
        }

    }

    public void calculateTotal() {
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

    public void sortCartByName() {
        cart.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        cart.forEach(System.out::println);
    }

    private static Product getProduct(String itemName) {
        for(Product item : cart) {
            if(item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void sortCartByPrice() {
        cart.sort((o1, o2) -> {
            int result =0;
            if(o1.getBasePrice() < o2.getBasePrice()) {
                result = -1;
            }
            if(o1.getBasePrice() > o2.getBasePrice()) {
                result = 1;
            }
            if(o1.getBasePrice() == o2.getBasePrice()) {
                result = 0;
            }
            return result;
        });
        cart.forEach(System.out::println);
    }

    public static List<Product> getCart() {
        return cart;
    }

    public static boolean isEmpty() {
        return cart.isEmpty();
    }
}
