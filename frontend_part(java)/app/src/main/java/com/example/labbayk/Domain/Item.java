package com.example.labbayk.Domain;

import java.io.Serializable;

public class Item implements Serializable {


    private int id;
    private int timeValue;
    private int numberInCart;
    private String title;
    private String description;
    private String imagePath;
    private double price;
    private double star;
    private boolean bestFood;

    @Override
    public String toString() {
        return title;
    }


//    public Item(int id, int timeValue, int numberInCart, String title, String description, String imagePath, double price, double star) {
//        Id = id;
//        TimeValue = timeValue;
//        Title = title;
//        Description = description;
//        ImagePath = imagePath;
//        Price = price;
//        Star = star;
//        NumberInCart = numberInCart;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(int timeValue) {
        this.timeValue = timeValue;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public boolean isBestFood() {
        return bestFood;
    }

    public void setBestFood(boolean bestFood) {
        this.bestFood = bestFood;
    }

}
