package com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.room.Database;
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
                Restaurant[] restaurantsList = new Restaurant[]{new Restaurant("McDonalds"),
                        new Restaurant("Chipotle"), new Restaurant("Noodles & Company")};
                restaurantItemDao.insertRestaurants(restaurantsList);

                OrderItem[] mcDonaldsOrderItems = new OrderItem[] {
                        new OrderItem("Burger", 5),
                        new OrderItem("Fries", 3),
                        new OrderItem("Fountain Drink", 2)
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

                OrderItem[][] orderItemsArrays = new OrderItem[][] {
                        mcDonaldsOrderItems, chipolteOrderItems, noodlesOrderItems
                };

                restaurantItemDao.insertOrderItems(mcDonaldsOrderItems);
                restaurantItemDao.insertOrderItems(chipolteOrderItems);
                restaurantItemDao.insertOrderItems(noodlesOrderItems);

                List<OrderItem> orderItemsDao = restaurantItemDao.getAllOrderItems();
                for (OrderItem orderItem : orderItemsDao) {
                    Log.v("ROOM_INFO", "ORDER ID: " + orderItem.getOrderItemId() + " ORDER NAME: " + orderItem.getOrderItemName());
                }

                List<Restaurant> restaurantListDao = restaurantItemDao.getAllRestaurants();
                for (Restaurant restaurant : restaurantListDao) {
                    Log.v("ROOM_INFO", "RESTAURANT ID: " + restaurant.getRestaurantId() + " RESTAURANT NAME: " + restaurant.getRestaurantName());
                }

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
