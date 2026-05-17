package com.example.instagramclone.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.instagramclone.R;
import com.example.instagramclone.model.User;
import com.example.instagramclone.utils.SharedPrefManager;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etNama, etPassword;
    private Button btnRegister;
    private TextView tvLogin;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        prefManager = new SharedPrefManager(this);
        
        // 1. Terapkan Tema Gelap/Terang Global
        if (prefManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etNama = findViewById(R.id.etNama);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String name = etNama.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || name.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            if (prefManager.getUser(username) != null) {
                Toast.makeText(this, "Username sudah digunakan", Toast.LENGTH_SHORT).show();
                return;
            }

            // 2. PASTIKAN AKUN BARU KOSONG TOTAL
            // Menggunakan ic_default_avatar dan Bio string kosong ""
            User newUser = new User(username, name, R.drawable.ic_default_avatar, "", 0, 0, 0);
            newUser.setWebsite("");
            newUser.setMusic("");
            
            prefManager.saveUser(newUser);
            Toast.makeText(this, "Registrasi Berhasil! Silakan Login", Toast.LENGTH_SHORT).show();
            finish();
        });

        tvLogin.setOnClickListener(v -> finish());
    }
}
