package com.example.praktikum_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfile extends AppCompatActivity {

    // Deklarasi variabel
    ImageView imgFotoProfilEdit, btnBack;
    TextView btnGantiFoto, btnSimpan;
    EditText etNama, etUsername, etBio, etWebsite;

    // Untuk menyimpan URI foto yang dipilih
    Uri selectedImageUri = null;

    // Launcher untuk buka galeri
    ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Hubungkan variabel dengan ID di layout
        imgFotoProfilEdit = findViewById(R.id.imgFotoProfilEdit);
        btnBack = findViewById(R.id.btnBack);
        btnGantiFoto = findViewById(R.id.btnGantiFoto);
        btnSimpan = findViewById(R.id.btnSimpan);
        etNama = findViewById(R.id.etNama);
        etUsername = findViewById(R.id.etUsername);
        etBio = findViewById(R.id.etBio);

        // Ambil data yang dikirim dari MainActivity
        Intent intentMasuk = getIntent();
        String nama = intentMasuk.getStringExtra("nama");
        String username = intentMasuk.getStringExtra("username");
        String bio = intentMasuk.getStringExtra("bio");

        // Isi form dengan data yang ada
        if (nama != null) etNama.setText(nama);
        if (username != null) etUsername.setText(username);
        if (bio != null) etBio.setText(bio);

        // Launcher untuk buka galeri HP
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // Ambil URI foto yang dipilih
                        selectedImageUri = result.getData().getData();

                        // Tampilkan foto yang dipilih
                        imgFotoProfilEdit.setImageURI(selectedImageUri);
                    }
                }
        );

        // Klik ganti foto → buka galeri
        btnGantiFoto.setOnClickListener(v -> bukaGaleri());
        imgFotoProfilEdit.setOnClickListener(v -> bukaGaleri());

        // Klik tombol Simpan → kirim data balik ke MainActivity
        btnSimpan.setOnClickListener(v -> {
            Intent intentBalik = new Intent();

            // Kirim data yang sudah diedit
            intentBalik.putExtra("nama", etNama.getText().toString());
            intentBalik.putExtra("username", etUsername.getText().toString());
            intentBalik.putExtra("bio", etBio.getText().toString());

            // Kirim URI foto kalau ada yang dipilih
            if (selectedImageUri != null) {
                intentBalik.putExtra("fotoUri", selectedImageUri.toString());
            }

            // Kembalikan hasil ke MainActivity
            setResult(RESULT_OK, intentBalik);
            finish();
        });

        // Klik tombol Back → kembali tanpa simpan
        btnBack.setOnClickListener(v -> finish());
    }

    // Fungsi buka galeri
    private void bukaGaleri() {
        Intent intent;

        // Android 13 ke atas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        } else {
            // Android 12 ke bawah
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }

        galleryLauncher.launch(intent);
    }
}
