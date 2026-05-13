package com.example.library_app;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.example.library_app.model.Book;
import com.example.library_app.utils.BookRepository;
import com.example.library_app.utils.PrefManager;
import com.google.android.material.button.MaterialButton;

public class DetailActivity extends AppCompatActivity {

    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefManager = new PrefManager(this);
        if (prefManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ivDetailCover = findViewById(R.id.ivDetailCover);
        TextView tvDetailTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDetailAuthor = findViewById(R.id.tvDetailAuthor);
        TextView tvDetailYear = findViewById(R.id.tvDetailYear);
        TextView tvDetailBlurb = findViewById(R.id.tvDetailBlurb);
        MaterialButton btnLike = findViewById(R.id.btnLike);
        ImageView btnBack = findViewById(R.id.btnBack);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        int bookIndex = getIntent().getIntExtra("BOOK_INDEX", -1);

        if (bookIndex != -1) {
            Book selectedBook = BookRepository.bookList.get(bookIndex);

            tvDetailTitle.setText(selectedBook.getTitle());
            tvDetailAuthor.setText(selectedBook.getAuthor());
            tvDetailYear.setText(selectedBook.getYear());
            tvDetailBlurb.setText(selectedBook.getBlurb());

            if (selectedBook.getImageUri() != null) {
                ivDetailCover.setImageURI(selectedBook.getImageUri());
            } else if (selectedBook.getImageResId() != 0) {
                ivDetailCover.setImageResource(selectedBook.getImageResId());
            } else {
                ivDetailCover.setImageResource(R.drawable.book_placeholder);
            }

            updateButtonState(btnLike, selectedBook.isLiked());

            btnLike.setOnClickListener(v -> {
                boolean newState = !selectedBook.isLiked();
                selectedBook.setLiked(newState);
                updateButtonState(btnLike, newState);
                
                // Simpan perubahan status favorit ke SharedPreferences
                BookRepository.saveBooks(this);

                String message = newState ? "Berhasil ditambahkan ke Favorit!" : "Dihapus dari Favorit.";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void updateButtonState(MaterialButton btnLike, boolean isLiked) {
        if (isLiked) {
            btnLike.setText("Hapus dari Favorit");
            btnLike.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_secondary));
            btnLike.setTextColor(ContextCompat.getColor(this, R.color.background));
        } else {
            btnLike.setText("Tambahkan ke Favorit");
            btnLike.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary));
            btnLike.setTextColor(ContextCompat.getColor(this, R.color.background));
        }
    }
}