package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantFoodItems.RestaurantFoodItemsFragment;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantImage.RestaurantImageFragment;

public class RestaurantActivity extends AppCompatActivity implements RestaurantFoodItemsFragment.RestaurantFoodItemsInterface, RestaurantImageFragment.RestaurantImageInterface {

    int restaurantId;
    String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Intent intent = getIntent();
        this.restaurantId = intent.getIntExtra("Restaurant Id", -1);
        this.restaurantName = intent.getStringExtra("Restaurant Name");
    }

    @Override
    public int getRestaurantId() {
        return this.restaurantId;
    }

    @Override
    public String getRestaurantName() {
        return this.restaurantName;
    }

    @Override
    public int getRestaurantIdImage() {
        return this.restaurantId;
    }
}