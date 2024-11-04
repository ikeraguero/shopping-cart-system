package com.shoppingcart.product;

import com.shoppingcart.stock.Stock;



public class Grocery extends Product{
    public Grocery(String name, double basePrice, String isOnSale,
                   int discountPercentage, String hasWarranty, int quantity) {
        super(name, basePrice, isOnSale, discountPercentage, hasWarranty, quantity, "groceries");
        this.calculatePrice();
    }

    public static Product createGrocery(String productName, double basePrice, String isOnSale,
                                        int discountPercentage, String hasWarranty, int quantity) {
        return new Grocery(productName, basePrice, isOnSale, discountPercentage, hasWarranty,  quantity);

    }

    @Override
    public void calculatePrice() {
        setFinalPrice(getBasePrice() - (isOnSale() ? getBasePrice() * getDiscountPercentage()/100 : 0));
    }

    @Override
    public boolean hasWarranty() {
        return false;
    }

}
