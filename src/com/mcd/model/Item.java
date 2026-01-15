package com.mcd.model;

public class Item {
    public String name;
    public double price;
    public String imagePath;

    public Item(String name, double price, String imagePath) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return name + " - Rs. " + price;
    }
}
