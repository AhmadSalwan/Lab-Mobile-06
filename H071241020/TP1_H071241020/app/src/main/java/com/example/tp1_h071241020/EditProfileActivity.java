package com.example.tp1_h071241020;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    private EditText etNama, etUsername, etBio;
    private ImageView ivEdit;
    private TextView tvChangePhoto;
    private Uri selectedPhotoUri;

    // Launcher untuk membuka galeri
    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedPhotoUri = result.getData().getData();
                    if (ivEdit != null) {
                        ivEdit.setImageURI(selectedPhotoUri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        etNama = findViewById(R.id.etEditNama);
        etUsername = findViewById(R.id.etEditUsername);
        etBio = findViewById(R.id.etEditBio);
        ivEdit = findViewById(R.id.ivEditPhoto);
        tvChangePhoto = findViewById(R.id.tvChangePhoto);
        Button btnSave = findViewById(R.id.btnSaveProfile);

        try {
            UserData initData = getIntent().getParcelableExtra("INIT_DATA");
            if (initData != null) {
                if (etNama != null) etNama.setText(initData.getNama());
                if (etUsername != null) etUsername.setText(initData.getUsername());
                if (etBio != null) etBio.setText(initData.getBio());
                if (initData.getPhotoUri() != null && ivEdit != null) {
                    selectedPhotoUri = initData.getPhotoUri();
                    ivEdit.setImageURI(selectedPhotoUri);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        View.OnClickListener pickPhotoListener = v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                galleryLauncher.launch(Intent.createChooser(intent, "Pilih Foto Baru"));
            } catch (Exception e) {
                Toast.makeText(this, "Tidak dapat membuka galeri", Toast.LENGTH_SHORT).show();
            }
        };

        if (ivEdit != null) ivEdit.setOnClickListener(pickPhotoListener);
        if (tvChangePhoto != null) tvChangePhoto.setOnClickListener(pickPhotoListener);

        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                String nama = (etNama != null) ? etNama.getText().toString() : "";
                String user = (etUsername != null) ? etUsername.getText().toString() : "";
                String bio = (etBio != null) ? etBio.getText().toString() : "";

                UserData data = new UserData(nama, user, bio, selectedPhotoUri);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("KEY_USER_DATA", data);
                setResult(RESULT_OK, resultIntent);
                finish();
            });
        }
    }
}