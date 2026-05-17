package com.example.instagramclone.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
import com.example.instagramclone.activity.PostDetailActivity;
import com.example.instagramclone.activity.ProfileActivity;
import com.example.instagramclone.model.Post;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private Context context;
    private List<Post> posts;

    public FeedAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.tvUsername.setText(post.getUsername());
        holder.tvCaption.setText(post.getUsername() + "  " + post.getCaption());
        holder.tvLikeCount.setText(post.getLikeCount() + " suka");
        holder.tvTime.setText(post.getTimeAgo());

        holder.ivProfile.setImageResource(post.getUserProfileImage());

        if (post.isUploaded()) {
            holder.ivPost.setImageURI(Uri.parse(post.getImageUriString()));
        } else {
            holder.ivPost.setImageResource(post.getImageResId());
        }

        holder.ivLike.setImageResource(post.isLiked() ? R.drawable.ic_heart_filled : R.drawable.ic_heart);
        holder.ivBookmark.setImageResource(post.isBookmarked() ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark);

        View.OnClickListener profileClick = v -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("username", post.getUsername());
            context.startActivity(intent);
        };
        holder.ivProfile.setOnClickListener(profileClick);
        holder.tvUsername.setOnClickListener(profileClick);

        holder.ivPost.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("post_id", post.getId());
            context.startActivity(intent);
        });

        holder.ivLike.setOnClickListener(v -> {
            boolean liked = !post.isLiked();
            post.setLiked(liked);
            post.setLikeCount(liked ? post.getLikeCount() + 1 : post.getLikeCount() - 1);
            holder.ivLike.setImageResource(liked ? R.drawable.ic_heart_filled : R.drawable.ic_heart);
            holder.tvLikeCount.setText(post.getLikeCount() + " suka");
        });

        holder.ivBookmark.setOnClickListener(v -> {
            boolean bm = !post.isBookmarked();
            post.setBookmarked(bm);
            holder.ivBookmark.setImageResource(bm ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark);
        });

        holder.ivComment.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("post_id", post.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return posts.size(); }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivProfile;
        TextView tvUsername, tvCaption, tvLikeCount, tvTime;
        ImageView ivPost, ivLike, ivComment, ivShare, ivBookmark;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile     = itemView.findViewById(R.id.iv_profile);
            tvUsername    = itemView.findViewById(R.id.tv_username);
            tvCaption     = itemView.findViewById(R.id.tv_caption);
            tvLikeCount   = itemView.findViewById(R.id.tv_like_count);
            tvTime        = itemView.findViewById(R.id.tv_time);
            ivPost        = itemView.findViewById(R.id.iv_post);
            ivLike        = itemView.findViewById(R.id.iv_like);
            ivComment     = itemView.findViewById(R.id.iv_comment);
            ivShare       = itemView.findViewById(R.id.iv_share);
            ivBookmark    = itemView.findViewById(R.id.iv_bookmark);
        }
    }
}
