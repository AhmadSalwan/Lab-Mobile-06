package com.example.tp1_h071241076;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

        Intent intent = getIntent();
        if (intent != null) {
            etName.setText(intent.getStringExtra("CURRENT_NAME"));
            etUsername.setText(intent.getStringExtra("CURRENT_USERNAME"));
            etBio.setText(intent.getStringExtra("CURRENT_BIO"));
        }

        btnSave.setOnClickListener(v -> {
            Intent resultIntent = new Intent();

            resultIntent.putExtra("EXTRA_NAME", etName.getText().toString());
            resultIntent.putExtra("EXTRA_USERNAME", etUsername.getText().toString());
            resultIntent.putExtra("EXTRA_BIO", etBio.getText().toString());

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }
}