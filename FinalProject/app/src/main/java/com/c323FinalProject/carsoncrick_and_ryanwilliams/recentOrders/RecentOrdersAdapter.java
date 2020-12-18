package com.c323FinalProject.carsoncrick_and_ryanwilliams.recentOrders;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecentOrdersAdapter extends RecyclerView.Adapter<RecentOrdersAdapter.RecentOrdersViewHolder> {

    @NonNull
    @Override
    public RecentOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentOrdersViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecentOrdersViewHolder extends RecyclerView.ViewHolder {

        public RecentOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
