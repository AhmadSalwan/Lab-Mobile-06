package com.example.tp2_h071241020;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        RecyclerView rv = findViewById(R.id.rv_home);
        if (rv != null) {
            rv.setLayoutManager(new LinearLayoutManager(this));

            FeedAdapter adapter = new FeedAdapter(DataSource.feeds, false, new FeedAdapter.OnFeedClickListener() {
                @Override
                public void onProfileClick(Feed feed) {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    intent.putExtra("TARGET_USER", feed);
                    startActivity(intent);
                }

                @Override
                public void onDeleteClick(Feed feed, int position) {
                }
            });
            rv.setAdapter(adapter);
        }

        if (findViewById(R.id.btn_to_post) != null) {
            findViewById(R.id.btn_to_post).setOnClickListener(v -> startActivity(new Intent(this, PostActivity.class)));
        }
        if (findViewById(R.id.btn_to_profile) != null) {
            findViewById(R.id.btn_to_profile).setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        }
    }
}