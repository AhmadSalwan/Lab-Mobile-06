package com.example.tp3;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> books;
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public BookAdapter(List<Book> books, OnBookClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book, listener);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle, tvAuthor, tvYear;
        RatingBar rbRating;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_book_cover);
            tvTitle = itemView.findViewById(R.id.tv_book_title);
            tvAuthor = itemView.findViewById(R.id.tv_book_author);
            tvYear = itemView.findViewById(R.id.tv_book_year);
            rbRating = itemView.findViewById(R.id.rb_book_rating);
        }

        public void bind(final Book book, final OnBookClickListener listener) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            tvYear.setText(String.valueOf(book.getYear()));
            rbRating.setRating(book.getRating());

            Context context = itemView.getContext();
            String imageUri = book.getImageUri();

            if (imageUri != null && !imageUri.isEmpty()) {
                if (imageUri.startsWith("content://") || imageUri.startsWith("file://")) {
                    ivCover.setImageURI(Uri.parse(imageUri));
                } else {
                    int resId = context.getResources().getIdentifier(imageUri, "drawable", context.getPackageName());
                    if (resId != 0) {
                        ivCover.setImageResource(resId);
                    } else {
                        ivCover.setImageResource(android.R.drawable.ic_menu_report_image);
                    }
                }
            } else {
                ivCover.setImageResource(android.R.drawable.ic_menu_report_image);
            }

            itemView.setOnClickListener(v -> listener.onBookClick(book));
        }
    }
}
