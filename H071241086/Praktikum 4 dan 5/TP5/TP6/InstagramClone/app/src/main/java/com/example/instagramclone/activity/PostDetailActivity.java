package com.example.instagramclone.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.bumptech.glide.Glide;
import com.example.instagramclone.R;
import com.example.instagramclone.model.DataDummy;
import com.example.instagramclone.model.Post;
import com.example.instagramclone.utils.SharedPrefManager;

public class PostDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPrefManager prefManager = new SharedPrefManager(this);
        if (prefManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_post_detail);

        String postId = getIntent().getStringExtra("post_id");
        Post post = DataDummy.getInstance(this).findPostById(postId);

        if (post != null) {
            ImageView ivProfile = findViewById(R.id.iv_profile);
            TextView tvUsername = findViewById(R.id.tv_username);
            ImageView ivPost = findViewById(R.id.iv_post);
            TextView tvCaption = findViewById(R.id.tv_caption);
            
            ivProfile.setImageResource(post.getUserProfileImage());
            tvUsername.setText(post.getUsername());
            
            if (post.isUploaded()) {
                Glide.with(this).load(post.getImageUriString()).into(ivPost);
            } else {
                ivPost.setImageResource(post.getImageResId());
            }

            tvCaption.setText(post.getCaption());
        }

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}
