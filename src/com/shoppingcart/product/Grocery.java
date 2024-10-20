package com.shoppingcart.product;

public class Grocery extends Product{
    public Grocery(String name, double basePrice, boolean isOnSale, int discountPercentage, int inventory) {
        super(name, basePrice, isOnSale, discountPercentage, inventory, "groceries");
        this.calculatePrice();
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
