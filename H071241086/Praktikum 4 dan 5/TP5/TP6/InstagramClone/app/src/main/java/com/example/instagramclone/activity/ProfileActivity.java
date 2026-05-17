package com.example.instagramclone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
import com.example.instagramclone.adapter.ProfileGridAdapter;
import com.example.instagramclone.adapter.StoryAdapter;
import com.example.instagramclone.model.DataDummy;
import com.example.instagramclone.model.User;
import com.example.instagramclone.utils.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ProfileActivity extends AppCompatActivity {

    private ProfileGridAdapter gridAdapter;
    private TextView tvPostCount, tvName, tvBio, tvWebsite, tvMusic;
    private String targetUsername;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prefManager = new SharedPrefManager(this);
        String currentLoggedInUser = prefManager.getCurrentUsername();

        targetUsername = getIntent().getStringExtra("username");
        if (targetUsername == null) targetUsername = currentLoggedInUser;

        boolean isMyProfile = targetUsername.equals(currentLoggedInUser);
        User user = DataDummy.getInstance(this).getUserByUsername(targetUsername);

        TextView tvToolbarUsername = findViewById(R.id.tvToolbarUsername);
        if (tvToolbarUsername != null) tvToolbarUsername.setText(user.getUsername());
        
        ImageView btnAddFeed = findViewById(R.id.btn_nav_send);
        if (btnAddFeed != null) {
            btnAddFeed.setOnClickListener(v -> startActivity(new Intent(this, UploadPostActivity.class)));
        }

        ImageView btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        ImageView imgProfilePicture = findViewById(R.id.imgProfilePicture);
        if (imgProfilePicture != null) imgProfilePicture.setImageResource(user.getProfileImageRes());

        tvName = findViewById(R.id.tvName);
        tvBio = findViewById(R.id.tvBio);
        tvWebsite = findViewById(R.id.tvWebsite);
        tvMusic = findViewById(R.id.tvMusic);

        updateProfileUI(user);

        tvPostCount = findViewById(R.id.tvPostCount);
        if (tvPostCount != null) {
            tvPostCount.setText(String.valueOf(DataDummy.getInstance(this).getPostsForUser(targetUsername).size()));
        }
        
        TextView tvFollowerCount = findViewById(R.id.tvFollowerCount);
        if (tvFollowerCount != null) {
            tvFollowerCount.setText(formatCount(user.getFollowerCount()));
        }
        
        TextView tvFollowingCount = findViewById(R.id.tvFollowingCount);
        if (tvFollowingCount != null) {
            tvFollowingCount.setText(formatCount(user.getFollowingCount()));
        }

        RecyclerView rvStories = findViewById(R.id.rv_stories);
        if (rvStories != null) {
            if (targetUsername.equals("erchiveess")) {
                rvStories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                rvStories.setAdapter(new StoryAdapter(this, DataDummy.getInstance(this).getHighlightStories()));
                rvStories.setVisibility(View.VISIBLE);
            } else {
                rvStories.setVisibility(View.GONE);
            }
        }

        RecyclerView rvGrid = findViewById(R.id.rv_grid);
        if (rvGrid != null) {
            rvGrid.setLayoutManager(new GridLayoutManager(this, 3));
            gridAdapter = new ProfileGridAdapter(this, DataDummy.getInstance(this).getPostsForUser(targetUsername));
            rvGrid.setAdapter(gridAdapter);
        }

        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        if (btnEditProfile != null) {
            if (isMyProfile) {
                btnEditProfile.setText("Edit profil");
                btnEditProfile.setOnClickListener(v -> startActivity(new Intent(this, EditProfileActivity.class)));
            } else {
                btnEditProfile.setText("Ikuti");
                btnEditProfile.setOnClickListener(v -> btnEditProfile.setText("Mengikuti ✓"));
            }
        }

        ImageView btnBurgerMenu = findViewById(R.id.btnBurgerMenu);
        if (btnBurgerMenu != null) {
            if (isMyProfile) {
                btnBurgerMenu.setVisibility(View.VISIBLE);
                btnBurgerMenu.setOnClickListener(v -> showMenuBottomSheet());
            } else {
                btnBurgerMenu.setVisibility(View.GONE);
            }
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.btn_nav_profile_active);
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.btn_nav_home) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.btn_nav_send) {
                    startActivity(new Intent(this, UploadPostActivity.class));
                    return true;
                } else if (itemId == R.id.btn_nav_profile_active) {
                    return true;
                }
                return false;
            });
        }
    }

    private void updateProfileUI(User user) {
        if (tvName != null) tvName.setText(user.getName());
        
        if (tvBio != null) {
            if (user.getBio() == null || user.getBio().isEmpty()) {
                tvBio.setVisibility(View.GONE);
            } else {
                tvBio.setText(user.getBio());
                tvBio.setVisibility(View.VISIBLE);
            }
        }

        if (tvWebsite != null) {
            if (user.getWebsite() == null || user.getWebsite().isEmpty()) {
                ((View)tvWebsite.getParent()).setVisibility(View.GONE);
            } else {
                tvWebsite.setText(user.getWebsite());
                ((View)tvWebsite.getParent()).setVisibility(View.VISIBLE);
            }
        }

        if (tvMusic != null) {
            if (user.getMusic() == null || user.getMusic().isEmpty()) {
                ((View)tvMusic.getParent()).setVisibility(View.GONE);
            } else {
                tvMusic.setText(user.getMusic());
                ((View)tvMusic.getParent()).setVisibility(View.VISIBLE);
            }
        }
    }

    private void showMenuBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_menu, null);
        bottomSheetDialog.setContentView(view);

        SwitchMaterial switchDarkMode = view.findViewById(R.id.switchDarkMode);
        LinearLayout layoutKeluar = view.findViewById(R.id.layoutKeluar);

        if (switchDarkMode != null) {
            switchDarkMode.setChecked(prefManager.isDarkMode());
            switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
                prefManager.setDarkMode(isChecked);
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                bottomSheetDialog.dismiss();
            });
        }

        if (layoutKeluar != null) {
            layoutKeluar.setOnClickListener(v -> {
                bottomSheetDialog.dismiss();
                performLogout();
            });
        }
        bottomSheetDialog.show();
    }

    private void performLogout() {
        prefManager.logout();
        DataDummy.resetInstance();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private String formatCount(int n) {
        if (n >= 1000000) return String.format("%.1fM", n / 1000000.0);
        if (n >= 1000)    return String.format("%.1fK", n / 1000.0);
        return String.valueOf(n);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = DataDummy.getInstance(this).getUserByUsername(targetUsername);
        updateProfileUI(user);
        
        if (tvPostCount != null) {
            tvPostCount.setText(String.valueOf(DataDummy.getInstance(this).getPostsForUser(targetUsername).size()));
        }
        if (gridAdapter != null) gridAdapter.notifyDataSetChanged();
        
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.btn_nav_profile_active);
        }
    }
}
