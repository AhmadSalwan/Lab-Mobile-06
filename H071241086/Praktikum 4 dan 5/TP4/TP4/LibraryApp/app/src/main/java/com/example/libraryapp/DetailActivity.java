package com.example.libraryapp;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libraryapp.models.Book;
import com.example.libraryapp.models.BookRepository;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    private Book book;
    private FloatingActionButton fabLike;
    private boolean isLiked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int bookId = getIntent().getIntExtra("book_id", -1);
        book = BookRepository.getInstance().getBookById(bookId);

        if (book == null) {
            finish();
            return;
        }

        // Toolbar back
        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        // Views
        ImageView ivCover = findViewById(R.id.iv_detail_cover);
        TextView tvTitle = findViewById(R.id.tv_detail_title);
        TextView tvAuthor = findViewById(R.id.tv_detail_author);
        TextView tvYear = findViewById(R.id.tv_detail_year);
        TextView tvGenre = findViewById(R.id.tv_detail_genre);
        TextView tvBlurb = findViewById(R.id.tv_detail_blurb);
        TextView tvPages = findViewById(R.id.tv_detail_pages);
        TextView tvLanguage = findViewById(R.id.tv_detail_language);
        TextView tvPublisher = findViewById(R.id.tv_detail_publisher);
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        TextView tvRatingNum = findViewById(R.id.tv_rating_number);
        fabLike = findViewById(R.id.fab_like);

        // Populate
        tvTitle.setText(book.getTitle());
        tvAuthor.setText("by " + book.getAuthor());
        tvYear.setText(String.valueOf(book.getYear()));
        tvGenre.setText(book.getGenre());
        tvBlurb.setText(book.getBlurb());
        tvPages.setText(book.getPageCount() > 0 ? book.getPageCount() + " halaman" : "N/A");
        tvLanguage.setText(book.getLanguage());
        tvPublisher.setText(book.getPublisher());
        ratingBar.setRating(book.getRating());
        tvRatingNum.setText(String.format("%.1f / 5.0", book.getRating()));

        // Cover
        if (book.getCoverUri() != null) {
            ivCover.setImageURI(book.getCoverUri());
        } else {
            int resId = getResources().getIdentifier(
                    book.getCoverUrl(), "drawable", getPackageName());
            ivCover.setImageResource(resId != 0 ? resId : R.drawable.cover_default);
        }

        // Like button
        isLiked = book.isLiked();
        updateLikeButton();

        fabLike.setOnClickListener(v -> {
            isLiked = !isLiked;
            book.setLiked(isLiked);
            updateLikeButton();
            Toast.makeText(this,
                    isLiked ? "❤ Ditambahkan ke Favorit" : "Dihapus dari Favorit",
                    Toast.LENGTH_SHORT).show();
        });
    }

    private void updateLikeButton() {
        fabLike.setImageResource(isLiked ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
    }
}
