package com.shoppingcart.stock;

import com.shoppingcart.product.Product;

import java.util.*;
import java.util.stream.Collectors;

public class Stock {
    private static final HashMap<String, Integer> productQuantity = new HashMap<>();
    private static final List<Product> stock = new LinkedList<>();

    public Stock() {
    }

    //changed
    public static void addItemStock(Product item) {
        if(stock.stream()
                .anyMatch(o->o.getName().equals(item.getName())))
        {
            Product product = stock.stream()
                    .filter(o->o.getName().equals(item.getName()))
                    .toList()
                    .getFirst();

            product.setQuantity(product.getQuantity()+item.getQuantity());
            return;
        }
        stock.add(item);
    }


    //changed
    public static void removeItemStock(String name, int option) {
        Product product = getProduct(name);
        switch (option) {
            case 1:
                product.setQuantity(product.getQuantity()-1);
                if(product.getQuantity() == 0) stock.remove(product);
                break;
            case 2:
                stock.remove(product);
                break;
        }
    }

    public static boolean hasItem(String name) {
        for(Product item : stock) {
            if(item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static Product getProduct(String name) {
       return stock.stream()
               .filter(o->o.getName().equals(name))
               .toList()
               .getFirst();
    }

    public static Product getFirstItem(String name) {
        for(Product item : stock) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public static void searchItem(String itemName){
        for(Product item : stock) {
            if(item.getName().equals(itemName)) {
                System.out.println("\nItem found!");
                System.out.printf("%-20sR$%.2f (%d %s)\n", item.getName(),
                        item.getFinalPrice(), item.getQuantity(),
                        item.getQuantity() > 1 ? "units" : "unit");
                return;
            }
        }
        System.out.println("Item not found!");
    }

    // changed
    public static void printStock(int sortType) {
        sortType = (sortType == 0 ? 1 : sortType);
        String previousCategory = null;
        Comparator<Product> comparator = (sortType == 1) ? Comparator.comparing(Product::getCategory)
                .thenComparing(Product::getName)
                : Comparator.comparing(Product::getCategory)
                .thenComparing(Product::getFinalPrice).reversed();

        List<Product> sortedProducts = stock.stream()
                .sorted(comparator)
                .toList();

        printProducts(sortedProducts, previousCategory);
    }

    private static void printProducts(List<Product> products, String previousCategory) {
        for (Product product : products) {
            if(!product.getCategory().equals(previousCategory)) {
                previousCategory = product.getCategory();
                System.out.println();
                System.out.println(previousCategory.toUpperCase());;
            }
            System.out.print(product);
        }
    }

    public static boolean isEmpty() {
        return stock.isEmpty();
    }
}
