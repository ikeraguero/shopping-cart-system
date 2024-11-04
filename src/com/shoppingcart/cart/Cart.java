package com.shoppingcart.cart;

import com.shoppingcart.MenuOption;
import com.shoppingcart.SixParamFunction;
import com.shoppingcart.product.Product;
import com.shoppingcart.stock.Stock;

import java.util.*;
import java.util.stream.Collectors;


public class Cart <T extends Product> {
    private static List<Product> cart = new LinkedList<>();

    //changed
    public static void addToCart(String itemName) {
        if(!Stock.hasItem(itemName)) {
            System.out.println("Item not available in stock!");
            return;
        }
        if(hasItem(itemName)) {
            // Updating cart
            Product product = cart.stream()
                            .filter(o -> o.getName().equals(itemName))
                            .toList()
                            .getFirst();
            product.setQuantity(product.getQuantity()+1);

            //Updating stock
            Stock.removeItemStock(itemName, 1);
            return;
        }
        Product product = Stock.getProduct(itemName);
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
        if(!hasItem(itemName)) {
            System.out.println("Item not found in your cart!");
            return;
        }
        // Updating cart
        Product product = getItem(itemName);
        product.setQuantity(product.getQuantity()-1);
        if(product.getQuantity() == 0) cart.remove(product);

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


    public static boolean isEmpty() {
        return cart.isEmpty();
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
}
