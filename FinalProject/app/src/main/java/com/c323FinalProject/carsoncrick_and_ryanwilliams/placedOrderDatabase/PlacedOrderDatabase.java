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
            setUpOrderDatabase();
        }
        return INSTANCE;
    }

    private static void setUpOrderDatabase() {
        PlacedOrder[] placedOrders = new PlacedOrder[5];
        PlacedOrderDao placedOrderDao = INSTANCE.getPlacedOrderItemDao();
        placedOrders[0] = new PlacedOrder(100, 10, "12/10/2020", "11:00 AM", "McAlister's", "1014 View Dr", 5);
        placedOrders[1] = new PlacedOrder(8, 1, "12/11/2020", "7:00 PM", "McDonald's", "9999 McPlace Dr", 1);
        placedOrders[2] = new PlacedOrder(23, 3, "12/13/2020", "1:00 PM", "Panda Express", "778 Place Ave", 7);
        placedOrders[3] = new PlacedOrder(77, 7, "12/15/2020", "8:00 AM", "Chipolte", "879 Spot Ln", 2);
        placedOrders[4] = new PlacedOrder(6, 1, "12/16/2020", "11:00 PM", "Starbucks", "5987 Sea Ln", 4);
        placedOrderDao.insertPlacedOrder(placedOrders);
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
