package com.fadhil.tp4.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fadhil.tp4.R;
import com.fadhil.tp4.data.BookProvider;
import com.fadhil.tp4.models.Book;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgCover;
    private TextView tvTitle, tvAuthorYear, tvGenreRating, tvBlurb;
    private ImageButton btnBack, btnLike;
    private Book currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgCover = findViewById(R.id.img_cover);
        tvTitle = findViewById(R.id.tv_title);
        tvAuthorYear = findViewById(R.id.tv_author_year);
        tvGenreRating = findViewById(R.id.tv_genre_rating);
        tvBlurb = findViewById(R.id.tv_blurb);
        btnBack = findViewById(R.id.btn_back);
        btnLike = findViewById(R.id.btn_like);

        String bookId = getIntent().getStringExtra("book_id");
        if (bookId != null) {
            for (Book book : BookProvider.getInstance().getBooks()) {
                if (book.getId().equals(bookId)) {
                    currentBook = book;
                    break;
                }
            }
        }

        if (currentBook == null) {
            Toast.makeText(this, "Book not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        displayBookDetails();

        btnBack.setOnClickListener(v -> finish());

        btnLike.setOnClickListener(v -> {
            currentBook.setLiked(!currentBook.isLiked());
            updateLikeIcon();
            if (currentBook.isLiked()) {
                Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayBookDetails() {
        tvTitle.setText(currentBook.getTitle());
        tvAuthorYear.setText(currentBook.getAuthor() + " • " + currentBook.getYear());
        tvGenreRating.setText("Genre: " + currentBook.getGenre() + " | Rating: " + currentBook.getRating() + " ⭐");
        tvBlurb.setText(currentBook.getBlurb());

        if (currentBook.getCoverImageUri() != null && !currentBook.getCoverImageUri().isEmpty()) {
            imgCover.setImageURI(Uri.parse(currentBook.getCoverImageUri()));
        } else {
            imgCover.setImageResource(currentBook.getCoverImageRes());
        }

        updateLikeIcon();
    }

    private void updateLikeIcon() {
        if (currentBook.isLiked()) {
            btnLike.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            btnLike.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
}
