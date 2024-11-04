package com.shoppingcart;

import com.shoppingcart.cart.Cart;
import com.shoppingcart.product.Clothing;
import com.shoppingcart.product.Electronics;
import com.shoppingcart.product.Grocery;
import com.shoppingcart.product.Product;
import com.shoppingcart.stock.Stock;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Consumer;


public class MenuOption {
    private static int category = 0;
    private static String productName = "";
    private static double basePrice = 0.0;
    private static String hasWarranty = "";
    private static String isOnSale = "";
    private static int discountPercentage = 0;
    private static int quantity = 0;
    private static HashMap<Integer, SixParamFunction<String, Double, String, Integer, String, Integer>> typeOptionsMap;

    public MenuOption() {
        /// Type Map
        typeOptionsMap = new HashMap<>();
        typeOptionsMap.put(1, Grocery::createGrocery);
        typeOptionsMap.put(2, Electronics::createElectronic);
        typeOptionsMap.put(3, Clothing::createClothing);
    }

    private static void resetAllFields() {
        category = 0;
        productName = "";
        basePrice = 0.0;
        hasWarranty = "";
        isOnSale = "";
        discountPercentage = 0;
        quantity = 0;
    }

    public static HashMap<Integer, SixParamFunction<String, Double, String, Integer, String, Integer>>
    getTypeOptionsMap() {
        return typeOptionsMap;
    }

    public static void printAddItem(Scanner scanner){

        while(category==0 || category > 3) {
            System.out.print("\n[1] - Groceries\n");
            System.out.print("[2] - Electronics\n");
            System.out.print("[3] - Clothing\n");
            System.out.print("Enter your choice: ");
            try {
                category = Integer.parseInt(scanner.next());
                scanner.nextLine();
            } catch (NumberFormatException _) {}
        }
        while(productName.isEmpty()) {
            System.out.print("Product name: ");
            try {
                productName = scanner.nextLine().toUpperCase();
            } catch (NumberFormatException _) {}
        }
        if(!Stock.hasItem(productName)) {
            while (basePrice == 0.0 || basePrice < 0.0 ) {
                System.out.print("Base price: ");
                try {
                    basePrice = Double.parseDouble(scanner.next());
                } catch (NumberFormatException _) {}
                scanner.nextLine();
            }
            while(!isOnSale.equals("Y") && !isOnSale.equals("N")) {
                System.out.print("Is on sale? [Y/N]: ");
                try {
                    isOnSale = scanner.next().toUpperCase();
                } catch (NumberFormatException _) {}
            }
            if(isOnSale.equals("Y")) {
                System.out.print("Discount percentage (%): ");
                try {
                    discountPercentage = Integer.parseInt(scanner.next());
                } catch (NumberFormatException _) {}
                scanner.nextLine();
            }
            while(!hasWarranty.equals("Y") && !hasWarranty.equals("N") && category==2) {
                if(!Stock.hasItem(productName)) {
                    System.out.print("Has Warranty? [Y/N]: ");
                    try {
                        hasWarranty = scanner.next().toUpperCase();
                    } catch (NumberFormatException _) {}
                }
            }
        } else {
            Product product = Stock.getFirstItem(productName);
            basePrice = product.getBasePrice();
            hasWarranty = (product instanceof Electronics && product.hasWarranty()) ? "Y" : "N";
            isOnSale = product.isOnSale() ? "Y" : "N";
            if (isOnSale.equals("Y")) {
                discountPercentage = product.getDiscountPercentage();
            }
        }
            while(quantity == 0 || quantity < 0) {
                System.out.print("Amount to add: ");
                quantity = Integer.parseInt(scanner.next());
                scanner.nextLine();
            }
        if(category < 1 || category > 3) {
            System.out.println("Invalid option");
            return;
        }
        // Remove while loop that creates n amount of objects in memory, now it creates only one object and will use
        // the quantity field to determine the amount

        // varible that implments SixParamFunction
        SixParamFunction<String, Double, String, Integer, String, Integer> action = typeOptionsMap.get(category);
        Product product = action.apply(productName, basePrice, isOnSale, discountPercentage, hasWarranty, quantity);
        Stock.addItemStock(product);
        Stock.printStock(0);
        resetAllFields();
        System.out.print("\n ");
    }

    public static void printRemoveItem(Scanner scanner) {
        if(Stock.isEmpty()) {
            System.out.println("Stock is empty!");
            return;
        };
        int deletionOption = -1;
        System.out.print("Enter the product name: ");
        String newProductName = scanner.next().toUpperCase();

        if(Stock.hasItem(newProductName)) {
            while(deletionOption == 0 || deletionOption > 2 || deletionOption < 0) {
                System.out.print("\n[1] - Delete one product\n");
                System.out.print("[2] - Delete all products\n");
                System.out.print("Enter your choice: ");
                deletionOption = Integer.parseInt(scanner.next());
                scanner.nextLine();
            }
            Stock.removeItemStock(newProductName, deletionOption);
            System.out.println("Item successfully removed!");
            Stock.printStock(0);
            resetAllFields();
            return;
        }
        resetAllFields();
        System.out.println("Item not found in stock!");
    }

    public static void printShowStock(Scanner scanner) {
        if(Stock.isEmpty()) {
            System.out.println("Stock is empty!");
            return;
        };
        int sortOption = 0;
        while(sortOption==0 || sortOption > 2) {
            System.out.print("\n[1] - Sort by Name\n");
            System.out.print("[2] - Sort by Price\n");
            System.out.print("Enter your choice: ");
            sortOption = Integer.parseInt(scanner.next());
        }
        Stock.printStock(sortOption);
    }

    public static void printSearchItem(Scanner scanner) {
        if(Stock.isEmpty()) {
            System.out.println("Stock is empty!");
            return;
        };
        String itemName;
        System.out.print("Item name: ");
        itemName = scanner.nextLine().toUpperCase();
        Stock.searchItem(itemName);
    }

    public static void printAddToCart(Scanner scanner) {
        if(Stock.isEmpty()) {
            System.out.println("Stock is empty!");
            return;
        };
        String productToAdd = "";
        Stock.printStock(0);
        while(productToAdd.isEmpty()) {
            System.out.print("Enter the name of the desired product: ");
            productToAdd = scanner.nextLine().toUpperCase();
        }
        Cart.addToCart(productToAdd);
    }

    public static void printRemoveFromCart(Scanner scanner) {
        if(Cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }
        String productToRemove = "";
        Cart.printCart();
        while(productToRemove.isEmpty()) {
            System.out.print("Enter the name of the product to be removed: ");
            productToRemove = scanner.nextLine().toUpperCase().toUpperCase();
        }
        Cart.removeFromCart(productToRemove);
    }

    public static void printShowCart(Scanner scanner) {
        if(Cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }
        Cart.printCart();

    }

    };
