package com.example.tp1_h071241076;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvUsername, tvName, tvBio;

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();

                    if (data.getStringExtra("EXTRA_USERNAME") != null) {
                        tvUsername.setText(data.getStringExtra("EXTRA_USERNAME"));
                    }
                    if (data.getStringExtra("EXTRA_NAME") != null) {
                        tvName.setText(data.getStringExtra("EXTRA_NAME"));
                    }
                    if (data.getStringExtra("EXTRA_BIO") != null) {
                        tvBio.setText(data.getStringExtra("EXTRA_BIO"));
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsername = findViewById(R.id.tvUsername);
        tvName = findViewById(R.id.tvName);
        tvBio = findViewById(R.id.tvBio);
        Button btnEditProfile = findViewById(R.id.btnEditProfile);

        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);

            intent.putExtra("CURRENT_USERNAME", tvUsername.getText().toString());
            intent.putExtra("CURRENT_NAME", tvName.getText().toString());
            intent.putExtra("CURRENT_BIO", tvBio.getText().toString());

            editProfileLauncher.launch(intent);
        });
    }
}