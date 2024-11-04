package com.shoppingcart.product;

import com.shoppingcart.stock.Stock;

public class Electronics extends Product{
    private static final double warrantyPrice = 300.00;

    public Electronics(String name, double basePrice, String isOnSale,
                       int discountPercentage, String hasWarranty, int quantity) {
        super(name, basePrice, isOnSale, discountPercentage, hasWarranty, quantity, "electronics");
        this.calculatePrice();
    }

    public static Product createElectronic(String productName, double basePrice, String isOnSale,
                                           int discountPercentage, String hasWarranty, int quantity) {
        return new Electronics(productName, basePrice, isOnSale, discountPercentage, hasWarranty,  quantity);
    }

    public boolean hasWarranty() {
        return hasWarranty;
    }

    public void calculatePrice() {
            setFinalPrice((getBasePrice()) - (getBasePrice() * getDiscountPercentage()/100) + (hasWarranty ? warrantyPrice : 0));
    }
}
