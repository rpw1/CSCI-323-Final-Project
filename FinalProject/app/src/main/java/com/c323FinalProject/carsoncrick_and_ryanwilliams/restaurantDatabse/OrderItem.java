package com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_items")
public class OrderItem {

    @PrimaryKey(autoGenerate = true)
    private int orderItemId;
    private String orderItemName;
    private int orderItemPrice;
    private int orderItemQuantity;

    public OrderItem(String orderItemName, int orderItemPrice) {
        this.orderItemName = orderItemName;
        this.orderItemPrice = orderItemPrice;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderItemName() {
        return orderItemName;
    }

    public void setOrderItemName(String orderItemName) {
        this.orderItemName = orderItemName;
    }

    public int getOrderItemPrice() {
        return orderItemPrice;
    }

    public void setOrderItemPrice(int orderItemPrice) {
        this.orderItemPrice = orderItemPrice;
    }

    public int getOrderItemQuantity() {
        return orderItemQuantity;
    }

    public void setOrderItemQuantity(int orderItemQuantity) {
        this.orderItemQuantity = orderItemQuantity;
    }
}
