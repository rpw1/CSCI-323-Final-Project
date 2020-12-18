package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.recentOrders.RecentOrdersFragment;

public class NavDrawerSelectionActivity extends AppCompatActivity {
    //This activity inflates whatever fragment (Recent Orders or Calendar) was chosen in the Nav Drawer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_selection);

        //get selection from Intent, default is recent orders
        int selection = getIntent().getIntExtra("selection", R.id.navDrawerRecentOrders);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //put selected fragment in the activity frame layout
        if(selection == R.id.navDrawerRecentOrders){
            fragmentTransaction.add(R.id.navDrawerSelectionFragFrame, new RecentOrdersFragment());
        }
        else if(selection == R.id.navDrawerCalendarView){
            fragmentTransaction.add(R.id.navDrawerSelectionFragFrame, new CalendarFragment());
        }
        fragmentTransaction.commit();
    }
}