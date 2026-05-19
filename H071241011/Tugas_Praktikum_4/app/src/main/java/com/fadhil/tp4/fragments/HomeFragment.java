package com.fadhil.tp4.fragments;

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
import androidx.appcompat.widget.SearchView;
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

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private SearchView searchView;
    private ProgressBar progressBar;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        recyclerView = view.findViewById(R.id.recycler_view);
        searchView = view.findViewById(R.id.search_view);
        progressBar = view.findViewById(R.id.progress_bar);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new BookAdapter(requireContext(), new ArrayList<>(), book -> {
            Intent intent = new Intent(requireContext(), DetailActivity.class);
            intent.putExtra("book_id", book.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        setupSearchView();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBooks("");
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadBooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadBooks(newText);
                return true;
            }
        });
    }

    private void loadBooks(String query) {
        progressBar.setVisibility(View.VISIBLE);
        executorService.execute(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Book> allBooks = BookProvider.getInstance().getBooks();
            List<Book> filteredBooks = new ArrayList<>();

            for (Book book : allBooks) {
                if (book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredBooks.add(book);
                }
            }

            mainHandler.post(() -> {
                if (isAdded()) {
                    adapter.updateData(filteredBooks);
                    progressBar.setVisibility(View.GONE);
                }
            });
        });
    }
}
