package com.example.tp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvName, tvUsername, tvBio, tvTopUsername;
    private Button btnEditProfile, btnShareProfile, btnAddPerson;
    private LinearLayout llPostingan, llPengikut, llMengikuti, hlBaru, hlSpesial, hlNgoding, hlRun;
    private ImageView ivMenuAdd, ivMenuBurger, ivProfilePic;

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String newName = data.getStringExtra("EXTRA_NAME");
                        String newUsername = data.getStringExtra("EXTRA_USERNAME");
                        String newBio = data.getStringExtra("EXTRA_BIO");

                        if (newName != null) tvName.setText(newName);
                        if (newUsername != null) {
                            tvUsername.setText(newUsername);
                            // Mengupdate juga username di pojok kiri atas
                            tvTopUsername.setText("🔒 " + newUsername.replace("\\@", ""));
                        }
                        if (newBio != null) tvBio.setText(newBio);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi Data Teks
        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvUsername);
        tvTopUsername = findViewById(R.id.tvTopUsername);
        tvBio = findViewById(R.id.tvBio);

        // Inisialisasi Tombol Aksi
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnShareProfile = findViewById(R.id.btnShareProfile);
        btnAddPerson = findViewById(R.id.btnAddPerson);

        // Inisialisasi Interaksi Lainnya
        llPostingan = findViewById(R.id.llPostingan);
        llPengikut = findViewById(R.id.llPengikut);
        llMengikuti = findViewById(R.id.llMengikuti);
        hlBaru = findViewById(R.id.hlBaru);
        hlSpesial = findViewById(R.id.hlSpesial);
        hlNgoding = findViewById(R.id.hlNgoding);
        hlRun = findViewById(R.id.hlRun);
        ivMenuAdd = findViewById(R.id.ivMenuAdd);
        ivMenuBurger = findViewById(R.id.ivMenuBurger);
        ivProfilePic = findViewById(R.id.ivProfilePic);

        // --- AKSI UTAMA (INTENT) ---
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("EXTRA_NAME", tvName.getText().toString());
            intent.putExtra("EXTRA_USERNAME", tvUsername.getText().toString());
            intent.putExtra("EXTRA_BIO", tvBio.getText().toString());
            editProfileLauncher.launch(intent);
        });

        // --- AKSI TAMBAHAN (TOAST) UNTUK SEMUA FITUR ---
        btnShareProfile.setOnClickListener(v -> showToast("Fitur Bagikan Profil diklik"));
        btnAddPerson.setOnClickListener(v -> showToast("Fitur Tambah Orang diklik"));

        llPostingan.setOnClickListener(v -> showToast("Melihat daftar Postingan"));
        llPengikut.setOnClickListener(v -> showToast("Melihat daftar Pengikut"));
        llMengikuti.setOnClickListener(v -> showToast("Melihat daftar Mengikuti"));

        hlBaru.setOnClickListener(v -> showToast("Membuat Sorotan Baru"));
        hlSpesial.setOnClickListener(v -> showToast("Membuka Sorotan: Spesial"));
        hlNgoding.setOnClickListener(v -> showToast("Membuka Sorotan: ngoding"));
        hlRun.setOnClickListener(v -> showToast("Membuka Sorotan: Run"));

        ivMenuAdd.setOnClickListener(v -> showToast("Menu Tambah Post/Reels diklik"));
        ivMenuBurger.setOnClickListener(v -> showToast("Pengaturan diklik"));
        ivProfilePic.setOnClickListener(v -> showToast("Melihat Foto Profil / Story"));
    }

    // Fungsi kecil untuk mempermudah pemanggilan Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}