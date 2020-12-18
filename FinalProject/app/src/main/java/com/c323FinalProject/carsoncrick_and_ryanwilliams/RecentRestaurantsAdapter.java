package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.Restaurant;

import java.util.List;

public class RecentRestaurantsAdapter extends RecyclerView.Adapter<RecentRestaurantsAdapter.RecentRestaurantsViewHolder> {

    List<String> restaurantNames;
    List<Integer> restaurantIds;
    Context context;

    public RecentRestaurantsAdapter(Context context, List<String> restaurantNames, List<Integer> restaurantIds) {
        this.context = context;
        this.restaurantNames = restaurantNames;
        this.restaurantIds = restaurantIds;
    }

    @NonNull
    @Override
    public RecentRestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.recent_restaurant_card_layout, parent, false);
        return new RecentRestaurantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentRestaurantsViewHolder holder, int position) {
        holder.textView.setText(this.restaurantNames.get(position));
    }

    @Override
    public int getItemCount() {
        return this.restaurantNames.size();
    }

    public class RecentRestaurantsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        public RecentRestaurantsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.tvRecentOrderRestaurant);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, RestaurantActivity.class);
            intent.putExtra("Restaurant Id", restaurantIds.get(getAdapterPosition()));
            intent.putExtra("Restaurant Name", restaurantNames.get(getAdapterPosition()));
            context.startActivity(intent);
        }
    }
}
