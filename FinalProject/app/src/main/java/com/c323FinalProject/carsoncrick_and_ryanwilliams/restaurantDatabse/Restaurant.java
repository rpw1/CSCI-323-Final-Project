package com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurants")
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    @NonNull private int restaurantId;
    private String restaurantName;
    private boolean isFavorite;

    public Restaurant(String restaurantName) {
        this.restaurantName = restaurantName;
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

    public boolean getIsFavorite() {
        return this.isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

}
