package com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RestaurantItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRestaurants(Restaurant... restaurants);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOrderItems(OrderItem... orderItems);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addRestaurantOrderItemsMap(RestaurantOrderItemMap... maps);

    @Update
    void updateRestaurants(Restaurant... restaurants);

    @Update
    void updateOrderItems(OrderItem... orderItems);

    @Delete
    void deleteRestaurants(Restaurant... restaurants);

    @Delete
    void deleteOrderItems(OrderItem... orderItems);

    @Query("DELETE FROM restaurants WHERE restaurantName = :name")
    int deleteRestaurantByName(String name);

    @Query("DELETE FROM order_items WHERE orderItemName = :name")
    int deleteOrderItemByName(String name);

    @Query("SELECT * FROM restaurants")
    List<Restaurant> getAllRestaurants();

    @Query("SELECT * FROM order_items")
    List<OrderItem> getAllOrderItems();

    @Query("SELECT * FROM restaurants WHERE restaurantId= :id")
    Restaurant getRestaurantById(int id);

    @Query("SELECT * FROM order_items WHERE orderItemId= :id")
    OrderItem getOrderItemById(int id);

    @Query("SELECT * FROM restaurantorderitemmap")
    List<RestaurantOrderItemMap> getRestaurantOrderItems();

    @Query("SELECT * FROM restaurantorderitemmap WHERE restaurant_reference= :restaurant_id")
    List<RestaurantOrderItemMap> getOrderItemFromMap(int restaurant_id);

}
