package com.c323FinalProject.carsoncrick_and_ryanwilliams.allRestaurants;

import android.content.Context;
import android.os.Bundle;

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
        this.restaurantDatabase = RestaurantDatabase.getAppDatabase(this.context);
        this.restaurantItemDao = this.restaurantDatabase.getRestaurantItemDao();
        this.restaurants = this.restaurantItemDao.getAllRestaurants();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_restaurants, container, false);

        if (this.restaurants.size() == 0) {
            this.restaurants = restaurantItemDao.getAllRestaurants();
        }

        this.recyclerView = view.findViewById(R.id.allRestaurantsRecycler);
        this.allRestaurantsAdapter = new AllRestaurantsAdapter(this.context, this.restaurants);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        this.recyclerView.setAdapter(this.allRestaurantsAdapter);
        return view;
    }
}