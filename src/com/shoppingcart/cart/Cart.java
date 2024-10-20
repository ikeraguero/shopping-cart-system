package com.shoppingcart.cart;

import com.shoppingcart.product.Product;

import java.util.LinkedList;
import java.util.List;

public class Cart <T extends Product> {
    List<T> cart = new LinkedList<>();

    public void addToCart(T item) {

    }

    public void removeFromCart(String name) {

    }

    public void calculateTotal() {
        double total = 0;
        for(T item : cart) {
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

}
