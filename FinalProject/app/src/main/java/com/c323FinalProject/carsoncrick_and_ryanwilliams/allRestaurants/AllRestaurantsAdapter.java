package com.c323FinalProject.carsoncrick_and_ryanwilliams.allRestaurants;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.R;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.RestaurantActivity;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.Restaurant;

import java.util.List;

public class AllRestaurantsAdapter extends RecyclerView.Adapter<AllRestaurantsAdapter.AllRestaurantsViewHolder> {

    Context context;
    List<Restaurant> restaurants;

    public AllRestaurantsAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurants = restaurantList;
    }

    @NonNull
    @Override
    public AllRestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.all_restaurant_card_layout, parent, false);
        return new AllRestaurantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllRestaurantsViewHolder holder, int position) {
        Restaurant restaurant = this.restaurants.get(position);
        Log.v("stuff", (restaurant == null) + "");
        if (restaurant != null)
            holder.textViewName.setText(restaurant.getRestaurantName());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class AllRestaurantsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName;

        public AllRestaurantsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.tvAllRestaurantItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, RestaurantActivity.class);
            intent.putExtra("Restaurant Id", restaurants.get(getAdapterPosition()).getRestaurantId());
            intent.putExtra("Restaurant Name", restaurants.get(getAdapterPosition()).getRestaurantName());
            context.startActivity(intent);
        }
    }
}
