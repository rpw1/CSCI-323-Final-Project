package com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

// I got this here https://stackoverflow.com/questions/53459317/saving-multiple-arraylist-in-room-database-best-way-of-doing-it
@Entity(
        primaryKeys = {"restaurant_reference", "order_items_reference"},
        foreignKeys = {
                @ForeignKey(entity = Restaurant.class, parentColumns = "restaurantId", childColumns = "restaurant_reference"),
                @ForeignKey(entity = OrderItem.class, parentColumns = "orderItemId", childColumns = "order_items_reference")
        }
)
public class RestaurantOrderItemMap {

    @ColumnInfo (name = "restaurant_reference")
    public int restaurantId;

    @ColumnInfo (name = "order_items_reference")
    public int orderItemId;

}
