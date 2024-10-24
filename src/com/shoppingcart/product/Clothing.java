package com.shoppingcart.product;

public class Clothing extends Product{
    public Clothing(String name, double basePrice, boolean isOnSale, int discountPercentage, int inventory) {
        super(name, basePrice, isOnSale, discountPercentage, inventory, "clothing");
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
