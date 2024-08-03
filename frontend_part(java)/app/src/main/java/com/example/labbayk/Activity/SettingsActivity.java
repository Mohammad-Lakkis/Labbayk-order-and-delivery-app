package com.example.labbayk.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.labbayk.DataSource;
import com.example.labbayk.R;
import com.example.labbayk.User;
import com.example.labbayk.databinding.ActivitySettingsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    private FirebaseAuth mAuth;
    User currentUser;
    final int PERMISSION_REQUEST_CAMERA = 103;
    ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK) {
                                Intent data = result.getData();
                                Bitmap photo = (Bitmap) data.getExtras().get("data");
                                float density = SettingsActivity.this.getResources().getDisplayMetrics().density;
                                int dp = 140;
                                int pixels = (int) ((dp * density) + 0.5);
                                Bitmap scaledPhoto = Bitmap.createScaledBitmap(
                                        photo, pixels, pixels, true);
                                binding.profilePhotoImageView.setImageBitmap(scaledPhoto);
                                currentUser.setUserPhoto(scaledPhoto);
                            }
                        }
                    });

    private final ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        binding.profilePhotoImageView.setImageBitmap(bitmap);
                        currentUser.setUserPhoto(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUser();
        initTextChangedEvents();
        initCameraButton();
        setVariables();
    }

    private String initEmail(){

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        return user.getEmail();
    }

    private void initUser() {
        binding.userEmail.setText(initEmail());
        DataSource ds = new DataSource(this);
        ds.open();
        currentUser = ds.getUserFromEmail(binding.userEmail.getText().toString());
        ds.close();
        if (currentUser == null) {
            currentUser = new User();
        } else {
            try {
                binding.usernameEditText.setText(currentUser.getUserName());

                if (currentUser.getUserPhoto() != null) {
                    binding.profilePhotoImageView.setImageBitmap(currentUser.getUserPhoto());
                } else {
                    binding.profilePhotoImageView.setImageResource(R.drawable.ic_default_profile_photo);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error Loading Data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryActivityResultLauncher.launch(intent);
    }

    private void initSaveButton() {
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wasSuccessful = false;
                DataSource dataSource = new DataSource(SettingsActivity.this);
                try {
                    dataSource.open();
                    if (currentUser.getUserID() == -1) {
                        wasSuccessful = dataSource.insertUser(currentUser);
                        if (wasSuccessful) {
                            int newId = dataSource.getLastUser();
                            currentUser.setUserID(newId);
                        }
                    } else {
                        dataSource.updateUser(currentUser);
                    }
                    Toast.makeText(SettingsActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SettingsActivity.this,MainActivity.class));
                } catch (Exception ignored) {
                }
            }
        });
    }

    private void initCameraButton() {
        binding.changePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SettingsActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            SettingsActivity.this, Manifest.permission.CAMERA)) {
                        Snackbar.make(binding.getRoot(),
                                        "The app needs permission to take photo",
                                        Snackbar.LENGTH_INDEFINITE)
                                .setAction("Ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("MainActivity Camera permission", "");
                                        ActivityCompat.requestPermissions(SettingsActivity.this,
                                                new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                                    }
                                }).show();
                    } else {
                        Log.d("MainActivity Camera permission", "");
                        ActivityCompat.requestPermissions(SettingsActivity.this,
                                new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    }
                }
            }
        });
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncher.launch(intent);
    }

    private void setVariables(){
        binding.backBtn.setOnClickListener(view -> finish());
        initSaveButton();
        binding.profilePhotoImageView.setOnClickListener(v -> openGallery());
    }

    private void initTextChangedEvents() {
        binding.usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("Before", binding.usernameEditText.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("on", binding.usernameEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("After", binding.usernameEditText.getText().toString());
                currentUser.setUserName(binding.usernameEditText.getText().toString());
            }
        });
        currentUser.setUserEmail(binding.userEmail.getText().toString());

    }
}