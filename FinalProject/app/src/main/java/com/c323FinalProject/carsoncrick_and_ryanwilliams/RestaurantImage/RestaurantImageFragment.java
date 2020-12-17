package com.c323FinalProject.carsoncrick_and_ryanwilliams.RestaurantImage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.R;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.Restaurant;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantDatabase;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantItemDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantFoodItems.RestaurantFoodItemsFragment;

import java.util.ArrayList;
import java.util.List;

public class RestaurantImageFragment extends Fragment {

    RecyclerView recyclerView;
    RestaurantDatabase restaurantDatabase;
    RestaurantItemDao restaurantItemDao;
    Context context;
    RestaurantImageAdapter restaurantImageAdapter;
    RestaurantFoodItemsFragment.RestaurantFoodItemsInterface itemsInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.restaurantDatabase = RestaurantDatabase.getExistingDatabaseInstance();
        this.restaurantItemDao = restaurantDatabase.getRestaurantItemDao();
        this.context = getContext();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.itemsInterface = (RestaurantFoodItemsFragment.RestaurantFoodItemsInterface) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_image, container, false);
        this.recyclerView = view.findViewById(R.id.recyclerViewRestaurantImage);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        List<Restaurant> restaurants = this.restaurantItemDao.getAllRestaurants();
        List<String> b64Strings = new ArrayList<>();
        this.restaurantImageAdapter = new RestaurantImageAdapter(this.context, )
        return view;
    }
}