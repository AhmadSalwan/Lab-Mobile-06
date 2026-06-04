package com.example.tp3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferenceManager = new PreferenceManager(this);
        etUsername = findViewById(R.id.et_reg_username);
        etPassword = findViewById(R.id.et_reg_password);
        Button btnRegister = findViewById(R.id.btn_register);
        TextView tvBackToLogin = findViewById(R.id.tv_back_to_login);

        btnRegister.setOnClickListener( v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                preferenceManager.saveUser(username, password);
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        tvBackToLogin.setOnClickListener(v -> finish());
    }
}
