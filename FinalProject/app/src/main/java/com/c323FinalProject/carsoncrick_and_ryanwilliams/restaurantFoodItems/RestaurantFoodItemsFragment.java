package com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantFoodItems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.checkout.CheckoutActivity;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.R;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.OrderItem;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantDatabase;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantItemDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantOrderItemMap;

import java.util.ArrayList;
import java.util.List;


public class RestaurantFoodItemsFragment extends Fragment {

    RecyclerView recyclerView;
    RestaurantFoodItemsAdapter itemsAdapter;
    Button button;
    Context context;
    RestaurantFoodItemsInterface itemsInterface;
    List<OrderItem> orderItems = new ArrayList<>();
    RestaurantDatabase restaurantDatabase;
    RestaurantItemDao restaurantItemDao;

    public interface RestaurantFoodItemsInterface {
        int getRestaurantId();
        String getRestaurantName();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.itemsInterface = (RestaurantFoodItemsInterface) getActivity();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_food_items, container, false);
        this.recyclerView = view.findViewById(R.id.restaurantFoodItemRecycler);
        this.restaurantDatabase = RestaurantDatabase.getAppDatabase(this.context);
        this.button = view.findViewById(R.id.btnCheckOut);
        this.button.setOnClickListener(btnView -> {
            for (int i = 0; i < this.itemsAdapter.getItemCount(); i++) {
                this.restaurantItemDao.updateOrderItems(this.itemsAdapter.orderItems.get(i));
            }
            Intent intent = new Intent(context, CheckoutActivity.class);
            intent.putExtra("id", this.itemsInterface.getRestaurantId());
            intent.putExtra("name", this.itemsInterface.getRestaurantName());
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.restaurantItemDao = this.restaurantDatabase.getRestaurantItemDao();
        List<RestaurantOrderItemMap> restaurantOrderItemMaps = this.restaurantItemDao.getOrderItemFromMap(this.itemsInterface.getRestaurantId());
        for (RestaurantOrderItemMap orderItemMap : restaurantOrderItemMaps) {
            this.orderItems.add(this.restaurantItemDao.getOrderItemById(orderItemMap.orderItemId));
        }
        this.itemsAdapter = new RestaurantFoodItemsAdapter(this.context, this.orderItems);
        this.recyclerView.setAdapter(this.itemsAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
    }
}