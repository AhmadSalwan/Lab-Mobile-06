package com.example.tp2_h071241076;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    private ArrayList<Story> listStory;

    public interface OnItemClickCallback {
        void onStoryClicked(Story story);
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public StoryAdapter(ArrayList<Story> list) {
        this.listStory = list;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        Story story = listStory.get(position);

        holder.ivStory.setImageResource(story.getImage());
        holder.tvStoryTitle.setText(story.getTitle());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickCallback != null) {
                onItemClickCallback.onStoryClicked(listStory.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listStory.size();
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivStory;
        TextView tvStoryTitle;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStory = itemView.findViewById(R.id.iv_story);
            tvStoryTitle = itemView.findViewById(R.id.tv_story_title);
        }
    }
}