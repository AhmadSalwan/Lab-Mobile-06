package com.example.tp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    private ImageButton btnEdit;
    private ImageView ivPhoto;
    private TextView tvNama, tvEmail, tvBio;
    private User currentUser;

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        User updatedUser = result.getData().getParcelableExtra("updated_user");
                        if (updatedUser != null) {
                            currentUser = updatedUser;
                            displayUserData(currentUser);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        btnEdit = findViewById(R.id.btn_edit);
        ivPhoto = findViewById(R.id.iv_profile);
        tvNama = findViewById(R.id.tv_nama);
        tvEmail = findViewById(R.id.tv_email);
        tvBio = findViewById(R.id.tv_nomor); // Tetap pakai id tv_nomor agar tidak perlu ubah XML

        // Ambil data awal dari Intent
        currentUser = getIntent().getParcelableExtra("user_data");
        if (currentUser != null) {
            displayUserData(currentUser);
        }

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
            intent.putExtra("user_data", currentUser);
            editProfileLauncher.launch(intent);
        });
    }

    private void displayUserData(User user) {
        tvNama.setText(user.getNama());
        tvEmail.setText(user.getEmail());
        tvBio.setText(user.getBio());
        if (user.getImageUri() != null) {
            ivPhoto.setImageURI(user.getImageUri());
        }
    }
}