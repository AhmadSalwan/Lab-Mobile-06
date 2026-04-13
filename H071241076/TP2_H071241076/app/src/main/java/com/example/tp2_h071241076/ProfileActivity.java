package com.example.tp2_h071241076;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private RecyclerView rvStory, rvGridPost;
    private ArrayList<Story> listStory = new ArrayList<>();
    private ArrayList<Post> listGridPost = new ArrayList<>();
    private GridPostAdapter gridAdapter;
    private Post currentPost;

    private final ActivityResultLauncher<Intent> addPostLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    addNewPostToGrid(
                            result.getData().getStringExtra("EXTRA_IMAGE_URI"),
                            result.getData().getStringExtra("EXTRA_CAPTION")
                    );
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currentPost = getIntent().getParcelableExtra("EXTRA_POST");

        ImageView ivProfile = findViewById(R.id.iv_profile_page);
        TextView tvName = findViewById(R.id.tv_name_profile);
        TextView tvPosts = findViewById(R.id.tv_posts_count);

        if (currentPost != null) {
            ivProfile.setImageResource(currentPost.getProfileImage());
            tvName.setText(currentPost.getUsername());
        }

        listStory.addAll(DataDummy.getAllStories());
        listGridPost.addAll(DataDummy.getAllPosts());

        Post newPostFromHome = getIntent().getParcelableExtra("NEW_POST_DATA");
        if (newPostFromHome != null) {
            listGridPost.add(0, newPostFromHome);
        }

        tvPosts.setText(String.valueOf(listGridPost.size()));

        rvStory = findViewById(R.id.rv_story);
        showStoryList();

        rvGridPost = findViewById(R.id.rv_grid_post);
        showGridPostList();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_add) {
                Intent intent = new Intent(ProfileActivity.this, AddPostActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                addPostLauncher.launch(intent);
                return true;
            } else if (id == R.id.nav_profile) {
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_profile);
    }

    private void addNewPostToGrid(String uri, String caption) {
        Post newPost = new Post(currentPost.getUsername(), caption, currentPost.getProfileImage(), uri);
        listGridPost.add(0, newPost);
        gridAdapter.notifyItemInserted(0);
        rvGridPost.scrollToPosition(0);
        TextView tvPosts = findViewById(R.id.tv_posts_count);
        tvPosts.setText(String.valueOf(listGridPost.size()));
    }

    private void showStoryList() {
        rvStory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        StoryAdapter storyAdapter = new StoryAdapter(listStory);
        rvStory.setAdapter(storyAdapter);

        storyAdapter.setOnItemClickCallback(story -> {
            Intent intent = new Intent(ProfileActivity.this, StoryDetailActivity.class);
            intent.putExtra("EXTRA_STORY", story);
            startActivity(intent);
        });
    }

    private void showGridPostList() {
        rvGridPost.setLayoutManager(new GridLayoutManager(this, 3));
        gridAdapter = new GridPostAdapter(listGridPost);
        rvGridPost.setAdapter(gridAdapter);
        gridAdapter.setOnItemClickCallback(clickedPost -> {
            Intent intent = new Intent(ProfileActivity.this, PostDetailActivity.class);
            intent.putExtra("EXTRA_POST", clickedPost);
            startActivity(intent);
        });
    }
}