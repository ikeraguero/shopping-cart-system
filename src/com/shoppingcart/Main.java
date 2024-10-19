package com.shoppingcart;

import com.shoppingcart.cart.Cart;
import com.shoppingcart.product.Clothing;
import com.shoppingcart.product.Electronics;
import com.shoppingcart.product.Grocery;
import com.shoppingcart.product.Product;

public class Main {
    public static void main(String[] args) {
        Product clothing =  new Clothing("Cargo Pants", 50.00,  false, 5, 5);
        Product clothing1 =  new Clothing("Cargo Pants", 50.00,  false, 5, 5);
        Product grocery = new Grocery("Candy", 1000.00, true, 5, 5);
        Product electronics = new Electronics("iPhone", 500.00, false, 0, false,5 );

        Cart<Product> cart = new Cart<>();
        cart.addToCart(electronics);
        cart.addToCart(electronics);
        cart.addToCart(electronics);
        cart.addToCart(electronics);
        cart.addToCart(electronics);
        cart.addToCart(electronics);
        cart.calculateTotal();
    }

}