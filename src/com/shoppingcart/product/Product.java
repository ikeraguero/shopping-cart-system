package com.shoppingcart.product;

public abstract class Product {
    protected final String name;
    protected final double basePrice;
    protected double finalPrice;
    protected String category;
    protected final boolean isOnSale;
    protected final int discountPercentage;
    protected int quantity;
    protected boolean hasWarranty;

    public Product(String productName, double basePrice, String isOnSale, int discountPercentage, String hasWarranty, int quantity, String category) {
        this.name = productName;
        this.basePrice = basePrice;
        this.isOnSale = !isOnSale.equals("N");
        this.discountPercentage = discountPercentage;
        this.quantity = quantity;
        this.category = category;
        this.hasWarranty = !hasWarranty.equals("N");
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

    public void setQuantity(int quantity) {
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
                %-20sR$%.2f (%d)
                """.formatted(name, finalPrice, quantity);
    }

    public abstract void calculatePrice();


    public abstract boolean hasWarranty();

}
