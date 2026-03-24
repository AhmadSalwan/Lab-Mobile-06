package com.example.tp1_h071241020;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvUsername, tvNama, tvBio;
    private ImageView ivProfile;
    private Uri currentPhotoUri;

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    UserData updatedData = result.getData().getParcelableExtra("KEY_USER_DATA");
                    if (updatedData != null) {
                        tvUsername.setText(updatedData.getUsername());
                        tvNama.setText(updatedData.getNama());
                        tvBio.setText(updatedData.getBio());
                        if (updatedData.getPhotoUri() != null) {
                            currentPhotoUri = updatedData.getPhotoUri();
                            ivProfile.setImageURI(currentPhotoUri);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        tvUsername = findViewById(R.id.tvUsernameTitle);
        tvNama = findViewById(R.id.tvNamaMain);
        tvBio = findViewById(R.id.tvBioMain);
        ivProfile = findViewById(R.id.ivProfileMain);
        Button btnEdit = findViewById(R.id.btnEditProfile);

        ivProfile.setOnClickListener(v -> {
            if (currentPhotoUri != null) {
            }
        });

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            UserData currentData = new UserData(
                    tvNama.getText().toString(),
                    tvUsername.getText().toString(),
                    tvBio.getText().toString(),
                    currentPhotoUri
            );
            intent.putExtra("INIT_DATA", currentData);
            editProfileLauncher.launch(intent);
        });
    }
}