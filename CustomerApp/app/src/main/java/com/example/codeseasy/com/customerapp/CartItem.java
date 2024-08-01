package com.example.codeseasy.com.customerapp;

public class CartItem {
    private String name;
    private String price;

    public CartItem(String name, String description, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
