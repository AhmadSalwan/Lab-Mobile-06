package com.example.tp2_h071241020;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

// Adapter 1: Feed Adapter
class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private final ArrayList<Feed> feeds;
    private final boolean showDelete;

    public interface OnFeedClickListener {
        void onProfileClick(Feed feed);
        void onDeleteClick(Feed feed, int position);
    }

    private final OnFeedClickListener listener;

    public FeedAdapter(ArrayList<Feed> feeds, boolean showDelete, OnFeedClickListener listener) {
        this.feeds = feeds;
        this.showDelete = showDelete;
        this.listener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int vt) {
        return new ViewHolder(LayoutInflater.from(p.getContext()).inflate(R.layout.item_feed, p, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        Feed f = feeds.get(pos);
        if (f == null) return;

        h.tvUser.setText(f.getUsername());
        h.tvCap.setText(f.getCaption());
        h.ivProf.setImageResource(f.getProfileImage());

        try {
            if (f.getPostImageUri() != null) h.ivPost.setImageURI(f.getPostImageUri());
            else h.ivPost.setImageResource(f.getPostImage());
        } catch (Exception e) {
            h.ivPost.setImageResource(R.drawable.ic_launcher_background);
        }

        h.btnDelete.setVisibility(showDelete ? View.VISIBLE : View.GONE);

        h.layoutUser.setOnClickListener(v -> {
            if (listener != null) listener.onProfileClick(f);
        });

        h.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(f, pos);
        });
    }

    @Override public int getItemCount() {
        return feeds != null ? feeds.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProf, ivPost;
        TextView tvUser, tvCap;
        View layoutUser;
        ImageButton btnDelete;

        public ViewHolder(View v) {
            super(v);
            ivProf = v.findViewById(R.id.iv_profile_item);
            ivPost = v.findViewById(R.id.iv_post_item);
            tvUser = v.findViewById(R.id.tv_username_item);
            tvCap = v.findViewById(R.id.tv_caption_item);
            layoutUser = v.findViewById(R.id.layout_user);
            btnDelete = v.findViewById(R.id.btn_delete_item);
        }
    }
}

class GridPostAdapter extends RecyclerView.Adapter<GridPostAdapter.ViewHolder> {
    private final ArrayList<Feed> posts;
    private final boolean isMyProfile;

    public GridPostAdapter(ArrayList<Feed> posts, boolean isMyProfile) {
        this.posts = posts;
        this.isMyProfile = isMyProfile;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feed f = posts.get(position);
        try {
            if (f.getPostImageUri() != null) holder.iv.setImageURI(f.getPostImageUri());
            else holder.iv.setImageResource(f.getPostImage());
        } catch (Exception e) {
            holder.iv.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("POS", position);
            intent.putExtra("IS_MY_PROFILE", isMyProfile);
            if (!isMyProfile) intent.putExtra("SINGLE_FEED", f);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override public int getItemCount() { return posts != null ? posts.size() : 0; }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        public ViewHolder(View v) { super(v); iv = v.findViewById(R.id.iv_grid_item); }
    }
}

class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {
    private final ArrayList<Highlight> list;
    public HighlightAdapter(ArrayList<Highlight> list) { this.list = list; }
    @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int vt) {
        return new ViewHolder(LayoutInflater.from(p.getContext()).inflate(R.layout.item_highlight, p, false));
    }
    @Override public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        Highlight hl = list.get(pos);
        h.tv.setText(hl.getTitle());
        h.iv.setImageResource(hl.getImage());
        h.itemView.setOnClickListener(v -> {
            Intent i = new Intent(h.itemView.getContext(), HighlightDetailActivity.class);
            i.putExtra("EXTRA_HIGHLIGHT", hl);
            h.itemView.getContext().startActivity(i);
        });
    }
    @Override public int getItemCount() { return list != null ? list.size() : 0; }
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv; TextView tv;
        ViewHolder(View v) { super(v); iv = v.findViewById(R.id.iv_highlight_item); tv = v.findViewById(R.id.tv_highlight_item); }
    }
}