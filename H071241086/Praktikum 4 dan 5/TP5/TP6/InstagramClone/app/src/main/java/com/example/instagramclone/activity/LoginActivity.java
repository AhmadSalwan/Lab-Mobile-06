package com.example.instagramclone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.instagramclone.R;
import com.example.instagramclone.model.DataDummy;
import com.example.instagramclone.model.User;
import com.example.instagramclone.utils.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        prefManager = new SharedPrefManager(this);
        
        // Terapkan tema sebelum setContentView
        if (prefManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_login);

        if (prefManager.getCurrentUsername() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Harap isi semua bidang", Toast.LENGTH_SHORT).show();
                return;
            }

            if (username.equals("erchiveess") && prefManager.getUser("erchiveess") == null) {
                User defaultUser = new User("erchiveess", "morejoyrideplease she",
                        R.drawable.profile1, "yg dekat\" aj\n@keonho.cortis @woojinl.ngshot 's noona",
                        5, 76, 221);
                prefManager.saveUser(defaultUser);
            }

            User user = prefManager.getUser(username);
            if (user != null) {
                prefManager.setCurrentUser(username);
                // PENTING: Reset data dummy agar data akun baru dimuat segar
                DataDummy.resetInstance(); 

                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "User tidak ditemukan. Silakan daftar.", Toast.LENGTH_SHORT).show();
            }
        });

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
