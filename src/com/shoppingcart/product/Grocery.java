package com.shoppingcart.product;

public class Grocery extends Product{
    public Grocery(String name, double basePrice, String category, boolean isOnSale, int discountPercentage) {
        super(name, basePrice, category, isOnSale, discountPercentage);
        this.calculatePrice();
    }

    @Override
    public void calculatePrice() {
        setFinalPrice(getBasePrice() - (isOnSale() ? getBasePrice() * getDiscountPercentage()/100 : 0));
    }

}
