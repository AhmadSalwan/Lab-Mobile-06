package com.fadhil.tp5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferencesManager prefsManager;

    private Button tabLogin, tabRegister, btnLogin, btnRegister;
    private LinearLayout formLogin, formRegister;
    private EditText inputNimLogin, inputPasswordLogin;
    private EditText inputNameReg, inputNimReg, inputPasswordReg;
    private RadioGroup rgSkinColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefsManager = new SharedPreferencesManager(this);

        if (prefsManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (prefsManager.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        tabLogin     = findViewById(R.id.tabLogin);
        tabRegister  = findViewById(R.id.tabRegister);
        formLogin    = findViewById(R.id.formLogin);
        formRegister = findViewById(R.id.formRegister);

        inputNimLogin = findViewById(R.id.inputNimLogin);
        inputPasswordLogin = findViewById(R.id.inputPasswordLogin);
        btnLogin      = findViewById(R.id.btnLogin);

        inputNameReg  = findViewById(R.id.inputNameReg);
        inputNimReg   = findViewById(R.id.inputNimReg);
        inputPasswordReg = findViewById(R.id.inputPasswordReg);
        rgSkinColor   = findViewById(R.id.rgSkinColor);
        btnRegister   = findViewById(R.id.btnRegister);

        tabLogin.setOnClickListener(v -> {
            formLogin.setVisibility(View.VISIBLE);
            formRegister.setVisibility(View.GONE);
            tabLogin.setBackgroundResource(R.drawable.bg_button_primary);
            tabLogin.setTextColor(getResources().getColor(R.color.white, getTheme()));
            tabRegister.setBackgroundResource(android.R.color.transparent);
            tabRegister.setTextColor(getResources().getColor(R.color.colorPrimary, getTheme()));
        });

        tabRegister.setOnClickListener(v -> {
            formLogin.setVisibility(View.GONE);
            formRegister.setVisibility(View.VISIBLE);
            tabRegister.setBackgroundResource(R.drawable.bg_button_primary);
            tabRegister.setTextColor(getResources().getColor(R.color.white, getTheme()));
            tabLogin.setBackgroundResource(android.R.color.transparent);
            tabLogin.setTextColor(getResources().getColor(R.color.colorPrimary, getTheme()));
        });

        btnLogin.setOnClickListener(v -> {
            String nim = inputNimLogin.getText().toString().trim();
            String password = inputPasswordLogin.getText().toString().trim();
            if (nim.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Masukkan NIM dan Password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (prefsManager.login(nim, password)) {
                if (prefsManager.isDarkMode()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "NIM atau Password salah, atau belum terdaftar", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(v -> {
            String name = inputNameReg.getText().toString().trim();
            String nim  = inputNimReg.getText().toString().trim();
            String password = inputPasswordReg.getText().toString().trim();

            if (name.isEmpty() || nim.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            if (prefsManager.isNimRegistered(nim)) {
                Toast.makeText(this, "NIM sudah terdaftar", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = rgSkinColor.getCheckedRadioButtonId();
            String skinColor = (selectedId == R.id.rbPutih) ? "putih" : "hitam";

            prefsManager.saveUser(name, nim, password, skinColor);

            if ("hitam".equals(skinColor)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            Toast.makeText(this, "Registrasi berhasil, silakan login", Toast.LENGTH_SHORT).show();
            tabLogin.performClick();
        });
    }
}
