package com.example.tp2_h071241076;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> listPost;

    public interface OnItemClickCallback {
        void onProfileClicked(Post post);
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public PostAdapter(ArrayList<Post> list) {
        this.listPost = list;
    }

    // Menyambungkan desain XML (item_post.xml)
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = listPost.get(position);

        holder.ivProfile.setImageResource(post.getProfileImage());
        holder.tvUsername.setText(post.getUsername());
        holder.tvCaptionUsername.setText(post.getUsername());
        holder.tvCaption.setText(post.getCaption());
        holder.ivPost.setImageResource(post.getPostImage());

        View.OnClickListener profileClickListener = v -> {
            if (onItemClickCallback != null) {
                onItemClickCallback.onProfileClicked(listPost.get(holder.getAdapterPosition()));
            }
        };

        holder.ivProfile.setOnClickListener(profileClickListener);
        holder.tvUsername.setOnClickListener(profileClickListener);
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile, ivPost;
        TextView tvUsername, tvCaptionUsername, tvCaption;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.iv_profile_home);
            tvUsername = itemView.findViewById(R.id.tv_username_home);
            ivPost = itemView.findViewById(R.id.iv_post_home);
            tvCaptionUsername = itemView.findViewById(R.id.tv_caption_username);
            tvCaption = itemView.findViewById(R.id.tv_caption_home);
        }
    }
}