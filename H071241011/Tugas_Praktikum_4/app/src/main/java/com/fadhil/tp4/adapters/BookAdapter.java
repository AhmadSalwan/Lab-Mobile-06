package com.fadhil.tp4.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fadhil.tp4.R;
import com.fadhil.tp4.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    
    private List<Book> bookList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public BookAdapter(Context context, List<Book> bookList, OnItemClickListener listener) {
        this.context = context;
        this.bookList = bookList;
        this.listener = listener;
    }

    public void updateData(List<Book> newBooks) {
        this.bookList = new ArrayList<>(newBooks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvYear.setText(String.valueOf(book.getYear()));

        if (book.getCoverImageUri() != null && !book.getCoverImageUri().isEmpty()) {
            holder.imgCover.setImageURI(Uri.parse(book.getCoverImageUri()));
        } else {
            holder.imgCover.setImageResource(book.getCoverImageRes());
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList != null ? bookList.size() : 0;
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        TextView tvTitle, tvAuthor, tvYear;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.img_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvYear = itemView.findViewById(R.id.tv_year);
        }
    }
}
