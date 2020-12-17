package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Base64;

import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.Restaurant;
import com.c323FinalProject.carsoncrick_and_ryanwilliams.restaurantDatabse.RestaurantDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText editTextName, editTextEmail;
    Intent intent;
    SharedPreferences.Editor editor;
    ImageView userImageView;
    AlertDialog alertDialog;
    LocationManager lm;
    LocationListener locationListener;
    double longitude;
    double latitude;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        this.editTextName = findViewById(R.id.editTextUsername);
        this.editTextEmail = findViewById(R.id.editTextUserEmail);
        this.userImageView = findViewById(R.id.userImageButton);

        createNotificationChannel();
        getUserLocation();
        try {
            RestaurantDatabase database = RestaurantDatabase.getAppDatabase(this, latitude, longitude);
        } catch (IOException e) {
            Log.v("stuff", "Something went wrong while creating db, potentially with calling the apis in getRestaurantLocations");
        }

        this.intent = new Intent(this, HomeActivity.class);
        HashMap<String, String> loginInfo = (HashMap<String, String>) sharedPreferences.getAll();
        this.editor = this.sharedPreferences.edit();
        this.sharedPreferences.edit().clear().commit();
        if (loginInfo.containsKey("name") && loginInfo.containsKey("email")) {
            startActivity(this.intent);
        }

    }

    /***
     * This function grabs the user's name and email and saves them in SharedPreferences.
     * Then, this function sends the user to the HomeActivity
     * @param view
     */
    public void login(View view) {
        String name = this.editTextName.getText().toString();
        String email = this.editTextEmail.getText().toString();
        boolean isComplete = true;
        if (name.equals("")) {
            isComplete = false;
            Toast.makeText(this, "Error: Please enter your name", Toast.LENGTH_SHORT).show();
        }
        if (email.equals("")) {
            isComplete = false;
            Toast.makeText(this, "Error: Please enter your email", Toast.LENGTH_SHORT).show();
        } else if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(this, "Error: Please enter email in correct format of xxxx@xxxx.xxx", Toast.LENGTH_SHORT).show();
            isComplete = false;
        }

        if (isComplete) {
            this.editor.putString("name", name);
            this.editor.putString("email", email);
            this.editor.commit();
            startActivity(this.intent);
        }
    }

    /**
     * This method opens a AlertDialogue which allows the user choose a photo from their camera or image gallery.
     * Then the function sets the image to the userImageView and saves the image in SharedPreferences
     * @param view
     */
    public void createUserImage(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogueView = getLayoutInflater().inflate(R.layout.choose_image_alert_dialogue, null);
        Button buttonGallery = dialogueView.findViewById(R.id.buttonDialogGallery);
        Button buttonCamera = dialogueView.findViewById(R.id.buttonDialogCamera);

        buttonGallery.setOnClickListener(viewGallery -> {
            // I found this here https://stackoverflow.com/questions/16928727/open-gallery-app-from-android-intent?noredirect=1&lq=1
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, 18);
        });

        buttonCamera.setOnClickListener(viewCamera -> {
            if (hasCamera()) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 17);
            } else {
                viewCamera.setEnabled(false);
            }
        });

        builder.setView(dialogueView);
        this.alertDialog = builder.create();
        this.alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Talk to Carson about this
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 17:
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitmapCamera = (Bitmap) bundle.get("data");
                        this.userImageView.setImageBitmap(bitmapCamera);
                        addImageToSharedPref(bitmapCamera);
                        this.intent.putExtra("image", this.userImageView.toString());
                    }
                    this.alertDialog.cancel();
                    break;
                case 18:
                    Uri imageUri = data.getData();
                    Bitmap bitmapGallery = null;
                    try {
                        // I found this here https://stackoverflow.com/questions/3879992/how-to-get-bitmap-from-an-uri
                        bitmapGallery = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (bitmapGallery != null) {
                        this.userImageView.setImageBitmap(bitmapGallery);
                        addImageToSharedPref(bitmapGallery);
                    }
                    this.alertDialog.cancel();
                    break;
            }
        }
    }

    /**
     * This function coverts a bitmap into a string and stores it in SharedPreferences.
     * I got this here https://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
     * @param bitmap
     */
    private void addImageToSharedPref(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        this.editor.putString("image", encodedString);
        this.editor.commit();
    }

    /**
     * This function checks if the user's current device has a camera.
     * @return
     */
    private boolean hasCamera() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
            return true;
        return false;
    }

    /**
     * Creates notification channel so our program is able to send out notifications
     */
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("serviceNotification", "program channel", importance);
            channel.setDescription("");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getUserLocation() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onProviderDisabled(@NonNull String provider) {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            }

            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 1);
            return;
        } else {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000000, 0, locationListener);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000000, 0, locationListener);
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}