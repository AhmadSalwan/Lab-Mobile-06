package com.example.tp3_h071241020.adapters;

import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp3_h071241020.R;
import com.example.tp3_h071241020.models.Book;
import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> books;
    private List<Book> selectedBooks = new ArrayList<>();
    private OnBookClickListener listener;
    private boolean isSelectMode = false;

    public interface OnBookClickListener {
        void onBookClick(Book book);
        void onSelectionChanged(int count);
    }

    public BookAdapter(List<Book> books, OnBookClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    public void updateData(List<Book> newBooks) {
        this.books = newBooks;
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selectedBooks.clear();
        isSelectMode = false;
        notifyDataSetChanged();
    }

    public List<Book> getSelectedBooks() { return selectedBooks; }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvGenre.setText(book.getGenre());

        String blurb = book.getBlurb();
        if (blurb.length() > 60) blurb = blurb.substring(0, 60) + ".........";
        holder.tvBlurb.setText(blurb);

        if (book.getCoverUri() != null) {
            holder.ivCover.setImageURI(Uri.parse(book.getCoverUri()));
        } else {
            holder.ivCover.setImageResource(book.getCoverResId());
        }

        holder.itemView.setBackgroundColor(selectedBooks.contains(book) ? Color.parseColor("#D1E9FF") : Color.TRANSPARENT);

        holder.itemView.setOnClickListener(v -> {
            if (isSelectMode) {
                toggleSelection(book);
            } else {
                listener.onBookClick(book);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            isSelectMode = true;
            toggleSelection(book);
            return true;
        });
    }

    private void toggleSelection(Book book) {
        if (selectedBooks.contains(book)) selectedBooks.remove(book);
        else selectedBooks.add(book);

        if (selectedBooks.isEmpty()) isSelectMode = false;
        notifyDataSetChanged();
        listener.onSelectionChanged(selectedBooks.size());
    }

    @Override
    public int getItemCount() { return books.size(); }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle, tvAuthor, tvGenre, tvBlurb;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            tvBlurb = itemView.findViewById(R.id.tvBlurb);
        }
    }
}