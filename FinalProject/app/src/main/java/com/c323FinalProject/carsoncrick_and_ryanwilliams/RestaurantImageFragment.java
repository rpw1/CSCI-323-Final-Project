package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.Restaurant;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantDatabase;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantItemDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantFoodItems.RestaurantFoodItemsFragment;

import java.util.ArrayList;
import java.util.List;

public class RestaurantImageFragment extends Fragment {

    private Restaurant restaurant;
    private List<String> restaurantBaseStrings;
    private List<Bitmap> restaurantBitmaps;
    RestaurantImageInterface restaurantImageInterface;
    RecyclerView imageRecycler;

    public interface RestaurantImageInterface {
        int getRestaurantIdImage();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
         this.restaurantImageInterface = (RestaurantImageInterface) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.restaurantBaseStrings = new ArrayList<>();
        this.restaurantBitmaps = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_image, container, false);
        this.imageRecycler = view.findViewById(R.id.restaurantImageRecycler);
        //get restaurant and then get its image bitmaps
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RestaurantDatabase restaurantDatabase = RestaurantDatabase.getAppDatabase(getContext());
        RestaurantItemDao restaurantItemDao = restaurantDatabase.getRestaurantItemDao();
        //get restaurant's id reference from restaurant activity's interface and use dao to get specific restaurant object
        int position = restaurantImageInterface.getRestaurantIdImage();
        restaurant = restaurantItemDao.getRestaurantById(position);
        List<Restaurant> restaurants = restaurantItemDao.getAllRestaurants();
        //get the 3 base 64 image strings out of the restaurant object
        restaurantBaseStrings.add(restaurant.getRestaurantImageOne());
        restaurantBaseStrings.add(restaurant.getRestaurantImageTwo());
        restaurantBaseStrings.add(restaurant.getRestaurantImageThree());
        //convert base 64 into bitmaps
        for(int i = 0; i < 3; i++){
            byte[] decodedString = Base64.decode(restaurantBaseStrings.get(i), Base64.DEFAULT);
            restaurantBitmaps.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        //set up recycler view
        //feed bitmaps to the adapter
        RestaurantImageAdapter restaurantImageAdapter = new RestaurantImageAdapter(restaurantBitmaps);
        imageRecycler.setAdapter(restaurantImageAdapter);
        imageRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // Inflate the layout for this fragment
    }
}