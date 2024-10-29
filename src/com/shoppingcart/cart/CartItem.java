package com.shoppingcart.cart;

import com.shoppingcart.product.Product;

public class CartItem extends Product{

    public CartItem(String productName, double basePrice, String isOnSale, int discountPercentage, String hasWarranty, int quantity, String category) {
        super(productName, basePrice, isOnSale, discountPercentage, hasWarranty, quantity, category);
    }

    @Override
    public void calculatePrice() {

    }

    @Override
    public boolean hasWarranty() {
        return false;
    }
}
