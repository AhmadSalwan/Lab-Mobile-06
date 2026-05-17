package com.example.libraryapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.DetailActivity;
import com.example.libraryapp.R;
import com.example.libraryapp.adapters.BookAdapter;
import com.example.libraryapp.models.Book;
import com.example.libraryapp.models.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private SearchView searchView;
    private Spinner spinnerGenre;
    private ProgressBar progressBar;

    private List<Book> allBooks;
    private String currentQuery = "";
    private String currentGenre = "All";

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_home);
        searchView = view.findViewById(R.id.search_view);
        spinnerGenre = view.findViewById(R.id.spinner_genre);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new BookAdapter(getContext(), new ArrayList<>(), book -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("book_id", book.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        setupSearchView();
        loadInitialData();
    }

    private void loadInitialData() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        
        executor.execute(() -> {
            allBooks = BookRepository.getInstance().getAllBooks();
            
            handler.post(() -> {
                setupGenreFilter();
                applyFilters();
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (allBooks != null) {
            allBooks = BookRepository.getInstance().getAllBooks();
            applyFilters();
        }
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentQuery = query;
                applyFilters();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentQuery = newText;
                applyFilters();
                return true;
            }
        });
    }

    private void setupGenreFilter() {
        List<String> genres = BookRepository.getInstance().getAllGenres();
        
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, genres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(genreAdapter);

        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentGenre = genres.get(position);
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void applyFilters() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        
        executor.execute(() -> {
            try { Thread.sleep(300); } catch (InterruptedException e) {}

            List<Book> filtered = new ArrayList<>();
            if (allBooks != null) {
                for (Book book : allBooks) {
                    boolean matchTitle = book.getTitle().toLowerCase()
                            .contains(currentQuery.toLowerCase());
                    boolean matchGenre = currentGenre.equals("All") ||
                            book.getGenre().equals(currentGenre);

                    if (matchTitle && matchGenre) {
                        filtered.add(book);
                    }
                }
            }

            handler.post(() -> {
                if (adapter != null) adapter.updateList(filtered);
                if (progressBar != null) progressBar.setVisibility(View.GONE);
            });
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
