package com.example.tp3_h071241020.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp3_h071241020.R;
import com.example.tp3_h071241020.activities.DetailActivity;
import com.example.tp3_h071241020.adapters.BookAdapter;
import com.example.tp3_h071241020.models.Book;
import com.example.tp3_h071241020.models.BookRepository;
import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements BookAdapter.OnBookClickListener {
    private BookAdapter adapter;
    private LinearLayout selectionBar;
    private TextView tvCount;
    private Button btnDelete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.recyclerViewFav);
        selectionBar = view.findViewById(R.id.selectionBar);
        tvCount = view.findViewById(R.id.tvCount);
        btnDelete = view.findViewById(R.id.btnDelete);

        adapter = new BookAdapter(new ArrayList<Book>(), this);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        btnDelete.setOnClickListener(v -> {
            for (Book b : adapter.getSelectedBooks()) {
                b.setLiked(false);
            }
            adapter.clearSelection();
            loadFavoriteBooks();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        List<Book> favs = new ArrayList<>();
        for (Book b : BookRepository.getBooks()) {
            if (b.isLiked()) favs.add(b);
        }
        adapter.updateData(favs);
        selectionBar.setVisibility(View.GONE);
    }

    @Override
    public void onBookClick(Book book) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("BOOK_ID", book.getId());
        startActivity(intent);
    }

    @Override
    public void onSelectionChanged(int count) {
        if (count == 0) {
            selectionBar.setVisibility(View.GONE);
        } else {
            selectionBar.setVisibility(View.VISIBLE);
            tvCount.setText(count + " terpilih");
        }
    }
}