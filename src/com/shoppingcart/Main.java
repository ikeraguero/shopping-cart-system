package com.shoppingcart;

import com.shoppingcart.product.Eletronics;
import com.shoppingcart.product.Grocery;
import com.shoppingcart.product.Product;

public class Main {
    public static void main(String[] args) {
        Product grocery = new Grocery("Candy", 10.00, "sweats", true, 5);
        Product clothing =  new Grocery("Cargo Pants", 50.00, "clothing", false, 5);
        Product electronics = new Eletronics("iPhone", 500.00, "electronics", false, 0, false);
        System.out.println(grocery);
        System.out.println(clothing);
        System.out.println(electronics);
    }

}