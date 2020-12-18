package com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantImage;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.R;

import java.util.List;

public class RestaurantImageAdapter extends RecyclerView.Adapter<RestaurantImageAdapter.RestaurantImageViewHolder> {


    private List<Bitmap> restaurantImageBitmaps;


    public RestaurantImageAdapter(List<Bitmap> bitmaps){
        this.restaurantImageBitmaps = bitmaps;
    }

    @NonNull
    @Override
    public RestaurantImageAdapter.RestaurantImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_image_card_layout, parent, false);
        return new RestaurantImageAdapter.RestaurantImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantImageAdapter.RestaurantImageViewHolder holder, int position) {
        holder.restaurantImageView.setImageBitmap(restaurantImageBitmaps.get(position));
    }

    @Override
    public int getItemCount() {
        return restaurantImageBitmaps.size();
    }

    public class RestaurantImageViewHolder extends RecyclerView.ViewHolder{
        ImageView restaurantImageView;

        public RestaurantImageViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImageView = itemView.findViewById(R.id.restaurantImageView);
        }
    }
}
