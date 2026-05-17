package com.example.instagramclone.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
import com.example.instagramclone.activity.PostDetailActivity;
import com.example.instagramclone.model.Post;

import java.util.List;

public class ProfileGridAdapter extends RecyclerView.Adapter<ProfileGridAdapter.GridViewHolder> {

    private Context context;
    private List<Post> posts;

    public ProfileGridAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_post, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        Post post = posts.get(position);

        if (post.isUploaded()) {
            holder.ivPost.setImageURI(Uri.parse(post.getImageUriString()));
        } else {
            holder.ivPost.setImageResource(post.getImageResId());
        }

        holder.ivPost.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("post_id", post.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return posts.size(); }

    public static class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPost;
        
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPost = itemView.findViewById(R.id.iv_grid_post);
        }
    }
}
