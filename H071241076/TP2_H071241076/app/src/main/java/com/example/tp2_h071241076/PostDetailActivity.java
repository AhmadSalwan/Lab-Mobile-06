package com.example.tp2_h071241076;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PostDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ImageView ivProfile = findViewById(R.id.iv_profile_detail);
        TextView tvUsername = findViewById(R.id.tv_username_detail);
        ImageView ivPost = findViewById(R.id.iv_post_detail);
        TextView tvCaptionUser = findViewById(R.id.tv_caption_username_detail);
        TextView tvCaption = findViewById(R.id.tv_caption_detail);

        Post post = getIntent().getParcelableExtra("EXTRA_POST");

        if (post != null) {
            ivProfile.setImageResource(post.getProfileImage());
            tvUsername.setText(post.getUsername());
            tvCaptionUser.setText(post.getUsername());
            tvCaption.setText(post.getCaption());

            if (post.getImageUri() != null) {
                ivPost.setImageURI(Uri.parse(post.getImageUri()));
            } else {
                ivPost.setImageResource(post.getPostImage());
            }
        }
    }
}