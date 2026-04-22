package com.example.tp3_h071241020.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.tp3_h071241020.R;
import com.example.tp3_h071241020.models.Book;
import com.example.tp3_h071241020.models.BookRepository;

public class DetailActivity extends AppCompatActivity {
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String id = getIntent().getStringExtra("BOOK_ID");
        book = BookRepository.getBookById(id);

        if (book == null) {
            Toast.makeText(this, "Buku tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ImageView ivCover = findViewById(R.id.ivDetailCover);
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvAuthor = findViewById(R.id.tvDetailAuthor);
        TextView tvInfo = findViewById(R.id.tvDetailInfo);
        TextView tvBlurb = findViewById(R.id.tvDetailBlurb);
        ImageButton btnLike = findViewById(R.id.btnLike);

        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        tvInfo.setText(String.format("%s | %s | %s", book.getGenre(), book.getPublisher(), book.getYear()));
        tvBlurb.setText(String.format("Edisi: %s\n\n%s", book.getEdition(), book.getBlurb()));

        if (book.getCoverUri() != null && !book.getCoverUri().isEmpty()) {
            ivCover.setImageURI(Uri.parse(book.getCoverUri()));
        } else {
            ivCover.setImageResource(book.getCoverResId());
        }

        updateLikeUI(btnLike);
        btnLike.setOnClickListener(v -> {
            book.setLiked(!book.isLiked());
            updateLikeUI(btnLike);
        });
    }

    private void updateLikeUI(ImageButton btn) {
        if (book.isLiked()) {
            btn.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            btn.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Edit");
        menu.add(0, 2, 1, "Hapus");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == 1) {
            Toast.makeText(this, "Kembali ke Beranda dan tahan lama pada buku untuk mengedit", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == 2) {
            // Mode Hapus
            BookRepository.deleteBook(book.getId());
            Toast.makeText(this, "Buku berhasil dihapus", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}