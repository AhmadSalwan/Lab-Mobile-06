package com.example.instagramclone.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
import com.example.instagramclone.activity.StoryDetailActivity;
import com.example.instagramclone.model.Story;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    private Context context;
    private List<Story> stories;

    public StoryAdapter(Context context, List<Story> stories) {
        this.context = context;
        this.stories = stories;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_story, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        Story story = stories.get(position);
        
        holder.tvStoryTitle.setText(story.getTitle());
        holder.ivStory.setImageResource(story.getCoverResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StoryDetailActivity.class);
            intent.putExtra("story_id", story.getId());
            intent.putExtra("story_title", story.getTitle());
            intent.putExtra("story_res", story.getCoverResId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return stories.size(); }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivStory;
        TextView tvStoryTitle;
        
        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStory       = itemView.findViewById(R.id.iv_story);
            tvStoryTitle  = itemView.findViewById(R.id.tv_story_title);
        }
    }
}
