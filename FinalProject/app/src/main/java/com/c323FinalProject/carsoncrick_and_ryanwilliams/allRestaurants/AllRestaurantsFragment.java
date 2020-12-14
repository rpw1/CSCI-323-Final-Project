package com.c323FinalProject.carsoncrick_and_ryanwilliams.allRestaurants;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.R;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.Restaurant;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantDatabase;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantItemDao;

import java.util.ArrayList;
import java.util.List;

public class AllRestaurantsFragment extends Fragment {

    RestaurantItemDao restaurantItemDao;
    RestaurantDatabase restaurantDatabase;
    Context context;
    RecyclerView recyclerView;
    AllRestaurantsAdapter allRestaurantsAdapter;
    List<Restaurant> restaurants = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
//        new Thread(() -> context.deleteDatabase("restaurantDb")).start();
        this.restaurantDatabase = RestaurantDatabase.getAppDatabase(this.context);
        this.restaurantItemDao = this.restaurantDatabase.getRestaurantItemDao();
        new Thread(() -> this.restaurants = restaurantItemDao.getAllRestaurants()).start();
        Log.v("REST_INFO", "Is this bitch empty? " + this.restaurants.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_restaurants, container, false);
        this.recyclerView = view.findViewById(R.id.allRestaurantsRecycler);
        this.allRestaurantsAdapter = new AllRestaurantsAdapter(this.context, this.restaurants);
        this.recyclerView.setAdapter(this.allRestaurantsAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        return view;
    }
}