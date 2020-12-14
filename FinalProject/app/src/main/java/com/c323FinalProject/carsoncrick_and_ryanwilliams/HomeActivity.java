package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.OrderItem;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.Restaurant;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantDatabase;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantItemDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantOrderItemMap;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    SharedPreferences sharedPreferences;
    RestaurantItemDao restaurantItemDao;
    RestaurantDatabase restaurantDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initializing nav drawer
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        // I found this here: https://stackoverflow.com/questions/34973456/how-to-change-text-of-a-textview-in-navigation-drawer-header
        View headerView = navigationView.getHeaderView(0);

        TextView textViewName = headerView.findViewById(R.id.tvHeaderUsername);
        TextView textViewEmail = headerView.findViewById(R.id.tvHeaderUserEmail);
        ImageView imageViewUser = headerView.findViewById(R.id.imageViewHeader);

        this.sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        HashMap<String, String> loginInfo = (HashMap<String, String>) sharedPreferences.getAll();
        if (loginInfo.containsKey("name") && loginInfo.containsKey("email")){
            textViewName.setText(loginInfo.get("name"));
            textViewEmail.setText(loginInfo.get("email"));
        } else {
            Toast.makeText(this, "DATA DIDN'T SAVE", Toast.LENGTH_SHORT).show();
        }

        if (loginInfo.containsKey("image")) {
            Bitmap bitmapUser = stringToBitmap(loginInfo.get("image"));
            if (bitmapUser != null)
                imageViewUser.setImageBitmap(bitmapUser);
        }

        //initializing toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setting up drawer toggle
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        this.restaurantDatabase = RestaurantDatabase.getAppDatabase(this);
//        new Thread(() -> this.deleteDatabase("restaurantDb")).start();
        this.restaurantItemDao = this.restaurantDatabase.getRestaurantItemDao();
        setUpDatabase();
    }

    /**
     * This function adds Restaurants to the database if the database is empty.
     * I got this here https://stackoverflow.com/questions/53459317/saving-multiple-arraylist-in-room-database-best-way-of-doing-it
     */
    private void setUpDatabase() {
        new Thread(() -> {
            List<Restaurant> restaurantItems = this.restaurantItemDao.getAllRestaurants();
            Log.v("ROOM_INFO", "Restaurants: " + restaurantItems.size());
            if (restaurantItems.size() == 0) {
                Restaurant[] restaurantsList = new Restaurant[]{new Restaurant("McDonalds"),
                        new Restaurant("Chipotle"), new Restaurant("Noodles & Company")};
                this.restaurantItemDao.insertRestaurants(restaurantsList);

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

                this.restaurantItemDao.insertOrderItems(mcDonaldsOrderItems);
                this.restaurantItemDao.insertOrderItems(chipolteOrderItems);
                this.restaurantItemDao.insertOrderItems(noodlesOrderItems);

                List<OrderItem> orderItemsDao = this.restaurantItemDao.getAllOrderItems();
                for (OrderItem orderItem : orderItemsDao) {
                    Log.v("ROOM_INFO", "ORDER ID: " + orderItem.getOrderItemId() + " ORDER NAME: " + orderItem.getOrderItemName());
                }

                List<Restaurant> restaurantListDao = this.restaurantItemDao.getAllRestaurants();
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
                this.restaurantItemDao.addRestaurantOrderItemsMap(restaurantMaps);
                this.restaurantDatabase.close();
            }
        }).start();
    }

    /**
     * @return the length of the given double array
     */
    public int getArraysLength(Object[][] doubleArray) {
        int length = 0;
        for (Object[] objectArray : doubleArray) {
            length += objectArray.length;
        }
        return length;
    }

    //click handler for nav drawer items
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.navDrawerRecentOrders:
                break;
            case R.id.navDrawerCalendarView:
                break;
        }
        return true;
    }

    /**
     * This function logs the user out of the app by clearing SharedPreferences and
     * sending the user back to the sign in activity.
     * @param view
     */
    public void logout(View view) {
        this.sharedPreferences.edit().clear().commit();
        startActivity(new Intent(this, SignInActivity.class));
    }

    /**
     * This function takes a Base64 encoded string and turns it into a Bitmap
     * I got this here: https://stackoverflow.com/questions/23005948/convert-string-to-bitmap/23006132
     * @param encodedString
     * @return
     */
    public Bitmap stringToBitmap(String encodedString) {
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}