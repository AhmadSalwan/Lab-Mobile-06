package com.example.praktikum_1;

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

        EditText etUser = findViewById(R.id.etEditUsername);
        EditText etName = findViewById(R.id.etEditName);
        EditText etBio = findViewById(R.id.etEditBio);
        Button btnSave = findViewById(R.id.btnSimpan);

        etUser.setText(getIntent().getStringExtra("USER"));
        etName.setText(getIntent().getStringExtra("NAME"));
        etBio.setText(getIntent().getStringExtra("BIO"));

        btnSave.setOnClickListener(v -> {
            Intent res = new Intent();
            res.putExtra("USER", etUser.getText().toString());
            res.putExtra("NAME", etName.getText().toString());
            res.putExtra("BIO", etBio.getText().toString());
            setResult(Activity.RESULT_OK, res);
            finish();
        });
    }
}