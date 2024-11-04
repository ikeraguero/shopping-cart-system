package com.shoppingcart.product;

import com.shoppingcart.stock.Stock;

public class Clothing extends Product{
    public Clothing(String name, double basePrice, String isOnSale,
                    int discountPercentage, String hasWarranty, int quantity) {
        super(name, basePrice, isOnSale, discountPercentage, hasWarranty, quantity, "clothing");
        this.calculatePrice();
    }

    public static Product createClothing(String productName, double basePrice,
                                         String isOnSale, int discountPercentage, String hasWarranty, int quantity) {
        return new Clothing(productName, basePrice, isOnSale, discountPercentage, hasWarranty,  quantity);
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
