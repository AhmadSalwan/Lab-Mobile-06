package com.example.tp2_h071241076;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        ImageView ivStory = findViewById(R.id.iv_story_detail);
        TextView tvTitle = findViewById(R.id.tv_story_title_detail);

        // Menangkap data Story
        Story story = getIntent().getParcelableExtra("EXTRA_STORY");

        if (story != null) {
            ivStory.setImageResource(story.getImage());
            tvTitle.setText(story.getTitle());
        }
    }
}