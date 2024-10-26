package com.shoppingcart.product;

import com.shoppingcart.stock.Stock;

public class Electronics extends Product{
    private final boolean hasWarranty;
    private static final double warrantyPrice = 300.00;

    public Electronics(String name, double basePrice, boolean isOnSale, int discountPercentage, boolean hasWarranty, int quantity) {
        super(name, basePrice, isOnSale, discountPercentage, quantity, "electronics");
        this.hasWarranty = hasWarranty;
        this.calculatePrice();
    }

    public static void createElectronic(String productName, double basePrice, String isOnSale, int discountPercentage, String hasWarranty, int quantity) {
        Product electronics = new Electronics(productName, basePrice, (isOnSale.equals("Y")), discountPercentage,(hasWarranty.equals("Y")), quantity);
        Stock.addItemStock(electronics);
        Stock.printStock(0);
    }

    public boolean hasWarranty() {
        return hasWarranty;
    }

    public void calculatePrice() {
            setFinalPrice((getBasePrice()) - (getBasePrice() * getDiscountPercentage()/100) + (hasWarranty ? warrantyPrice : 0));
    }
}
