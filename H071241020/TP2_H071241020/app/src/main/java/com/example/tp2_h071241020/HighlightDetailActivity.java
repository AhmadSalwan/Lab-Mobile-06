package com.example.tp2_h071241020;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HighlightDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlight_detail);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        Highlight highlight = getIntent().getParcelableExtra("EXTRA_HIGHLIGHT");

        ImageView ivFull = findViewById(R.id.iv_highlight_full);
        TextView tvTitle = findViewById(R.id.tv_highlight_name_full);

        if (highlight != null) {
            ivFull.setImageResource(highlight.getImage());
            tvTitle.setText(highlight.getTitle());
        }

        findViewById(R.id.btn_close_highlight).setOnClickListener(v -> finish());
    }
}