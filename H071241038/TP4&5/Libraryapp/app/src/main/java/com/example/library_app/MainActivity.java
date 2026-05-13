package com.example.library_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.library_app.data.DummyData;
import com.example.library_app.fragment.AddBookFragment;
import com.example.library_app.fragment.FavoriteFragment;
import com.example.library_app.fragment.HomeFragment;
import com.example.library_app.utils.BookRepository;
import com.example.library_app.utils.PrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefManager prefManager = new PrefManager(this);
        if (prefManager.isDarkMode()) {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CEK LOGIN
        if (!prefManager.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Muat data dari SharedPreferences
        BookRepository.loadBooks(this);
        // Jika masih kosong, baru generate dummy data
        DummyData.generateBooks();
        // Simpan jika dummy data baru saja di-generate
        BookRepository.saveBooks(this);

        bottomNavigationView = findViewById(R.id.bottomNav);

        // Fragment awal
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // Bottom Navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_favorite) {
                selectedFragment = new FavoriteFragment();
            } else if (itemId == R.id.nav_add) {
                selectedFragment = new AddBookFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}