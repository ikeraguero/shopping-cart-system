package com.shoppingcart.cart;

import com.shoppingcart.MenuOption;
import com.shoppingcart.SixParamFunction;
import com.shoppingcart.product.Product;
import com.shoppingcart.stock.Stock;

import java.util.*;
import java.util.stream.Collectors;


public class Cart <T extends Product> {
    private static TreeMap<String, Product> cart = new TreeMap<>();

    public static void addToCart(String itemName) {
        if(!Stock.hasItem(itemName)) {
            System.out.println("Item not available in stock!");
            return;
        }
        if(cart.containsKey(itemName)) {
            // Updating cart
            Product product = cart.get(itemName);
            product.setInventory(product.getQuantity()+1);

            //Updating stock
            Stock.removeItemStock(itemName, 1);
            return;
        }
        Product product = Stock.getProduct(itemName);
        SixParamFunction<String, Double, String, Integer, String, Integer> action= MenuOption.getTypeOptionsMap().get(product.getCategory().equals("groceries") ? 1 : product.getCategory().equals("electronics") ? 2 : 3);
        Product addProduct = action.apply(product.getName(), product.getBasePrice(), product.isOnSale() ? "Y" : "N", product.getDiscountPercentage(), product.hasWarranty() ? "Y" : "N", 1);
        cart.put(product.getName(), addProduct);
        Stock.removeItemStock(itemName, 1);
    }

    public static void removeFromCart(String itemName) {
        if(!cart.containsKey(itemName)) {
            System.out.println("Item not found in your cart!");
            return;
        }
        // Updating cart
        Product product = cart.get(itemName);
        product.setInventory(product.getQuantity()-1);
        if(product.getQuantity() == 0) cart.remove(product.getName());

        //Updating stock
        SixParamFunction<String, Double, String, Integer, String, Integer> action= MenuOption.getTypeOptionsMap().get(product.getCategory().equals("groceries") ? 1 : product.getCategory().equals("electronics") ? 2 : 3);
        Product stockProduct = action.apply(product.getName(), product.getBasePrice(), product.isOnSale() ? "Y" : "N", product.getDiscountPercentage(), product.hasWarranty() ? "Y" : "N", 1);
        Stock.addItemStock(stockProduct);


    }

    private static void calculateTotal() {
        Double[] prices = cart.values().stream()
                .map(o->o.getFinalPrice()*o.getQuantity())
                .toArray(Double[]::new);

        double total = Arrays.stream(prices)
                .reduce(0.0, Double::sum);

        System.out.printf("\n%-20sR$%.2f%n", "TOTAL", total);
    }
//
//    private static CartItem getProduct(String itemName) {
//        for(CartItem item : cart) {
//            if(item.getName().equals(itemName)) {
//                return item;
//            }
//        }
//        return null;
//    }
//
//    public static void sortCart(int sortOption) {
//        switch (sortOption) {
//            case 1:
//                cart.sort((o1, o2)->o1.getName().compareTo(o2.getName()));
//                break;
//            case 2:
//                cart.sort(Comparator.comparing(CartItem::getFinalPrice).reversed());
//                break;
//        }
//    }
//
    public static void printCart(int sortOption) {
        System.out.println("\n=========== Your Cart =============");
        cart.forEach((key, value) -> {
                    for(int i=0; i<value.getQuantity(); i++) System.out.printf("%-20sR$%.2f%n", key, value.getFinalPrice());
                });
        calculateTotal();
//        sortCart(sortOption);
//        System.out.println("\n=========== Your Cart =============");
//        calculateTotal();
//        System.out.println("====================================");
    }


    public static boolean isEmpty() {
        return cart.isEmpty();
    }
}
