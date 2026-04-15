package com.example.tp2_h071241020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private GridPostAdapter adapter;
    private HighlightAdapter highlightAdapter;
    private ArrayList<Feed> feedsToDisplay = new ArrayList<>();
    private ArrayList<Highlight> highlightsToDisplay = new ArrayList<>();
    private TextView tvCountPost;
    private boolean isMyProfile = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // Inisialisasi View
        TextView tvUser = findViewById(R.id.tvUsernameTitle);
        TextView tvNama = findViewById(R.id.tvNamaMain);
        TextView tvBio = findViewById(R.id.tvBioMain);
        tvCountPost = findViewById(R.id.tv_count_post);
        ImageView ivProf = findViewById(R.id.ivProfileMain);

        setupProfileData(tvUser, tvNama, tvBio, ivProf);

        // Setup RecyclerView Highlights (Horizontal)
        RecyclerView rvH = findViewById(R.id.rv_highlights);
        if (rvH != null) {
            rvH.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            highlightAdapter = new HighlightAdapter(highlightsToDisplay);
            rvH.setAdapter(highlightAdapter);
        }

        // Setup RecyclerView Posts (Grid 3x3)
        RecyclerView rvP = findViewById(R.id.rv_profile_posts);
        if (rvP != null) {
            rvP.setLayoutManager(new GridLayoutManager(this, 3));
            adapter = new GridPostAdapter(feedsToDisplay, isMyProfile);
            rvP.setAdapter(adapter);
        }

        setupNavigation();
    }

    private void setupProfileData(TextView tvUser, TextView tvNama, TextView tvBio, ImageView ivProf) {
        Feed target = getIntent().getParcelableExtra("TARGET_USER");
        feedsToDisplay.clear();
        highlightsToDisplay.clear();

        if (target != null) {
            // PROFIL ORANG LAIN (DUMMY)
            isMyProfile = false;
            if (tvUser != null) tvUser.setText(target.getUsername());
            if (tvNama != null) tvNama.setText(target.getFullName());
            if (tvBio != null) tvBio.setText(target.getBio());
            if (ivProf != null) ivProf.setImageResource(target.getProfileImage());

            feedsToDisplay.add(target);

            // Cari highlight musisi di DataSource
            for (Feed f : DataSource.feeds) {
                if (f.getUsername().equals(target.getUsername()) && f.getUserHighlights() != null) {
                    highlightsToDisplay.addAll(f.getUserHighlights());
                    break;
                }
            }
        } else {
            // PROFIL UTAMA (AGUNG)
            isMyProfile = true;
            if (tvUser != null) tvUser.setText("agungallokraeng");
            if (tvNama != null) tvNama.setText("Agung Allo K.");
            if (tvBio != null) tvBio.setText("Sistem Informasi 24\nAir Putih");
            // Menggunakan pp_mai sesuai perubahan nama file Anda
            if (ivProf != null) ivProf.setImageResource(R.drawable.ppmai);

            feedsToDisplay.addAll(DataSource.myProfileFeeds);
            if (DataSource.myHighlights != null) {
                highlightsToDisplay.addAll(DataSource.myHighlights);
            }
        }

        // Update jumlah postingan secara dinamis
        if (tvCountPost != null) {
            tvCountPost.setText(feedsToDisplay.size() + "\nPost");
        }
    }

    private void setupNavigation() {
        if (findViewById(R.id.btn_prof_home) != null) {
            findViewById(R.id.btn_prof_home).setOnClickListener(v -> {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }
        if (findViewById(R.id.btn_prof_post) != null) {
            findViewById(R.id.btn_prof_post).setOnClickListener(v -> startActivity(new Intent(this, PostActivity.class)));
        }
        if (findViewById(R.id.btn_prof_profile) != null) {
            findViewById(R.id.btn_prof_profile).setOnClickListener(v -> {
                if (!isMyProfile) {
                    Intent intent = new Intent(this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Jika ini profil saya, refresh data untuk mendeteksi postingan yang baru dihapus
        if (isMyProfile) {
            // Kita panggil ulang inisialisasi view untuk menyegarkan list dan jumlah post
            TextView tvUser = findViewById(R.id.tvUsernameTitle);
            TextView tvNama = findViewById(R.id.tvNamaMain);
            TextView tvBio = findViewById(R.id.tvBioMain);
            ImageView ivProf = findViewById(R.id.ivProfileMain);
            setupProfileData(tvUser, tvNama, tvBio, ivProf);
        }

        if (adapter != null) adapter.notifyDataSetChanged();
        if (highlightAdapter != null) highlightAdapter.notifyDataSetChanged();
    }
}