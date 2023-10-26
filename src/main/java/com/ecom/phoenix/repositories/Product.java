package com.ecom.phoenix.repositories;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class Product {
    private long id;
    private String team;
    private String category;
    private ArrayNode color;
    private double price;
    private int storage;
    private String directory;
    private String league;

    public Product() {
    }

    public Product(long id, String team, String category, ArrayNode color, double price, int storage, String directory, String league) {
        this.id = id;
        this.team = team;
        this.category = category;
        this.color = color;
        this.price = price;
        this.storage = storage;
        this.directory = directory;
        this.league = league;
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

    public ArrayNode getColor() {
        return color;
    }

    public void setColor(ArrayNode color) {
        if( color != null && color.isNull() && color.get(1) != null && color.get(1).asText().equals("null")){
            color = null;
        }
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

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", team='" + team + '\'' +
                ", directory='" + directory + '\'' +
                ", category='" + category + '\'' +
                ", league='" + league + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", storage=" + storage +
                '}';
    }
}
