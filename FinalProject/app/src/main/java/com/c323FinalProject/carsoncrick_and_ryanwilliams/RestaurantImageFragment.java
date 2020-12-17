package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.Restaurant;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantDatabase;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantItemDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantFoodItems.RestaurantFoodItemsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantImageFragment extends Fragment {

    private Restaurant restaurant;
    private List<String> restaurantBaseStrings;
    private List<Bitmap> restaurantBimtaps;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestaurantImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantImageFragment newInstance(String param1, String param2) {
        RestaurantImageFragment fragment = new RestaurantImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_image, container, false);

        //get restaurant and then get its image bitmaps
        RestaurantDatabase restaurantDatabase = RestaurantDatabase.getAppDatabase(getContext());
        RestaurantItemDao restaurantItemDao = restaurantDatabase.getRestaurantItemDao();
        RestaurantFoodItemsFragment.RestaurantFoodItemsInterface restaurantFoodItemsInterface = (RestaurantFoodItemsFragment.RestaurantFoodItemsInterface) getActivity();
        //get restaurant's id reference from restaurant activity's interface and use dao to get specific restaurant object
        restaurant = restaurantItemDao.getRestaurantById(restaurantFoodItemsInterface.getRestaurantId());
        //get the 3 base 64 image strings out of the restaurant object
        restaurantBaseStrings.add(restaurant.getRestaurantImageOne());
        restaurantBaseStrings.add(restaurant.getRestaurantImageTwo());
        restaurantBaseStrings.add(restaurant.getRestaurantImageThree());
        //convert base 64 into bitmaps
        for(int i = 0; i < 3; i++){
            byte[] decodedString = Base64.decode(restaurantBaseStrings.get(i), Base64.DEFAULT);
            restaurantBimtaps.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        //set up recycler view
        RecyclerView imageRecycler = view.findViewById(R.id.restaurantImageRecycler);
        //feed bitmaps to the adapter
        RestaurantImageAdapter restaurantImageAdapter = new RestaurantImageAdapter(restaurantBimtaps);
        imageRecycler.setAdapter(restaurantImageAdapter);
        imageRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // Inflate the layout for this fragment
        return view;
    }
}