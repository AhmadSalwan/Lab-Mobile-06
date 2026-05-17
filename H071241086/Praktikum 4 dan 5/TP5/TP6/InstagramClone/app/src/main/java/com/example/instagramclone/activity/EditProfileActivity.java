package com.example.instagramclone.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.instagramclone.R;
import com.example.instagramclone.model.DataDummy;
import com.example.instagramclone.model.User;
import com.example.instagramclone.utils.SharedPrefManager;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName, etUsername, etPronoun, etBio, etWebsite, etMusic, etGender;
    private User currentUser;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        prefManager = new SharedPrefManager(this);
        if (prefManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_edit_profile);

        currentUser = DataDummy.getInstance(this).getCurrentUser();

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etBio = findViewById(R.id.etBio);
        ImageView imgProfile = findViewById(R.id.imgProfileEdit);
        ImageView btnBack = findViewById(R.id.btnBack);
        Button btnSave = findViewById(R.id.btnSave);

        etName.setText(currentUser.getName());
        etUsername.setText(currentUser.getUsername());
        etBio.setText(currentUser.getBio());
        imgProfile.setImageResource(currentUser.getProfileImageRes());

        btnBack.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String newName = etName.getText().toString().trim();
        String newUsername = etUsername.getText().toString().trim();
        
        if (newName.isEmpty() || newUsername.isEmpty()) {
            Toast.makeText(this, "Nama dan Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        currentUser.setName(newName);
        currentUser.setUsername(newUsername);
        currentUser.setBio(etBio.getText().toString().trim());

        // Update data di SharedPreferences agar tidak hilang saat restart
        prefManager.saveUser(currentUser);

        Toast.makeText(this, "Profil diperbarui", Toast.LENGTH_SHORT).show();
        finish();
    }
}
