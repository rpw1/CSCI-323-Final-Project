package com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantFoodItems;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class RestaurantFoodItemsAdapter extends RecyclerView.Adapter<RestaurantFoodItemsAdapter.RestaurantFoodItemsViewHolder> {

    List<OrderItem> orderItems = new ArrayList<>();
    Context context;

    public RestaurantFoodItemsAdapter(Context context, List<OrderItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public RestaurantFoodItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantFoodItemsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RestaurantFoodItemsViewHolder extends RecyclerView.ViewHolder {

        public RestaurantFoodItemsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
