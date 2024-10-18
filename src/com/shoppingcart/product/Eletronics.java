package com.shoppingcart.product;

public class Eletronics extends Product{
    private boolean hasWarranty;
    private static double warrantyPrice = 300.00;

    public Eletronics(String name, double basePrice, String category, boolean isOnSale, int discountPercentage, boolean hasWarranty) {
        super(name, basePrice, category, isOnSale, discountPercentage);
        this.hasWarranty = hasWarranty;
        this.calculatePrice();
    }

    public void calculatePrice() {
            setFinalPrice((getBasePrice()) - (getBasePrice() * getDiscountPercentage()) + (hasWarranty ? warrantyPrice : 0));
    }
}
