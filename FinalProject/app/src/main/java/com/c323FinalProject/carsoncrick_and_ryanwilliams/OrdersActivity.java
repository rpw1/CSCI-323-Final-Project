package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrder;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrderDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrderDatabase;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.OrderItem;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantDatabase;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantItemDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantOrderItemMap;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    List<OrderItem> orderItems;
    TextView textViewFoodItem, textViewOrderedFrom, textViewPrice,
    textViewDate, textViewTime, textViewAddress;
    Button buttonTrackOrder;
    String restaurantName;
    int restaurantId;
    RestaurantDatabase restaurantDatabase;
    RestaurantItemDao restaurantItemDao;
    String addressString;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        this.orderItems = new ArrayList<>();
        this.restaurantItemDao = this.restaurantDatabase.getRestaurantItemDao();
        List<RestaurantOrderItemMap> restaurantOrderItemMaps = this.restaurantItemDao.getOrderItemFromMap(this.restaurantId);
        for (RestaurantOrderItemMap orderItemMap : restaurantOrderItemMaps) {
            OrderItem currentItem = this.restaurantItemDao.getOrderItemById(orderItemMap.orderItemId);
            if (currentItem.getOrderItemQuantity() > 0)
                this.orderItems.add(currentItem);
        }

        String foodItems = "";
        int price = 0;
        int quantity = 0;
        for (OrderItem orderItem : this.orderItems) {
            String quantityString = "Quantity: " + orderItem.getOrderItemQuantity();
            String nameString = orderItem.getOrderItemName();
            int padding = 50 - nameString.length();
            foodItems += String.format("%s %" + padding + "s %n", nameString, quantityString);
            quantity += orderItem.getOrderItemQuantity();
            price += orderItem.getOrderItemPrice() * orderItem.getOrderItemQuantity();
            orderItem.setOrderItemQuantity(0);
            this.restaurantItemDao.updateOrderItems(orderItem);
        }
        foodItems = foodItems.substring(0, foodItems.length() - 1);
        this.textViewFoodItem.setText(foodItems);
        this.textViewOrderedFrom.setText("Ordered from: " + this.restaurantName);
        this.textViewPrice.setText("Price: $" + price);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = simpleDateFormat.format(date);
        this.textViewDate.setText("Date: " + dateString);
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm a");
        String timeString = simpleDateFormatTime.format(date);
        this.textViewTime.setText("Time: " + timeString);
        this.textViewAddress.setText(this.addressString);
        PlacedOrderDatabase placedOrderDatabase = PlacedOrderDatabase.getPlacedOrderDatabase(this);
        PlacedOrderDao placedOrderDao = placedOrderDatabase.getPlacedOrderItemDao();
        placedOrderDao.insertPlacedOrder(new PlacedOrder(price, quantity, dateString, timeString,
                restaurantName, addressString, restaurantId, foodItems));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        this.restaurantDatabase = RestaurantDatabase.getAppDatabase(this);
        Intent intent = getIntent();
        this.restaurantId = intent.getIntExtra("id", -1);
        this.restaurantName = intent.getStringExtra("name");
        this.addressString = intent.getStringExtra("address");
        this.textViewFoodItem = findViewById(R.id.tvOrderActAllFoodItems);
        this.textViewOrderedFrom = findViewById(R.id.tvOrderActOrderedFrom);
        this.textViewPrice = findViewById(R.id.tvOrderActPrice);
        this.textViewDate = findViewById(R.id.tvOrderActDate);
        this.textViewTime = findViewById(R.id.tvOrderActTime);
        this.textViewAddress = findViewById(R.id.tvOrderActAddress);
        this.restaurantItemDao = this.restaurantDatabase.getRestaurantItemDao();
    }
}