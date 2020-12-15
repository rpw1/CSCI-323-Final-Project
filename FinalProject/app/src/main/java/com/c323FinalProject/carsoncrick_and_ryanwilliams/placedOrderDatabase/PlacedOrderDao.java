package com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.Restaurant;

import java.util.List;

@Dao
public interface PlacedOrderDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPlacedOrder(PlacedOrder... placed_orders);

    @Query("SELECT * FROM placed_orders")
    List<PlacedOrder> getAllPlacedOrders();
}
