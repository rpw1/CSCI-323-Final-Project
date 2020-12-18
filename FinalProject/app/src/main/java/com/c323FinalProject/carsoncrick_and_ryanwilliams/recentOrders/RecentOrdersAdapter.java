package com.c323FinalProject.carsoncrick_and_ryanwilliams.recentOrders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.R;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrder;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrderDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrderDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecentOrdersAdapter extends RecyclerView.Adapter<RecentOrdersAdapter.RecentOrdersViewHolder> {

    List<PlacedOrder> placedOrders;
    Context context;

    public RecentOrdersAdapter(Context context, List<PlacedOrder> placedOrders) {
        this.context = context;
        this.placedOrders = placedOrders;
    }

    @NonNull
    @Override
    public RecentOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.recent_order_item_layout, parent, false);
        return new RecentOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentOrdersViewHolder holder, int position) {
        PlacedOrder placedOrder = this.placedOrders.get(position);
        holder.textViewFoodItems.setText("Food Items:\n" + placedOrder.getOrdered_food_items());
        holder.textViewOrderedFrom.setText("Ordered From: " + placedOrder.getRestaurant_name());
        holder.textViewPrice.setText("Total Price: " + placedOrder.getTotal_price());
        holder.textViewDate.setText("Date: " + placedOrder.getDate());
        holder.textViewTime.setText("Time: " + placedOrder.getTime());
        holder.textViewAddress.setText("Address: " + placedOrder.getAddress());
    }

    @Override
    public int getItemCount() {
        return this.placedOrders.size();
    }

    public class RecentOrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewFoodItems, textViewOrderedFrom, textViewPrice, textViewDate, textViewTime, textViewAddress;
        Button placeOrderAgain;

        public RecentOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewFoodItems = itemView.findViewById(R.id.tvRecentOrderFragAllFoodItems);
            this.textViewOrderedFrom= itemView.findViewById(R.id.tvRecentOrderFragOrderedFrom);
            this.textViewPrice = itemView.findViewById(R.id.tvRecentOrderFragPrice);
            this.textViewDate = itemView.findViewById(R.id.tvRecentOrderFragDate);
            this.textViewTime = itemView.findViewById(R.id.tvRecentOrderFragTime);
            this.textViewAddress = itemView.findViewById(R.id.tvRecentOrderFragAddress);
            this.placeOrderAgain = itemView.findViewById(R.id.buttonReplaceOrder);
            this.placeOrderAgain.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            PlacedOrderDatabase placedOrderDatabase = PlacedOrderDatabase.getPlacedOrderDatabase(context);
            PlacedOrderDao placedOrderDao = placedOrderDatabase.getPlacedOrderItemDao();
            PlacedOrder placedOrder = placedOrders.get(getAdapterPosition());
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String dateString = simpleDateFormat.format(date);
            SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm a");
            String timeString = simpleDateFormatTime.format(date);
            PlacedOrder newPlacedOrder = new PlacedOrder(placedOrder.getTotal_price(), placedOrder.getTotal_quantity(),
                    dateString, timeString, placedOrder.getRestaurant_name(), placedOrder.getAddress(),
                    placedOrder.getRestaurantId(), placedOrder.getOrdered_food_items());
            placedOrderDao.insertPlacedOrder(newPlacedOrder);
            Toast.makeText(context, "Order number " + placedOrder.getPlaced_order_id() + " was successfully reordered", Toast.LENGTH_SHORT).show();
        }
    }
}
