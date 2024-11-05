package com.shoppingcart;

import com.shoppingcart.stock.Stock;
import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.swing.plaf.nimbus.State;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {;
        HashMap<Integer, Consumer<Scanner>> menuMap = new HashMap<>();
        new MenuOption();
        Stock.loadDatabaseStock();
        menuMap.put(1, MenuOption::printAddItem);
        menuMap.put(2, MenuOption::printRemoveItem);
        menuMap.put(3, MenuOption::printShowStock);
        menuMap.put(4, MenuOption::printSearchItem);
        menuMap.put(5, MenuOption::printAddToCart);
        menuMap.put(6, MenuOption::printRemoveFromCart);
        menuMap.put(7, MenuOption::printShowCart);



        //

        final Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            printMenu();
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (NumberFormatException _) {
            }
            scanner.nextLine();
            if(choice==8) {
                quit = true;
                System.out.println("Exiting the application...");
                return;
            }
                Consumer<Scanner> action = menuMap.get(choice);
                if (action != null) {
                    action.accept(scanner);
                } else {
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