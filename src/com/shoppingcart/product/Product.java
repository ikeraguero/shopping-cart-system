package com.shoppingcart.product;

public abstract class Product {
    private String name;
    private double basePrice;
    private double finalPrice;
    private String category;
    private boolean isOnSale;
    private int discountPercentage;

    public Product(String name, double basePrice, String category, boolean isOnSale, int discountPercentage) {
        this.name = name;
        this.basePrice = basePrice;
        this.category = category;
        this.isOnSale = isOnSale;
        this.discountPercentage = discountPercentage;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
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



}
