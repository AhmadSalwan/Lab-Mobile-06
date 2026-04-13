package com.example.tp1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SecondActivity extends AppCompatActivity {
    private ImageButton photo;
    private TextInputEditText etNama, etEmail, etBio;
    private Button btnSimpan;
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
        setContentView(R.layout.activity_second);

        photo = findViewById(R.id.imageButton2);
        etNama = findViewById(R.id.textInputEditText);
        etEmail = findViewById(R.id.editText);
        etBio = findViewById(R.id.editText2);
        btnSimpan = findViewById(R.id.btn_save);

        photo.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            openGallery.launch(Intent.createChooser(intent, "Pilih Foto"));
        });

        btnSimpan.setOnClickListener(v -> {
            String nama = etNama.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String bio = etBio.getText().toString().trim();

            if (imageUri == null) {
                Toast.makeText(this, "Silakan pilih foto profil terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nama.isEmpty()) {
                etNama.setError("Nama tidak boleh kosong");
                return;
            }

            if (email.isEmpty()) {
                etEmail.setError("Email tidak boleh kosong");
                return;
            }

            if (bio.isEmpty()) {
                etBio.setError("Bio tidak boleh kosong");
                return;
            }

            User user = new User(nama, email, bio, imageUri);

            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
            intent.putExtra("user_data", user);
            startActivity(intent);
        });
    }
}