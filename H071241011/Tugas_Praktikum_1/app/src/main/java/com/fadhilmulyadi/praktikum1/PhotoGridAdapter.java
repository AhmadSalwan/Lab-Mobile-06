package com.fadhilmulyadi.praktikum1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.fadhilmulyadi.praktikum1.databinding.ItemGridImageBinding;
import java.util.List;

public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.PhotoViewHolder> {

    private final Context context;
    private final List<Integer> photoList;

    public PhotoGridAdapter(Context context, List<Integer> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGridImageBinding binding = ItemGridImageBinding.inflate(LayoutInflater.from(context), parent, false);
        return new PhotoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Glide.with(context)
                .load(photoList.get(position))
                .centerCrop()
                .into(holder.binding.ivGridPhoto);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ItemGridImageBinding binding;

        public PhotoViewHolder(ItemGridImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}