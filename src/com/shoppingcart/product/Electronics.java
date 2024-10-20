package com.shoppingcart.product;

public class Electronics extends Product{
    private final boolean hasWarranty;
    private static final double warrantyPrice = 300.00;

    public Electronics(String name, double basePrice, boolean isOnSale, int discountPercentage, boolean hasWarranty, int inventory) {
        super(name, basePrice, isOnSale, discountPercentage, inventory);
        this.hasWarranty = hasWarranty;
        this.calculatePrice();
    }

    public boolean hasWarranty() {
        return hasWarranty;
    }

    public void calculatePrice() {
            setFinalPrice((getBasePrice()) - (getBasePrice() * getDiscountPercentage()) + (hasWarranty ? warrantyPrice : 0));
    }
}
