package com.shoppingcart.product;

public abstract class Product {
    private final String name;
    private final double basePrice;
    private double finalPrice;
    private String category;
    private final boolean isOnSale;
    private final int discountPercentage;
    private int inventory;

    public Product(String name, double basePrice, boolean isOnSale, int discountPercentage, int inventory) {
        this.name = name;
        this.basePrice = basePrice;
        this.category = getClass().getName().toLowerCase();
        this.isOnSale = isOnSale;
        this.discountPercentage = discountPercentage;
        this.inventory = inventory;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public String getName() {
        return name;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString() {
        return """
                Product: %s
                Total Price: R$%.2f
                """.formatted(name, finalPrice);
    }

    public abstract void calculatePrice();


    public abstract boolean hasWarranty();
}
