package com.fadhilmulyadi.praktikum1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.fadhilmulyadi.praktikum1.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private UserProfile receivedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().hasExtra("CURRENT_USER")) {
            receivedUser = (UserProfile) getIntent().getSerializableExtra("CURRENT_USER");
            if (receivedUser != null) {
                binding.etEditName.setText(receivedUser.getName());
                binding.etEditUsername.setText(receivedUser.getUsername());
                binding.etEditBio.setText(receivedUser.getBio());
            }
        }

        binding.btnCancel.setOnClickListener(v -> finish());

        binding.btnSave.setOnClickListener(v -> {
            if (receivedUser != null) {
                receivedUser.setName(binding.etEditName.getText().toString().trim());
                receivedUser.setUsername(binding.etEditUsername.getText().toString().trim());
                receivedUser.setBio(binding.etEditBio.getText().toString().trim());

                Intent resultIntent = new Intent();
                resultIntent.putExtra("UPDATED_USER", receivedUser);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}