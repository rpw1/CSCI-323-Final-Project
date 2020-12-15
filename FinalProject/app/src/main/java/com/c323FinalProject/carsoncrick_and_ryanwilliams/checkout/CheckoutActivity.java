package com.c323FinalProject.carsoncrick_and_ryanwilliams.checkout;

import androidx.appcompat.app.AppCompatActivity;
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

public class CheckoutActivity extends AppCompatActivity {

    String restaurantName;
    OrderItem[] orderItems;
    RecyclerView recyclerView;
    EditText textViewAddress;
    CheckoutAdapter checkoutAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Intent intent = getIntent();
        this.orderItems = (OrderItem[]) intent.getSerializableExtra("order");
        this.restaurantName = intent.getStringExtra("name");
        this.recyclerView = findViewById(R.id.checkoutRecycler);
        this.checkoutAdapter = new CheckoutAdapter(this, this.orderItems);
        this.recyclerView.setAdapter(this.checkoutAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.textViewAddress = findViewById(R.id.tvDeliveryAddress);
    }

    public void modifyOrder(View view) {
        finish();
    }

    public void placeOrder(View view) {
        Intent intent = new Intent(this, OrdersActivity.class);
        intent.putExtra("order", this.orderItems);
        intent.putExtra("name", this.restaurantName);
        intent.putExtra("address", this.textViewAddress.getText().toString());
    }
}