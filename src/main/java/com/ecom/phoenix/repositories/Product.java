package com.ecom.phoenix.repositories;

public class Product {
    private long id;
    private String team;
    private String category;
    private String color;
    private double price;
    private int storage;
    private String directory;

    public Product() {
    }

    public Product(long id, String team, String category, String color, double price, int storage, String directory) {
        this.id = id;
        this.team = team;
        this.category = category;
        this.color = color;
        this.price = price;
        this.storage = storage;
        this.directory = directory;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", team='" + team + '\'' +
                ", directory='" + directory + '\'' +
                ", category='" + category + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", storage=" + storage +
                '}';
    }
}
