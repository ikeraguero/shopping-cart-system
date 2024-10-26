package com.shoppingcart.product;

import com.shoppingcart.stock.Stock;

public class Clothing extends Product{
    public Clothing(String name, double basePrice, boolean isOnSale, int discountPercentage, int quantity) {
        super(name, basePrice, isOnSale, discountPercentage, quantity, "clothing");
        this.calculatePrice();
    }

    public static void createClothing(String productName, double basePrice, String isOnSale, int discountPercentage, String hasWarranty, int quantity) {
        Product clothing = new Clothing(productName, basePrice, (isOnSale.equals("Y")), discountPercentage, quantity);
        Stock.addItemStock(clothing);
        Stock.printStock(0);
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
