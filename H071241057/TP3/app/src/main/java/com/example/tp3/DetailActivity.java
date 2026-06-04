package com.example.tp3;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private Book book;
    private ImageButton btnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ivCover = findViewById(R.id.iv_detail_cover);
        TextView tvTitle = findViewById(R.id.tv_detail_title);
        TextView tvAuthor = findViewById(R.id.tv_detail_author);
        TextView tvYearGenre = findViewById(R.id.tv_detail_year_genre);
        RatingBar rbRating = findViewById(R.id.rb_detail_rating);
        TextView tvBlurb = findViewById(R.id.tv_detail_blurb);
        btnLike = findViewById(R.id.btn_like);

        book = getIntent().getParcelableExtra("EXTRA_BOOK");

        if (book != null) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            String yearGenre = book.getYear() + " | " + book.getGenre();
            tvYearGenre.setText(yearGenre);
            rbRating.setRating(book.getRating());
            tvBlurb.setText(book.getBlurb());
            
            updateLikeButtonIcon();

            btnLike.setOnClickListener(v -> {
                boolean newLikedStatus = !book.isLiked();
                book.setLiked(newLikedStatus);
                updateLikeButtonIcon();
                updateDataSource(book.getId(), newLikedStatus);
            });
            
            String imageUri = book.getImageUri();
            if (imageUri != null && !imageUri.isEmpty()) {
                if (imageUri.startsWith("content://") || imageUri.startsWith("file://")) {
                    ivCover.setImageURI(Uri.parse(imageUri));
                } else {
                    int resId = getResources().getIdentifier(imageUri, "drawable", getPackageName());
                    if (resId != 0) {
                        ivCover.setImageResource(resId);
                    } else {
                        ivCover.setImageResource(android.R.drawable.ic_menu_report_image);
                    }
                }
            } else {
                ivCover.setImageResource(android.R.drawable.ic_menu_report_image);
            }
        }
    }

    private void updateDataSource(String id, boolean liked) {
        List<Book> allBooks = DataSource.getInstance().getBooks();
        for (Book b : allBooks) {
            if (b.getId().equals(id)) {
                b.setLiked(liked);
                break;
            }
        }
    }

    private void updateLikeButtonIcon() {
        if (book.isLiked()) {
            btnLike.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            btnLike.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
}
