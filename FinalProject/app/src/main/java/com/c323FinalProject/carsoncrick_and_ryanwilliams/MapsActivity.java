package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng deliveryCoords;
    LatLng restaurantCoords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //get data from intent passed from recent orders activity
        Intent i = getIntent();
        String deliveryAddress = i.getStringExtra("delivery address");
        String restaurantAddress = i.getStringExtra("restaurant address");
        //get lat and long from addresses
        Geocoder geocoder = new Geocoder(this);
        try {
            Address addressDelivery = geocoder.getFromLocationName(deliveryAddress, 1).get(0);
            Address addressRestaurant = geocoder.getFromLocationName(restaurantAddress, 1).get(0);
            this.deliveryCoords = new LatLng(addressDelivery.getLatitude(), addressDelivery.getLongitude());
            this.restaurantCoords = new LatLng(addressRestaurant.getLatitude(), addressRestaurant.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings settings = mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setAllGesturesEnabled(true);
        settings.setCompassEnabled(true);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(deliveryCoords, 50);

        //get distance between both points
        Location deliveryLocation = new Location(LocationManager.GPS_PROVIDER);
        deliveryLocation.setLatitude(deliveryCoords.latitude);
        deliveryLocation.setLongitude(deliveryCoords.longitude);

        Location restaurantLocation = new Location(LocationManager.GPS_PROVIDER);
        restaurantLocation.setLatitude(restaurantCoords.latitude);
        restaurantLocation.setLongitude(restaurantCoords.longitude);

        float distBetween = deliveryLocation.distanceTo(restaurantLocation) / 1000;

        //make markers for both addresses
        //include distance between with delivery marker title
        MarkerOptions deliveryMarker = new MarkerOptions().position(deliveryCoords)
                .title("Your Delivery Destination, Distance In KM: " + (int) distBetween).visible(true);
        mMap.addMarker(deliveryMarker);
        CircleOptions circleOptions = new CircleOptions().center(deliveryCoords).radius(200).fillColor(0x2500ff00).strokeColor(Color.BLUE).strokeWidth(3);
        mMap.addCircle(circleOptions);

        MarkerOptions restaurantMarker = new MarkerOptions().position(restaurantCoords)
                .title("Your Chosen Restaurant").visible(true);
        mMap.addMarker(restaurantMarker);
        CircleOptions circleOptionsActivity = new CircleOptions().center(restaurantCoords).radius(200).fillColor(0x2500ff00).strokeColor(Color.BLUE).strokeWidth(3);
        mMap.addCircle(circleOptionsActivity);

        //create line between the two
        mMap.addPolyline(new PolylineOptions().add(deliveryCoords, restaurantCoords));
        //move camera to delivery address
        mMap.animateCamera(update);
    }
}