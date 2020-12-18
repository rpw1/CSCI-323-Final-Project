package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrder;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrderDao;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.placedOrderDatabase.PlacedOrderDatabase;
import com.google.android.material.datepicker.MaterialCalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CalendarFragment extends Fragment {

    com.applandeo.materialcalendarview.CalendarView calendarView;
    Context context;
    ArrayList<String> dates;
    ArrayList<Integer> prices;
    TextView textViewSpent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
        PlacedOrderDatabase placedOrderDatabase = PlacedOrderDatabase.getPlacedOrderDatabase(this.context);
        PlacedOrderDao placedOrderDao = placedOrderDatabase.getPlacedOrderItemDao();
        List<PlacedOrder> placedOrders = placedOrderDao.getAllPlacedOrders();
        this.dates = new ArrayList<>();
        this.prices = new ArrayList<>();
        for (PlacedOrder po : placedOrders) {
            this.dates.add(po.getDate());
            this.prices.add(po.getTotal_price());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        this.calendarView = view.findViewById(R.id.calendarView);
        this.textViewSpent = view.findViewById(R.id.textViewCalendarCost);
        this.calendarView.setOnDayClickListener(eventDay -> {
            List<Calendar> calendars = new ArrayList<>();
            calendars.add(eventDay.getCalendar());
            calendarView.setHighlightedDays(calendars);
            Date date = eventDay.getCalendar().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String dateString = simpleDateFormat.format(date);
            int totalCost = 0;
            for (int i = 0; i < this.dates.size(); i++) {
                if (isSameDay(this.dates.get(i), dateString)) {
                    totalCost += this.prices.get(i);
                }
            }
            textViewSpent.setText("Total Spent: $" + totalCost);
        });
        return view;
    }

    /**
     * This function compares two days to see if they are equal.
     * @param date1
     * @param date2
     * @return
     */
    public boolean isSameDay(String date1, String date2) {
        int month1 = Integer.parseInt(date1.substring(0,2));
        int month2 = Integer.parseInt(date2.substring(0,2));

        int day1 = Integer.parseInt(date1.substring(3,5));
        int day2 = Integer.parseInt(date2.substring(3,5));

        int year1 = Integer.parseInt(date1.substring(6));
        int year2 = Integer.parseInt(date2.substring(6));

        boolean error = false;
        if (year1 != year2) {
            error = true;
        }
        if (month1 != month2) {
            error = true;
        }
        if (day1 != day2) {
            error = true;
        }
        return !error;
    }
}