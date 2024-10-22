package com.shoppingcart;

import com.shoppingcart.cart.Cart;
import com.shoppingcart.product.Clothing;
import com.shoppingcart.product.Electronics;
import com.shoppingcart.product.Grocery;
import com.shoppingcart.product.Product;
import com.shoppingcart.stock.Stock;

import java.awt.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Product clothing =  new Clothing("Cargo Pants", 50.00,  false, 5, 5);
//        Product clothing1 =  new Clothing("Cargo Pants", 50.00,  false, 5, 5);
//        Product grocery = new Grocery("Candy", 1000.00, true, 5, 5);
//        Product electronics = new Electronics("iPhone", 500.00, false, 0, false,5 );
//
//        Cart<Product> cart = new Cart<>();
//        cart.addToCart(electronics);
//        cart.addToCart(electronics);
//        cart.addToCart(electronics);
//        cart.addToCart(electronics);
//        cart.addToCart(electronics);
//        cart.addToCart(electronics);
//        cart.calculateTotal();

        final Scanner scanner = new Scanner(System.in);

        boolean quit = false;

        while (!quit) {
            printMenu();
            int choice = Integer.parseInt(scanner.next());
            scanner.nextLine();

            switch (choice) {
                case 1:
                    MenuOption.printAddItem(scanner);
                    break;
                case 2:
                    MenuOption.printRemoveItem(scanner);
                    break;
                case 3:
                    MenuOption.printShowStock(scanner);
                    break;
                case 4:
                    MenuOption.printSearchItem(scanner);
                    break;
                case 5:
                    MenuOption.printAddToCart(scanner);
                    break;
                case 6:
                    MenuOption.printRemoveFromCart(scanner);
                    break;
                case 7:
                    // SHOW CART
                case 8:
                    quit = true;
                    System.out.println("Exiting the application...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n======== Shopping Cart System ========");
        System.out.println("1. Add new item to stock");
        System.out.println("2. Remove item from stock");
        System.out.println("3. Show stock");
        System.out.println("4. Search Item");
        System.out.println("5. Add Item to Cart");
        System.out.println("6. Remove item from Cart");
        System.out.println("7. Show Cart");
        System.out.println("8. Exit");
        System.out.println("====================================");
        System.out.print("Enter your choice: ");
    }

}