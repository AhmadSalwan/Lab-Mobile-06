package com.fadhil.tp4.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fadhil.tp4.R;
import com.fadhil.tp4.activities.DetailActivity;
import com.fadhil.tp4.adapters.BookAdapter;
import com.fadhil.tp4.data.BookProvider;
import com.fadhil.tp4.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvEmpty;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        tvEmpty = view.findViewById(R.id.tv_empty);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new BookAdapter(requireContext(), new ArrayList<>(), book -> {
            Intent intent = new Intent(requireContext(), DetailActivity.class);
            intent.putExtra("book_id", book.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.GONE);

        executorService.execute(() -> {
            try {
                Thread.sleep(400); // Simulate processing time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Book> allBooks = BookProvider.getInstance().getBooks();
            List<Book> likedBooks = new ArrayList<>();

            for (Book book : allBooks) {
                if (book.isLiked()) {
                    likedBooks.add(book);
                }
            }

            mainHandler.post(() -> {
                if (isAdded()) {
                    progressBar.setVisibility(View.GONE);
                    adapter.updateData(likedBooks);
                    
                    if (likedBooks.isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        tvEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        });
    }
}
