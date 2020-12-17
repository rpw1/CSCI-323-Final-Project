package com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurants")
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    @NonNull private int restaurantId;
    private String restaurantName;
    private String restaurantLocation;
    private String restaurantImageOne;
    private String restaurantImageTwo;
    private String restaurantImageThree;
    private boolean isFavorite;

    public Restaurant(String restaurantName, String restaurantLocation, String restaurantImageOne, String restaurantImageTwo, String restaurantImageThree) {
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.restaurantImageOne = restaurantImageOne;
        this.restaurantImageTwo = restaurantImageTwo;
        this.restaurantImageThree = restaurantImageThree;
        this.isFavorite = false;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public String getRestaurantImageOne() {
        return restaurantImageOne;
    }

    public void setRestaurantImageOne(String restaurantImageOne) {
        this.restaurantImageOne = restaurantImageOne;
    }

    public String getRestaurantImageTwo() {
        return restaurantImageTwo;
    }

    public void setRestaurantImageTwo(String restaurantImageTwo) {
        this.restaurantImageTwo = restaurantImageTwo;
    }

    public String getRestaurantImageThree() {
        return restaurantImageThree;
    }

    public void setRestaurantImageThree(String restaurantImageThree) {
        this.restaurantImageThree = restaurantImageThree;
    }

    public boolean getIsFavorite() {
        return this.isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

}
