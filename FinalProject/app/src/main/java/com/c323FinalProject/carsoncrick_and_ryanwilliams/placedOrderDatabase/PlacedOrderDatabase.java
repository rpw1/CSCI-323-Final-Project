package com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantDatabase;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantItemDao;

@Database(entities = {PlacedOrder.class} , version = 1)
public abstract class PlacedOrderDatabase extends RoomDatabase {

    public abstract PlacedOrderDao getPlacedOrderItemDao();
    private static PlacedOrderDatabase INSTANCE;

    /**
     * This function grabs the database instance and creates one if INSTANCE is null
     * I got this here https://medium.com/@ajaysaini.official/building-database-with-room-persistence-library-ecf7d0b8f3e9
     * @param context
     * @return
     */
    public static PlacedOrderDatabase getPlacedOrderDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    PlacedOrderDatabase.class, "placedOrderDb").allowMainThreadQueries().build();
            new Thread(() -> context.deleteDatabase("placedOrderDb")).start();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
