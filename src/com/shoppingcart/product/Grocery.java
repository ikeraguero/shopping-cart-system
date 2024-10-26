package com.shoppingcart.product;

import com.shoppingcart.stock.Stock;



public class Grocery extends Product{
    public Grocery(String name, double basePrice, boolean isOnSale, int discountPercentage, int quantity) {
        super(name, basePrice, isOnSale, discountPercentage, quantity, "groceries");
        this.calculatePrice();
    }

    public static void createGrocery(String productName, double basePrice, String isOnSale, int discountPercentage, String hasWarranty, int quantity) {
        Product grocery = new Grocery(productName, basePrice, (isOnSale.equals("Y")), discountPercentage,  quantity);
        Stock.addItemStock(grocery);
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
