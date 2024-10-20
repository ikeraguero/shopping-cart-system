package com.shoppingcart;

import com.shoppingcart.product.Clothing;
import com.shoppingcart.product.Electronics;
import com.shoppingcart.product.Grocery;
import com.shoppingcart.product.Product;
import com.shoppingcart.stock.Stock;

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

            switch (choice) {
                case 1:
                    int category = 0;
                    String productName = "";
                    double basePrice = 0.0;
                    String hasWarranty = "";
                    String isOnSale = "";
                    int discountPercentage = 0;
                    int inventory = 0;
                    while(category==0 || category > 3) {
                        System.out.print("\n[1] - Groceries\n");
                        System.out.print("[2] - Electronics\n");
                        System.out.print("[3] - Clothing\n");
                        System.out.print("Enter your choice: ");
                        category = Integer.parseInt(scanner.next());
                    }
                    while(productName.isEmpty()) {
                        System.out.print("\nProduct name: ");
                        productName = scanner.next().toUpperCase();
                    }
                   if(!Stock.hasItem(productName)) {
                       while (basePrice == 0.0 || basePrice < 0.0 ) {
                           System.out.print("Base price: ");
                           basePrice = Double.parseDouble(scanner.next());
                       }
                       while(!isOnSale.equals("Y") && !isOnSale.equals("N")) {
                           System.out.print("Is on sale? [Y/N]: ");
                           isOnSale = scanner.next().toUpperCase();
                       }
                       if(isOnSale.equals("Y")) {
                           System.out.print("Discount percentage (%): ");
                           discountPercentage = Integer.parseInt(scanner.next());
                       }
                       while(!hasWarranty.equals("Y") && !hasWarranty.equals("N") && category==2) {
                           if(!Stock.hasItem(productName)) {
                               System.out.print("Has Warranty? [Y/N]: ");
                               hasWarranty = scanner.next().toUpperCase();
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
                    System.out.print("Amount to add: ");
                    inventory = Integer.parseInt(scanner.next());
                    switch (category) {
                        case 1:
                            int count = 0;
                            do {
                                Product grocery = new Grocery(productName, basePrice, (isOnSale.equals("Y")), discountPercentage, inventory);
                                Stock.addItemStock(grocery);
                                count++;
                            } while (count < inventory);
                            Stock.printStock();
                            break;
                        case 2:
                            count = 0;
                            do {
                                Product electronics = new Electronics(productName, basePrice, (isOnSale.equals("Y")), discountPercentage,(hasWarranty.equals("Y")), inventory);
                                Stock.addItemStock(electronics);
                                count++;
                            } while (count < inventory);
                            Stock.printStock();
                            break;
                        case 3:
                            count = 0;
                            do {
                                Product clothing = new Clothing(productName, basePrice, (isOnSale.equals("Y")), discountPercentage, inventory);
                                Stock.addItemStock(clothing);
                                count++;
                            } while (count < inventory);
                            Stock.printStock();
                            break;
                    }
                    System.out.print("\n: ");
                    break;
                case 2:
                    int deletionOption = 0;
                    System.out.print("Enter the product name: ");
                    productName = scanner.next().toUpperCase();
                    if(Stock.hasItem(productName)) {
                        while(deletionOption == 0 || deletionOption > 2 || deletionOption < 0) {
                            System.out.print("\n[1] - Delete one product\n");
                            System.out.print("[2] - Delete all products\n");
                            System.out.print("Enter your choice: ");
                            deletionOption = Integer.parseInt(scanner.next());
                    }
                        Stock.removeItemStock(productName, deletionOption);
                        System.out.println("Item successfully removed!");
                        Stock.printStock();
                        break;
                    }
                    System.out.println("Item not found in stock!");// TO-DO : implements deletion
                    break;
                case 3:
                    Stock.printStock();
                    break;
                case 4:
                    System.out.println("Item name: ");

                    break;
                case 5:

                case 6:

                case 7:
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