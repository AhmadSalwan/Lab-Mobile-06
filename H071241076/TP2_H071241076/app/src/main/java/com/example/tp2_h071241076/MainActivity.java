package com.example.tp2_h071241076;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvPosts;
    private ArrayList<Post> listPost = new ArrayList<>();

    private final ActivityResultLauncher<Intent> addPostLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String imageUri = result.getData().getStringExtra("EXTRA_IMAGE_URI");
                    String caption = result.getData().getStringExtra("EXTRA_CAPTION");

                    Post dummy = DataDummy.getAllPosts().get(0);
                    Post newPost = new Post(dummy.getUsername(), caption, dummy.getProfileImage(), imageUri);

                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("EXTRA_POST", dummy);
                    intent.putExtra("NEW_POST_DATA", newPost);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPosts = findViewById(R.id.rv_posts);
        rvPosts.setHasFixedSize(true);
        listPost.addAll(DataDummy.getAllPosts());
        showRecyclerList();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_add) {
                Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                addPostLauncher.launch(intent);
                return true;
            } else if (id == R.id.nav_profile) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("EXTRA_POST", DataDummy.getAllPosts().get(0));
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
    }

    private void showRecyclerList() {
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        PostAdapter postAdapter = new PostAdapter(listPost);
        rvPosts.setAdapter(postAdapter);
        postAdapter.setOnItemClickCallback(data -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("EXTRA_POST", data);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
    }
}