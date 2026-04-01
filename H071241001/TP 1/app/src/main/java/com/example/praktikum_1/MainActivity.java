package com.example.praktikum_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvTopUsername, tvName, tvBio;
    private Button btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTopUsername = findViewById(R.id.tvTopUsername);
        tvName = findViewById(R.id.tvName);
        tvBio = findViewById(R.id.tvBio);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        tvTopUsername.setText(result.getData().getStringExtra("USER"));
                        tvName.setText(result.getData().getStringExtra("NAME"));
                        tvBio.setText(result.getData().getStringExtra("BIO"));
                    }
                }
        );

        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            intent.putExtra("USER", tvTopUsername.getText().toString());
            intent.putExtra("NAME", tvName.getText().toString());
            intent.putExtra("BIO", tvBio.getText().toString());
            launcher.launch(intent);
        });
    }
}