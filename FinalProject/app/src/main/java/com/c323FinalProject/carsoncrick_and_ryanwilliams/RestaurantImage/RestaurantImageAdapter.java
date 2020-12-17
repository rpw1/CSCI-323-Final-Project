package com.c323FinalProject.carsoncrick_and_ryanwilliams.RestaurantImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.R;

import java.util.List;

public class RestaurantImageAdapter extends RecyclerView.Adapter<RestaurantImageAdapter.RestaurantImageViewHolder> {

    List<String> base64ImageStrings;
    Context context;

    public RestaurantImageAdapter (Context context, List<String> base64ImageStrings) {
        this.context = context;
        this.base64ImageStrings = base64ImageStrings;
    }

    @NonNull
    @Override
    public RestaurantImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.restaurant_image_item, parent, false);
        return new RestaurantImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantImageViewHolder holder, int position) {
        Bitmap bitmap = getImageBitmap(this.base64ImageStrings.get(position));
        holder.imageView.setImageBitmap(bitmap);
    }

    /**
     * I got this here: https://stackoverflow.com/questions/4837110/how-to-convert-a-base64-string-into-a-bitmap-image-to-show-it-in-a-imageview
     * This function converts a Base64 string into a Bitmap
     * @param b64ImageString
     * @return
     */
    private Bitmap getImageBitmap(String b64ImageString) {
        byte[] decodedString = Base64.decode(b64ImageString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    @Override
    public int getItemCount() {
        return this.base64ImageStrings.size();
    }

    public class RestaurantImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public RestaurantImageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.recyclerViewRestaurantImage);
        }
    }
}
