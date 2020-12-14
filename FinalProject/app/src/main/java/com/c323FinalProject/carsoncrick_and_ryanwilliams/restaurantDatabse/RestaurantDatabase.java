package com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Restaurant.class, OrderItem.class, RestaurantOrderItemMap.class }, version = 1)
public abstract class RestaurantDatabase extends RoomDatabase {
    public abstract RestaurantItemDao getRestaurantItemDao();

    private static RestaurantDatabase INSTANCE;

    public static RestaurantDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    RestaurantDatabase.class, "restaurantDb").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
