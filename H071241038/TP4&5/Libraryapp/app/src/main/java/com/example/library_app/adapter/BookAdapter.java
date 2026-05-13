package com.example.library_app.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_app.DetailActivity;
import com.example.library_app.R;
import com.example.library_app.model.Book;
import com.example.library_app.utils.BookRepository;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private int layoutId;

    // Constructor baru dengan parameter layoutId agar bisa digunakan untuk berbagai desain item
    public BookAdapter(List<Book> bookList, int layoutId) {
        this.bookList = bookList;
        this.layoutId = layoutId;
    }

    public void setFilteredList(List<Book> filteredList) {
        this.bookList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());

        // Jika menggunakan layout horizontal, set tahun/tanggal di tvDate jika ada
        if (holder.tvDate != null) {
            holder.tvDate.setText("Published: " + book.getYear());
        }

        if (book.getImageUri() != null) {
            holder.imageBook.setImageURI(book.getImageUri());
        } else if (book.getImageResId() != 0) {
            holder.imageBook.setImageResource(book.getImageResId());
        } else {
            holder.imageBook.setImageResource(R.drawable.book_placeholder);
        }

        holder.itemView.setOnClickListener(v -> {
            // Cari index asli di Repository agar tidak salah buka saat di-filter
            int originalIndex = BookRepository.bookList.indexOf(book);
            
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("BOOK_INDEX", originalIndex);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageBook;
        TextView tvTitle, tvAuthor, tvDate;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBook = itemView.findViewById(R.id.imageBook);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvDate = itemView.findViewById(R.id.tvDate); // Hanya ada di item_book_horizontal
        }
    }
}