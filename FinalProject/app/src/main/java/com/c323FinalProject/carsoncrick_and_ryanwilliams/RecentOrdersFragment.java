package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrderDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrderDatabase;

public class RecentOrdersFragment extends Fragment {

    RecyclerView recyclerView;
    PlacedOrderDao placedOrderDao;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
        PlacedOrderDatabase placedOrderDatabase = PlacedOrderDatabase.getPlacedOrderDatabase(context);
        this.placedOrderDao = placedOrderDatabase.getPlacedOrderItemDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_orders, container, false);
        this.recyclerView = view.findViewById(R.id.recentOrdersFragRecycler);
        return view;
    }
}