package com.example.tp2_h071241020;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        int pos = getIntent().getIntExtra("POS", 0);
        boolean isMyProfile = getIntent().getBooleanExtra("IS_MY_PROFILE", true);
        Feed singleFeed = getIntent().getParcelableExtra("SINGLE_FEED");

        ArrayList<Feed> dataToDisplay = new ArrayList<>();
        if (isMyProfile) {
            dataToDisplay = DataSource.myProfileFeeds;
        } else if (singleFeed != null) {
            dataToDisplay.add(singleFeed);
        }

        RecyclerView rv = findViewById(R.id.rv_detail_feeds);
        if (rv != null) {
            rv.setLayoutManager(new LinearLayoutManager(this));

            FeedAdapter adapter = new FeedAdapter(dataToDisplay, isMyProfile, new FeedAdapter.OnFeedClickListener() {
                @Override
                public void onProfileClick(Feed feed) {
                }

                @Override
                public void onDeleteClick(Feed feed, int position) {
                    showDeleteDialog(feed);
                }
            });

            rv.setAdapter(adapter);
            rv.scrollToPosition(pos);
        }
    }

    private void showDeleteDialog(Feed feed) {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Postingan")
                .setMessage("Apakah Anda yakin ingin menghapus foto ini?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    DataSource.deletePost(feed);
                    finish();
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}