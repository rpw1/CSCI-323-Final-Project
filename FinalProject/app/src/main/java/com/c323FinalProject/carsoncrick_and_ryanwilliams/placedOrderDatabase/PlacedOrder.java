package com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.OrderItem;

import java.util.List;

@Entity(tableName = "placed_orders")
public class PlacedOrder {

    @PrimaryKey(autoGenerate = true)
    @NonNull private int placed_order_id;

    @ColumnInfo(name = "ordered_food_items")
    String ordered_food_items;

    @ColumnInfo(name = "total_price")
    int total_price;

    @ColumnInfo(name = "total_quantity")
    int total_quantity;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "time")
    String time;

    @ColumnInfo(name = "restaurant_name")
    String restaurant_name;

    @ColumnInfo(name = "address")
    String address;

    @ColumnInfo(name = "restaurant_id")
    int restaurant_id;

    public PlacedOrder(int total_price, int total_quantity, String date, String time, String restaurant_name, String address, int restaurant_id) {
        this.total_price = total_price;
        this.total_quantity = total_quantity;
        this.date = date;
        this.time = time;
        this.restaurant_name = restaurant_name;
        this.address = address;
        this.restaurant_id = restaurant_id;
    }

    public int getPlaced_order_id() {
        return placed_order_id;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public void setPlaced_order_id(int placed_order_id) {
        this.placed_order_id = placed_order_id;
    }

    public String getOrdered_food_items() {
        return ordered_food_items;
    }

    public void setOrdered_food_items(String ordered_food_items) {
        this.ordered_food_items = ordered_food_items;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRestaurantId() {
        return restaurant_id;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurant_id = restaurantId;
    }
}
