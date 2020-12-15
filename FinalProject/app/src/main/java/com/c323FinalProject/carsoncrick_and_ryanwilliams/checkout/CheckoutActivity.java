package com.c323FinalProject.carsoncrick_and_ryanwilliams.checkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.FtsOptions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.OrdersActivity;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.R;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.OrderItem;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantDatabase;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantItemDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantOrderItemMap;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    String restaurantName;
    List<OrderItem> orderItems;
    RecyclerView recyclerView;
    EditText textViewAddress;
    CheckoutAdapter checkoutAdapter;
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
        this.checkoutAdapter = new CheckoutAdapter(this, this.orderItems);
        //attach item touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        this.recyclerView.setAdapter(this.checkoutAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        this.restaurantDatabase = RestaurantDatabase.getAppDatabase(this);
        Intent intent = getIntent();
        this.restaurantId = intent.getIntExtra("id", -1);
        this.restaurantName = intent.getStringExtra("name");
        this.recyclerView = findViewById(R.id.checkoutRecycler);
        this.restaurantItemDao = this.restaurantDatabase.getRestaurantItemDao();
        this.textViewAddress = findViewById(R.id.tvDeliveryAddress);
    }

    public void modifyOrder(View view) {
        finish();
    }

    public void placeOrder(View view) {
        Intent intent = new Intent(this, OrdersActivity.class);
        intent.putExtra("id", restaurantId);
        intent.putExtra("name", this.restaurantName);
        intent.putExtra("address", this.textViewAddress.getText().toString());
        startActivity(intent);
    }

    //right swipe handler for recycler view items
    //Source: https://www.youtube.com/watch?v=M1XEqqo6Ktg&ab_channel=CodingWithMitch
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            //delete item and notify dataset
            orderItems.remove(orderItems.get(viewHolder.getAdapterPosition()));
            checkoutAdapter.notifyDataSetChanged();
        }
    };
}