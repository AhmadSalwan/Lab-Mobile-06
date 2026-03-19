package com.example.praktikum_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Deklarasi variabel
    TextView txtUsername, txtNamaLengkap, txtBio;
    ImageView imgFotoProfil;
    Button btnEditProfil;

    // Untuk menerima hasil dari EditProfileActivity
    ActivityResultLauncher<Intent> editProfileLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hubungkan variabel dengan ID di layout
        txtUsername = findViewById(R.id.txtUsername);
        txtNamaLengkap = findViewById(R.id.txtNamaLengkap);
        txtBio = findViewById(R.id.txtBio);
        imgFotoProfil = findViewById(R.id.imgFotoProfil);

        btnEditProfil = findViewById(R.id.btnEditProfil);

        // Launcher untuk menerima data balik dari EditProfileActivity
        editProfileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();

                        // Ambil data yang dikirim balik dari EditProfileActivity
                        String nama = data.getStringExtra("nama");
                        String username = data.getStringExtra("username");
                        String bio = data.getStringExtra("bio");
                        String fotoUri = data.getStringExtra("fotoUri");

                        // Update tampilan profil
                        if (nama != null && !nama.isEmpty()) {
                            txtNamaLengkap.setText(nama);
                        }
                        if (username != null && !username.isEmpty()) {
                            txtUsername.setText(username);
                        }
                        if (bio != null && !bio.isEmpty()) {
                            txtBio.setText(bio);
                        }
                        if (fotoUri != null && !fotoUri.isEmpty()) {
                            imgFotoProfil.setImageURI(Uri.parse(fotoUri));
                        }
                    }
                }
        );

        // Klik tombol Edit Profil → pindah ke EditProfileActivity
        btnEditProfil.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfile.class);

            // Kirim data profil saat ini ke EditProfileActivity
            intent.putExtra("nama", txtNamaLengkap.getText().toString());
            intent.putExtra("username", txtUsername.getText().toString());
            intent.putExtra("bio", txtBio.getText().toString());

            editProfileLauncher.launch(intent);
        });
    }
}