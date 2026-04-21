package com.example.tp2_h071241076;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class GridPostAdapter extends RecyclerView.Adapter<GridPostAdapter.GridViewHolder> {

    private ArrayList<Post> listPost;

    public interface OnItemClickCallback {
        void onGridClicked(Post post);
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public GridPostAdapter(ArrayList<Post> list) {
        this.listPost = list;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_post, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        Post post = listPost.get(position);

        if (post.getImageUri() != null) {
            holder.ivGridPost.setImageURI(Uri.parse(post.getImageUri()));
        } else {
            holder.ivGridPost.setImageResource(post.getPostImage());
        }

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickCallback != null) {
                onItemClickCallback.onGridClicked(listPost.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGridPost;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGridPost = itemView.findViewById(R.id.iv_grid_post);
        }
    }
}