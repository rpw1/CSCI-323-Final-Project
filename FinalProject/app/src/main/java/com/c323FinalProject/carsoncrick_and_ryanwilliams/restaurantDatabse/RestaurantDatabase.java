package com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.room.Database;
import androidx.room.FtsOptions;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Database(entities = {Restaurant.class, OrderItem.class, RestaurantOrderItemMap.class }, version = 1)
public abstract class RestaurantDatabase extends RoomDatabase {

    private static ArrayList<String> imageStrings;
    public abstract RestaurantItemDao getRestaurantItemDao();
    private static RestaurantDatabase INSTANCE;
    private static Thread databaseThread;
    private static Thread imageThread;

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
            imageStrings = new ArrayList<>();
            compressImages(context);
            setUpDatabase(imageStrings);
            int NUM_CORES = Runtime.getRuntime().availableProcessors();
            ThreadPoolExecutor executor = new ThreadPoolExecutor(NUM_CORES * 2,
                    NUM_CORES * 2,
                    60L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>());
            executor.execute(new Thread(() -> {
                imageThread.start();
                try {
                    imageThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                databaseThread.start();
            }));

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
    private static void setUpDatabase(ArrayList<String> imageStrings) {
        databaseThread = new Thread(() -> {
            RestaurantItemDao restaurantItemDao = INSTANCE.getRestaurantItemDao();
            List<Restaurant> restaurantItems = restaurantItemDao.getAllRestaurants();

            if (restaurantItems.size() == 0) {
                Restaurant[] restaurantsList = new Restaurant[]{
                        new Restaurant("McDonalds", "placeholder", imageStrings.get(0), imageStrings.get(1), imageStrings.get(2)),
                        new Restaurant("Chipotle", "placeholder", imageStrings.get(3), imageStrings.get(4), imageStrings.get(5)),
                        new Restaurant("Noodles & Company", "placeholder", imageStrings.get(6), imageStrings.get(7), imageStrings.get(8)),
                        new Restaurant("Starbucks", "placeholder", imageStrings.get(9), imageStrings.get(10), imageStrings.get(11)),
                        new Restaurant("McAlister's Deli", "placeholder", imageStrings.get(12), imageStrings.get(13), imageStrings.get(14)),
                        new Restaurant("Five Guys", "placeholder", imageStrings.get(15), imageStrings.get(16), imageStrings.get(17)),
                        new Restaurant("Panda Express", "placeholder", imageStrings.get(18), imageStrings.get(19), imageStrings.get(20)),
                        new Restaurant("Domino's Pizza", "placeholder", imageStrings.get(21), imageStrings.get(22), imageStrings.get(23))
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
                        currentMap.orderItemId = position + 1;
                        restaurantMaps[position] = currentMap;
                        position++;
                    }
                }

                restaurantItemDao.addRestaurantOrderItemsMap(restaurantMaps);
            }
        });
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

    /**
     * Gets all drawable restaurant images turns them into bitmaps, compresses them
     * into byte arrays, and then turns them into base64 strings  to be stored in the database
     * Image -> Bitmaps -> Byte Array -> Base 64 String
     * @param context
     * @return
     */
    public static void compressImages(Context context){
      imageThread = new Thread(() -> {

            ArrayList<Bitmap> bitmaps = new ArrayList<>();

            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.mcdonalds1));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.mcdonalds2));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.mcdonalds3));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.chipolte1));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.chipolte2));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.chipotle3));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.noodles1));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.noodles2));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.noodles3));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.starbuck1));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.starbucks2));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.starbuck3));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.mcalisters1));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.mcalisters2));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.mcalisters3));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.fiveguys1));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.fiveguys2));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.fiveguys3));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.panda1));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.panda2));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.panda3));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.dominos1));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.dominos2));
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.dominos3));

            //compress to byte arrays then to base 64
            for(int i = 0; i < bitmaps.size(); i++){
                Log.v("stuff", i+"");
                Bitmap bitmap = bitmaps.get(i);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                imageStrings.add(Base64.encodeToString(byteArray, Base64.DEFAULT));
            }


        });
    }

}
