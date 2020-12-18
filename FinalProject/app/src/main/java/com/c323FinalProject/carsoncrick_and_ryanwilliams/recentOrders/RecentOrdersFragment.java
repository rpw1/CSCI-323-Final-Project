package com.c323FinalProject.carsoncrick_and_ryanwilliams.recentOrders;

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

import java.util.List;

public class RecentOrdersFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_recent_orders, container, false);
        this.recyclerView = view.findViewById(R.id.recentOrdersFragRecycler);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RecentOrdersAdapter recentOrdersAdapter = new RecentOrdersAdapter(this.context, this.placedOrders);
        this.recyclerView.setAdapter(recentOrdersAdapter);
    }
}