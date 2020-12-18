package com.c323FinalProject.carsoncrick_and_ryanwilliams.recentRestaurants;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.R;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrder;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrderDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrderDatabase;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.recentRestaurants.RecentRestaurantsAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecentRestaurantsFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    List<PlacedOrder> placedOrders;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
        PlacedOrderDatabase placedOrderDatabase = PlacedOrderDatabase.getPlacedOrderDatabase(context);
        PlacedOrderDao placedOrderDao = placedOrderDatabase.getPlacedOrderItemDao();
        this.placedOrders = placedOrderDao.getAllPlacedOrders();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_restaurants, container, false);
        this.recyclerView = view.findViewById(R.id.recentOrdersRecycler);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<String> restaurantNames = new ArrayList<>();
        List<Integer> restaurantIds = new ArrayList<>();
        for (PlacedOrder placedOrder : this.placedOrders) {
            String restaurantName = placedOrder.getRestaurant_name();
            if (!restaurantNames.contains(restaurantName)) {
                restaurantNames.add(restaurantName);
                restaurantIds.add(placedOrder.getRestaurantId());
            }
        }
        RecentRestaurantsAdapter recentRestaurantsAdapter = new RecentRestaurantsAdapter(this.context, restaurantNames, restaurantIds);
        this.recyclerView.setAdapter(recentRestaurantsAdapter);
    }
}