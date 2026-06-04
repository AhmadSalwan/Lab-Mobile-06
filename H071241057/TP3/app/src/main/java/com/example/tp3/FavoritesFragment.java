package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {
    private RecyclerView rvFavorites;
    private BookAdapter adapter;
    private List<Book> favoriteBooks;

    private ProgressBar progressBar;

    private Handler mainHandler;
    private Thread favoriteThread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        rvFavorites = view.findViewById(R.id.rv_favorites);
        favoriteBooks = new ArrayList<>();
        progressBar = view.findViewById(R.id.progressBar);
        mainHandler = new Handler(Looper.getMainLooper());
        adapter = new BookAdapter(favoriteBooks, book -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("EXTRA_BOOK", book);
            startActivity(intent);
        });
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavorites.setAdapter(adapter);
        updateFavoriteList();
        return view;
    }

    private void updateFavoriteList() {
        favoriteBooks.clear();
        for (Book book : DataSource.getInstance().getBooks()) {
            if (book.isLiked()) {
                favoriteBooks.add(book);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void applyFavoritelistasync() {
        if (favoriteThread != null && favoriteThread.isAlive()) {
            favoriteThread.interrupt();
        }
        progressBar.setVisibility(View.VISIBLE);
        favoriteThread = new Thread(() -> {

            try {
                Thread.sleep(1000);
                List<Book> tempFavoritebooks = new ArrayList<>();
                for (Book book : DataSource.getInstance().getBooks()){
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    if (book.isLiked()) {
                        tempFavoritebooks.add(book);
                    }
                }
                mainHandler.post(() -> {
                    if (!Thread.currentThread().isInterrupted()) {
                        favoriteBooks.clear();
                        favoriteBooks.addAll(tempFavoritebooks);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } catch (InterruptedException e) {
                mainHandler.post(() -> progressBar.setVisibility(View.GONE));
            }
        });
        favoriteThread.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        applyFavoritelistasync();
    }
}
