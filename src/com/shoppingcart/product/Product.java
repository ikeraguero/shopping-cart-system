package com.shoppingcart.product;

public abstract class Product implements Cloneable {
    private final String name;
    private final double basePrice;
    private double finalPrice;
    private String category;
    private final boolean isOnSale;
    private final int discountPercentage;
    private int quantity;

    public Product(String name, double basePrice, boolean isOnSale, int discountPercentage, int quantity, String category) {
        this.name = name;
        this.basePrice = basePrice;
        this.isOnSale = isOnSale;
        this.discountPercentage = discountPercentage;
        this.quantity = quantity;
        this.category = category;
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

    public Double getFinalPrice() {
        return finalPrice;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setInventory(int quantity) {
        this.quantity = quantity;
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
                %-20sR$%.2f
                """.formatted(name, finalPrice);
    }

    public abstract void calculatePrice();


    public abstract boolean hasWarranty();

}
