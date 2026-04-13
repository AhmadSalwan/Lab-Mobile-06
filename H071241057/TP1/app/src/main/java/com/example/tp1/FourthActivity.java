package com.example.tp1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class FourthActivity extends AppCompatActivity {
    private ImageButton photo;
    private TextInputEditText etNama, etEmail, etBio;
    private Button btnUpdate;
    private Uri imageUri;

    private final ActivityResultLauncher<Intent> openGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        photo.setImageURI(imageUri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        photo = findViewById(R.id.iv_edit_photo);
        etNama = findViewById(R.id.et_edit_nama);
        etEmail = findViewById(R.id.et_edit_email);
        etBio = findViewById(R.id.et_edit_nomor);
        btnUpdate = findViewById(R.id.btn_update);

        // Ambil data awal dari Activity 3
        User user = getIntent().getParcelableExtra("user_data");
        if (user != null) {
            etNama.setText(user.getNama());
            etEmail.setText(user.getEmail());
            etBio.setText(user.getBio());
            imageUri = user.getImageUri();
            if (imageUri != null) {
                photo.setImageURI(imageUri);
            }
        }

        photo.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            openGallery.launch(Intent.createChooser(intent, "Pilih Foto Baru"));
        });

        btnUpdate.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String email = etEmail.getText().toString();
            String bio = etBio.getText().toString();

            User updatedUser = new User(nama, email, bio, imageUri);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("updated_user", updatedUser);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}