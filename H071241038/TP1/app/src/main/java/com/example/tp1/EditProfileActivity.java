package com.example.tp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText etName = findViewById(R.id.etName);
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etBio = findViewById(R.id.etBio);
        Button btnSave = findViewById(R.id.btnSave);

        // Elemen tambahan untuk aksi klik
        TextView tvChangePhoto = findViewById(R.id.tvChangePhoto);
        ImageView ivEditProfilePic = findViewById(R.id.ivEditProfilePic);

        // Menerima data bawaan
        Intent incomingIntent = getIntent();
        if (incomingIntent != null) {
            etName.setText(incomingIntent.getStringExtra("EXTRA_NAME"));
            etUsername.setText(incomingIntent.getStringExtra("EXTRA_USERNAME"));
            etBio.setText(incomingIntent.getStringExtra("EXTRA_BIO"));
        }

        // Aksi Simpan dan Kembali (Intent Result)
        btnSave.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("EXTRA_NAME", etName.getText().toString());

            // Memastikan username selalu memiliki '@' di depannya
            String updatedUsername = etUsername.getText().toString();
            if (!updatedUsername.startsWith("\\@") && !updatedUsername.startsWith("@")) {
                updatedUsername = "\\@" + updatedUsername;
            }
            resultIntent.putExtra("EXTRA_USERNAME", updatedUsername);

            resultIntent.putExtra("EXTRA_BIO", etBio.getText().toString());

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });

        // Aksi klik untuk mengubah foto profil (Simulasi)
        tvChangePhoto.setOnClickListener(v ->
                Toast.makeText(this, "Membuka Galeri untuk pilih foto...", Toast.LENGTH_SHORT).show()
        );
        ivEditProfilePic.setOnClickListener(v ->
                Toast.makeText(this, "Membuka Galeri untuk pilih foto...", Toast.LENGTH_SHORT).show()
        );
    }
}