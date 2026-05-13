package com.example.tp5_h071241076;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataSource.initDummyData();


        syncFavorites();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_add) {
                selectedFragment = new AddFragment();
            } else if (itemId == R.id.nav_favorites) {
                selectedFragment = new FavoritesFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });
    }

    // Metode untuk menyinkronkan daftar buku
    private void syncFavorites() {
        android.content.SharedPreferences pref = getSharedPreferences("user_pref", MODE_PRIVATE);
        String currentUsername = pref.getString("saved_username", "");

        if (!currentUsername.isEmpty()) {
            String favKey = "fav_" + currentUsername;
            java.util.Set<String> favIds = pref.getStringSet(favKey, new java.util.HashSet<>());

            for (Book book : DataSource.books) {
                book.setLiked(false);
            }

            for (Book book : DataSource.books) {
                if (favIds.contains(book.getId())) {
                    book.setLiked(true);
                }
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}