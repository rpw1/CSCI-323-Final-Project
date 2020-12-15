package com.c323FinalProject.carsoncrick_and_ryanwilliams.checkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.R;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.OrderItem;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {

    Context context;
    List<OrderItem> orderItems;

    public CheckoutAdapter(Context context, List<OrderItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.food_item_layout, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        OrderItem orderItem = this.orderItems.get(position);
        int quantity = orderItem.getOrderItemQuantity();
        holder.textViewName.setText(orderItem.getOrderItemName());
        holder.textViewPrice.setText("$" + orderItem.getOrderItemPrice());
        holder.textViewQuantity.setText(quantity + "");
        holder.quantity = quantity;
    }

    @Override
    public int getItemCount() {
        return this.orderItems.size();
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int quantity = 0;
        TextView textViewName, textViewPrice, textViewQuantity;
        ImageButton buttonIncrease, buttonDecrease;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.tvFoodItemName);
            this.textViewPrice = itemView.findViewById(R.id.tvFoodItemPrice);
            this.textViewQuantity = itemView.findViewById(R.id.tvFoodItemQuantity);
            this.buttonIncrease = itemView.findViewById(R.id.btnIncreaseQuantity);
            this.buttonDecrease = itemView.findViewById(R.id.btnDecreaseQuantity);
            this.buttonIncrease.setOnClickListener(this);
            this.buttonDecrease.setOnClickListener(this);
        }
        // food item's + and - button handler
        @Override
        public void onClick(View view) {
            if (view.getId() == this.buttonIncrease.getId()) {
                this.quantity++;
                this.textViewQuantity.setText(this.quantity + "");
            } else if (view.getId() == this.buttonDecrease.getId()) {
                this.quantity--;
                this.textViewQuantity.setText(this.quantity + "");
            }
            orderItems.get(getAdapterPosition()).setOrderItemQuantity(quantity);
        }
    }
}
