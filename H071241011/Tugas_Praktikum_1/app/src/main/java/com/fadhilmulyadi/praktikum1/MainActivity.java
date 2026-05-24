package com.fadhilmulyadi.praktikum1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import com.fadhilmulyadi.praktikum1.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private UserProfile currentUser;

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    UserProfile updatedUser = (UserProfile) result.getData().getSerializableExtra("UPDATED_USER");
                    if (updatedUser != null) {
                        currentUser = updatedUser;
                        updateUI();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currentUser = new UserProfile(
                "Muhammad Fadhil Mulyadi",
                "fadhilmulydi",
                "woke up hella grateful",
                "",
                0,
                100,
                200
        );

        updateUI();
        setupPhotoGrid();

        binding.btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("CURRENT_USER", currentUser);
            editProfileLauncher.launch(intent);
        });
    }

    private void setupPhotoGrid() {
        List<Integer> myPhotos = new ArrayList<>();

        myPhotos.add(R.drawable.marsha1);
        myPhotos.add(R.drawable.marsha2);
        myPhotos.add(R.drawable.marsha3);
        myPhotos.add(R.drawable.marsha4);
        myPhotos.add(R.drawable.marsha5);
        myPhotos.add(R.drawable.marsha6);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        binding.rvPhotoGrid.setLayoutManager(gridLayoutManager);

        PhotoGridAdapter adapter = new PhotoGridAdapter(this, myPhotos);
        binding.rvPhotoGrid.setAdapter(adapter);
    }

    private void updateUI() {
        binding.tvUsernameTop.setText(currentUser.getUsername());
        binding.tvUsername.setText(currentUser.getUsername());
        binding.tvFullName.setText(currentUser.getName());
        binding.tvBio.setText(currentUser.getBio());
        binding.tvPostsCount.setText(String.valueOf(currentUser.getPostsCount()));
        binding.tvFollowersCount.setText(String.valueOf(currentUser.getFollowersCount()));
        binding.tvFollowingCount.setText(String.valueOf(currentUser.getFollowingCount()));
    }
}