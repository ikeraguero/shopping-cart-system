package com.shoppingcart.stock;

import com.shoppingcart.product.Product;

import java.util.*;

public class Stock {
    private static final List<Product> stock = new LinkedList<>();
    public static final List<Product> groceriesList = new LinkedList<>();
    private static final List<Product> electronicsList = new LinkedList<>();
    private static final List<Product> clothingList = new LinkedList<>();
    private static final List<List<Product>> listLists = new LinkedList<>(Arrays.asList(groceriesList, electronicsList, clothingList));

    public Stock() {
    }

    public static void addItemStock(Product item) {
        stock.add(item);
        switch (item.getCategory()) {
            case "groceries":
                groceriesList.add(item);
                break;
            case "electronics":
                electronicsList.add(item);
                break;
            case "clothing":
                clothingList.add(item);
                break;
        }
    }

    private static void removeSingleItem(List<Product> list, String name) {
        Iterator<Product> iterator = list.iterator();
        while(iterator.hasNext()) {
            Product product = iterator.next();
            if(product.getName().equals(name)) {
                iterator.remove();
            }
        }
    }

    public static void removeItemStock(String name, int option) {
        String category = "";
        for(Product item : stock) {
            if(item.getName().equals(name)) {
                category = item.getCategory();
                break;
            }
        }
        switch (option) {
            case 1:
                switch (category) {
                   case "groceries":
                        groceriesList.remove(getFirstItem(name));
                        break;
                    case "electronics":
                        electronicsList.remove(getFirstItem(name));
                        break;
                    case "clothing":
                        clothingList.remove(getFirstItem(name));
                        break;
                }
                stock.remove(getFirstItem(name));
                break;
            case 2:
                Iterator<Product> iterator = stock.iterator();
                while(iterator.hasNext()) {
                    Product product = iterator.next();
                    if(product.getName().equals(name)) {
                        iterator.remove();
                    }
                }
                switch (category) {
                    case "groceries":
                        removeSingleItem(groceriesList, name);
                        break;
                    case "electronics":
                        removeSingleItem(electronicsList, name);
                        break;
                    case "clothing":
                        removeSingleItem(clothingList, name);
                        break;
                }
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

    public static Product getFirstItem(String name) {
        for(Product item : stock) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    private static void printSortedStock() {
        for (List<Product> list: Stock.listLists) {
        int productCount = 0;
            if(list.isEmpty()) continue;
            System.out.println(list.getFirst().getCategory().toUpperCase());
            HashMap<String, Product> productMap = new HashMap<>();
            for(Product item : list) {
                productMap.putIfAbsent(item.getName(), item);
                productCount++;
            }
            for(Product item : productMap.values()) {
                System.out.printf("%-20sR$%.2f (%d %s)\n", item.getName(), item.getFinalPrice(), productCount, productCount > 1 ? "units" : "unit");
            }
        }
    }

    public static void printStock(int sortType) {
        sortType = (sortType == 0 ? 1 : sortType);
        switch (sortType) {
            case 1:
                electronicsList.sort(Comparator.comparing(Product::getName));
                groceriesList.sort(Comparator.comparing(Product::getName));
                clothingList.sort(Comparator.comparing(Product::getName));
                printSortedStock();
                break;
            case 2:
                electronicsList.sort(Comparator.comparing(Product::getBasePrice).reversed());
                groceriesList.sort(Comparator.comparing(Product::getBasePrice).reversed());
                clothingList.sort(Comparator.comparing(Product::getBasePrice).reversed());
                printSortedStock();
                break;

        }
    }
}
