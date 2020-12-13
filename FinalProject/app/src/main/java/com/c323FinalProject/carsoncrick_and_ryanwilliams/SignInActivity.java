package com.c323FinalProject.carsoncrick_and_ryanwilliams;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Base64;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        this.editTextName = findViewById(R.id.editTextUsername);
        this.editTextEmail = findViewById(R.id.editTextUserEmail);
        this.userImageView = findViewById(R.id.userImageButton);

        this.intent = new Intent(this, HomeActivity.class);
        HashMap<String, String> loginInfo = (HashMap<String, String>) sharedPreferences.getAll();
        this.editor = this.sharedPreferences.edit();
        // this.sharedPreferences.edit().clear().commit();
        if (loginInfo.containsKey("name") && loginInfo.containsKey("email")) {
            startActivity(this.intent);
        }
    }

    //create user with credentials or check credentials
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
    //method to create dialog for selecting user image
    public void createUserImage(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogueView = getLayoutInflater().inflate(R.layout.choose_image_alert_dialogue, null);
        Button buttonGallery = dialogueView.findViewById(R.id.buttonDialogGallery);
        Button buttonCamera = dialogueView.findViewById(R.id.buttonDialogCamera);

        buttonGallery.setOnClickListener(viewGallery -> {
            // I found this here https://stackoverflow.com/questions/16928727/open-gallery-app-from-android-intent?noredirect=1&lq=1
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

    // I got this here https://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
    private void addImageToSharedPref(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        this.editor.putString("image", encodedString);
        this.editor.commit();
    }


    private boolean hasCamera() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
            return true;
        return false;
    }
}