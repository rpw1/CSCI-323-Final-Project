package com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.FtsOptions;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Database(entities = {Restaurant.class, OrderItem.class, RestaurantOrderItemMap.class }, version = 1)
public abstract class RestaurantDatabase extends RoomDatabase {
    public abstract RestaurantItemDao getRestaurantItemDao();

    private static RestaurantDatabase INSTANCE;

    /**
     * This function grabs the database instance and creates one if INSTANCE is null
     * I got this here https://medium.com/@ajaysaini.official/building-database-with-room-persistence-library-ecf7d0b8f3e9
     * @param context
     * @return
     */
    public static RestaurantDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    RestaurantDatabase.class, "restaurantDb").allowMainThreadQueries().build();
//            new Thread(() -> context.deleteDatabase("restaurantDb")).start();
            setUpDatabase();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * This function adds Restaurants to the database if the database is empty.
     * I got this here https://stackoverflow.com/questions/53459317/saving-multiple-arraylist-in-room-database-best-way-of-doing-it
     */
    private static void setUpDatabase() {
        new Thread(() -> {
            RestaurantItemDao restaurantItemDao = INSTANCE.getRestaurantItemDao();
            List<Restaurant> restaurantItems = restaurantItemDao.getAllRestaurants();
            Log.v("ROOM_INFO", "Restaurants: " + restaurantItems.size());
            if (restaurantItems.size() == 0) {
                Restaurant[] restaurantsList = new Restaurant[]{
                        new Restaurant("McDonalds"),
                        new Restaurant("Chipotle"),
                        new Restaurant("Noodles & Company"),
                        new Restaurant("Starbucks"),
                        new Restaurant("McAlister's Deli"),
                        new Restaurant("Five Guys"),
                        new Restaurant("Panda Express"),
                        new Restaurant("Domino's Pizza")
                };

                restaurantItemDao.insertRestaurants(restaurantsList);

                OrderItem[] mcDonaldsOrderItems = new OrderItem[] {
                        new OrderItem("Big Mac Meal", 8),
                        new OrderItem("Fries", 2),
                        new OrderItem("McNuggets Meal", 7),
                        new OrderItem("Fountain Drink", 1)
                };

                OrderItem[] chipolteOrderItems = new OrderItem[] {
                        new OrderItem("Burrito", 10),
                        new OrderItem("Chips & Queso", 4),
                        new OrderItem("Taco", 8),
                        new OrderItem("Fountain Drink", 3)
                };

                OrderItem[] noodlesOrderItems = new OrderItem[] {
                        new OrderItem("Penne Rosa", 7),
                        new OrderItem("Mac & Cheese", 7),
                        new OrderItem("Spaghetti & Meatballs", 7),
                        new OrderItem("Fountain Drink", 3),
                        new OrderItem("Cookie", 3)
                };

                OrderItem[] starbucksOrderItems = new OrderItem[] {
                        new OrderItem("Latte", 6),
                        new OrderItem("Mocha", 6),
                        new OrderItem("Turkey & Basil Pesto", 7)
                };

                OrderItem[] mcalistersOrderItems = new OrderItem[] {
                        new OrderItem("French Dip", 10),
                        new OrderItem("Sweet Chipolte Chicken", 8),
                        new OrderItem("Giant Spud", 8),
                        new OrderItem("Iced Tea", 3),
                        new OrderItem("Lemonade", 3)
                };

                OrderItem[] fiveGuysOrderItems = new OrderItem[] {
                        new OrderItem("Cheese Burger", 8),
                        new OrderItem("Little Cheese Burger", 6),
                        new OrderItem("Fries", 4),
                        new OrderItem("Fountain Drink", 3),
                };

                OrderItem[] pandaExpressOrderItems = new OrderItem[] {
                        new OrderItem("Sesame Chicken", 8),
                        new OrderItem("Orange Chicken", 8),
                        new OrderItem("Chow Mein", 4),
                        new OrderItem("Fried Rice", 4),
                        new OrderItem("Fountain Drink", 2)
                };

                OrderItem[] dominosOrderItems = new OrderItem[] {
                        new OrderItem("Cheese Pizza", 10),
                        new OrderItem("Pepperoni Pizza", 12),
                        new OrderItem("Mushroom Pizza", 12),
                        new OrderItem("Fountain Drink", 2)
                };

                OrderItem[][] orderItemsArrays = new OrderItem[][] {
                        mcDonaldsOrderItems, chipolteOrderItems, noodlesOrderItems,
                        starbucksOrderItems, mcalistersOrderItems, fiveGuysOrderItems,
                        pandaExpressOrderItems, dominosOrderItems
                };

                restaurantItemDao.insertOrderItems(mcDonaldsOrderItems);
                restaurantItemDao.insertOrderItems(chipolteOrderItems);
                restaurantItemDao.insertOrderItems(noodlesOrderItems);
                restaurantItemDao.insertOrderItems(starbucksOrderItems);
                restaurantItemDao.insertOrderItems(mcalistersOrderItems);
                restaurantItemDao.insertOrderItems(fiveGuysOrderItems);
                restaurantItemDao.insertOrderItems(pandaExpressOrderItems);
                restaurantItemDao.insertOrderItems(dominosOrderItems);

                RestaurantOrderItemMap[] restaurantMaps = new RestaurantOrderItemMap[
                        getArraysLength(orderItemsArrays)];

                int position = 0;
                for (int i = 0; i < orderItemsArrays.length; i++) {
                    OrderItem[] currentOrderItemArray = orderItemsArrays[i];
                    for (int j = 0; j < currentOrderItemArray.length; j++) {
                        RestaurantOrderItemMap currentMap = new RestaurantOrderItemMap();
                        currentMap.restaurantId = i + 1;
                        currentMap.orderItemId = j + 1;
                        restaurantMaps[position] = currentMap;
                        position++;
                    }
                }
                restaurantItemDao.addRestaurantOrderItemsMap(restaurantMaps);
            }
        }).start();
    }

    /**
     * @return the length of the given double array
     */
    public static int getArraysLength(Object[][] doubleArray) {
        int length = 0;
        for (Object[] objectArray : doubleArray) {
            length += objectArray.length;
        }
        return length;
    }

}
