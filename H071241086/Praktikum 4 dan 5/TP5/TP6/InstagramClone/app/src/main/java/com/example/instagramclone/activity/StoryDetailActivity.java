package com.example.instagramclone.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.instagramclone.R;
import com.example.instagramclone.utils.SharedPrefManager;

public class StoryDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPrefManager prefManager = new SharedPrefManager(this);
        if (prefManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_story_detail);

        String title = getIntent().getStringExtra("story_title");
        int imageRes = getIntent().getIntExtra("story_image", 0);

        TextView tvTitle = findViewById(R.id.tv_story_title);
        ImageView ivStory = findViewById(R.id.iv_story);
        
        if (tvTitle != null) tvTitle.setText(title);
        if (ivStory != null && imageRes != 0) ivStory.setImageResource(imageRes);

        findViewById(R.id.btn_close).setOnClickListener(v -> finish());
    }
}
