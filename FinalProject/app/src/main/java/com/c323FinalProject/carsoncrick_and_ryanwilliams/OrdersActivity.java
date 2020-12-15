package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.OrderItem;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantDatabase;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantItemDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantOrderItemMap;

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
        for (OrderItem orderItem : this.orderItems) {
            foodItems += orderItem.getOrderItemName() + '\t' + "Quantity: " + orderItem.getOrderItemQuantity() + '\n';
            price += orderItem.getOrderItemPrice() * orderItem.getOrderItemQuantity();
        }
        this.textViewFoodItem.setText(foodItems);
        this.textViewOrderedFrom.setText("Ordered from: " + this.restaurantName);
        this.textViewPrice.setText("Price: $" + price);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        this.textViewDate.setText("Date: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        this.restaurantDatabase = RestaurantDatabase.getAppDatabase(this);
        Intent intent = getIntent();
        this.restaurantId = intent.getIntExtra("id", -1);
        this.restaurantName = intent.getStringExtra("name");
        this.textViewFoodItem = findViewById(R.id.tvOrderActAllFoodItems);
        this.textViewOrderedFrom = findViewById(R.id.tvOrderActAllFoodItems);
        this.textViewPrice = findViewById(R.id.tvOrderActPrice);
        this.textViewDate = findViewById(R.id.tvOrderActDate);
        this.textViewTime = findViewById(R.id.tvOrderActTime);
        this.textViewAddress = findViewById(R.id.tvOrderActAddress);
        this.restaurantItemDao = this.restaurantDatabase.getRestaurantItemDao();
    }
}