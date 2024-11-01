package com.shoppingcart.stock;

import com.shoppingcart.product.Product;

import java.util.*;
import java.util.stream.Collectors;

public class Stock {
    private static final HashMap<String, Integer> productQuantity = new HashMap<>();
    private static final List<Product> stock = new LinkedList<>();
//    private static final List<Product> groceriesList = new LinkedList<>();
//    private static final List<Product> electronicsList = new LinkedList<>();
//    private static final List<Product> clothingList = new LinkedList<>();
//    private static final List<List<Product>> listLists = new LinkedList<>(Arrays.asList(groceriesList, electronicsList, clothingList));

    public Stock() {
    }

    //changed
    public static void addItemStock(Product item) {
        if(stock.stream()
                .anyMatch(o->o.getName().equals(item.getName()))) // streams and lambda to check if there's already an object with the same name value
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

//    private static void removeSingleItem(List<Product> list, String name) {
//        Iterator<Product> iterator = list.iterator();
//        while(iterator.hasNext()) {
//            Product product = iterator.next();
//            if(product.getName().equals(name)) {
//                iterator.remove();
//            }
//        }
//    }

    //changed
    public static void removeItemStock(String name, int option) {
//        String category = "";
//        for(Product item : stock) {
//            if(item.getName().equals(name)) {
//                category = item.getCategory();
//                break;
//            }
//        }
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

    private static void calculateQuantity() {
//        for (List<Product> list : Stock.listLists) {
//            if (list.isEmpty()) continue;
//            HashMap<String, Product> productMap = new HashMap<>();
//            for (Product item : list) {
//                if (!productMap.containsKey(item.getName())) {
//                    int productCount = 0;
//                    productMap.put(item.getName(), item);
//                    for (Product itemList : list) {
//                        if (itemList.getName().equals(item.getName())) {
//                            productCount++;
//                        }
//                    }
//                    productQuantity.put(item.getName(), productCount);
//                }
//            }
//        }
    }

    private static void printSortedStock() {
        System.out.println("\n============== Stock =================");
//        for (List<Product> list: Stock.listLists) {
//            if(list.isEmpty()) continue;
//            System.out.println();
//            System.out.println(list.getFirst().getCategory().toUpperCase());
//            HashMap<String, Product> productMap = new HashMap<>();
//            for(Product item : list) {
//                    if(!productMap.containsKey(item.getName())) {
//                        int productCount = 0;
//                        productMap.put(item.getName(), item);
//                        for(Product itemList : list) {
//                            if(itemList.getName().equals(item.getName())) {
//                                productCount++;
//                            }
//                        }
//                        System.out.printf("%-20sR$%.2f (%d %s)\n", item.getName(), item.getFinalPrice(), productCount, productCount > 1 ? "units" : "unit");
//                    }
//            }
//            for(Product item : list) {
//                if(productMap.containsKey(item.getName())) continue;
//                productMap.put(item.getName(), item);
//                System.out.printf("%-20sR$%.2f (%d %s)\n", item.getName(), item.getFinalPrice(), productCount, productCount > 1 ? "units" : "unit");
//
//            }
        }
//        System.out.println("\n======================================");

    public static void searchItem(String itemName){
        for(Product item : stock) {
            if(item.getName().equals(itemName)) {
                System.out.println("\nItem found!");
                System.out.printf("%-20sR$%.2f (%d %s)\n", item.getName(), item.getFinalPrice(), item.getQuantity(), item.getQuantity() > 1 ? "units" : "unit");
                return;
            }
        }
        System.out.println("Item not found!");
    }

    // changed
    public static void printStock(int sortType) {
        sortType = (sortType == 0 ? 1 : sortType);
        String previousCategory = null;
//        switch (sortType) {
//            case 1:
//                electronicsList.sort(Comparator.comparing(Product::getName));
//                groceriesList.sort(Comparator.comparing(Product::getName));
//                clothingList.sort(Comparator.comparing(Product::getName));
//                printSortedStock();
//                break;
//            case 2:
//                electronicsList.sort(Comparator.comparing(Product::getFinalPrice).reversed());
//                groceriesList.sort(Comparator.comparing(Product::getFinalPrice).reversed());
//                clothingList.sort(Comparator.comparing(Product::getFinalPrice).reversed());
//                printSortedStock();
//                break;
//        }
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
            System.out.println(product);
        }
    }

    public static boolean isEmpty() {
        return stock.isEmpty();
    }
}
