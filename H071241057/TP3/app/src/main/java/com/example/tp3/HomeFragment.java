package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {
    private BookAdapter adapter;
    private List<Book> allBooks;
    private List<Book> filteredBooks;
    private String currentSearchText = "";
    private String currentGenre = "All";
    private String currentRating = "All";
    private ProgressBar progressBar;
    private Handler mainHandler;
    private Thread filterThread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView rvBooks = view.findViewById(R.id.rv_books);
        SearchView searchView = view.findViewById(R.id.searchView);
        Spinner spinnerGenre = view.findViewById(R.id.spinnerGenre);
        Spinner spinnerRating = view.findViewById(R.id.spinnerRating);
        progressBar = view.findViewById(R.id.progressBar);
        mainHandler = new Handler(Looper.getMainLooper());
        allBooks = DataSource.getInstance().getBooks();
        filteredBooks = new ArrayList<>(allBooks);
        adapter = new BookAdapter(filteredBooks, book -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("EXTRA_BOOK", book);
            startActivity(intent);
        });
        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBooks.setAdapter(adapter);
        List<String> genres = new ArrayList<>();
        genres.add("All");
        Set<String> uniqueGenres = new HashSet<>();
        for (Book b : allBooks) {
            uniqueGenres.add(b.getGenre());
        }
        genres.addAll(uniqueGenres);
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, genres);
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
        List<String> ratings = Arrays.asList("All", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0");
        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ratings);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRating.setAdapter(ratingAdapter);
        spinnerRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentRating = ratings.get(position);
                applyFilters();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }
            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchText = newText;
                applyFiltersAsync();
                return true;
            }
        });
        return view;
    }

    private void applyFilters() {
        filteredBooks.clear();
        for (Book book : allBooks) {
            boolean matchesSearch = book.getTitle().toLowerCase().contains(currentSearchText.toLowerCase());
            boolean matchesGenre = currentGenre.equals("All") || book.getGenre().equals(currentGenre);
            boolean matchesRating = checkRating(book.getRating(), currentRating);
            if (matchesSearch && matchesGenre && matchesRating) {
                filteredBooks.add(book);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void applyFiltersAsync() {
        if (filterThread != null && filterThread.isAlive()) {
            filterThread.interrupt();
        }
        progressBar.setVisibility(View.VISIBLE);
        filterThread = new Thread(() -> {
            try {
                Thread.sleep(300);
                List<Book> tempFilteredBooks = new ArrayList<>();
                for (Book book : allBooks) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    boolean matchesSearch = book.getTitle().toLowerCase().contains(currentSearchText.toLowerCase());
                    boolean matchesGenre = currentGenre.equals("All") || book.getGenre().equals(currentGenre);
                    boolean matchesRating = checkRating(book.getRating(), currentRating);
                    if (matchesSearch && matchesGenre && matchesRating) {
                        tempFilteredBooks.add(book);
                    }
                }
                mainHandler.post(() -> {
                    if (!Thread.currentThread().isInterrupted()) {
                        filteredBooks.clear();
                        filteredBooks.addAll(tempFilteredBooks);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } catch (InterruptedException e) {
                mainHandler.post(() -> progressBar.setVisibility(View.GONE));
            }
        });
        filterThread.start();
    }

    private boolean checkRating(float rating, String filter) {
        switch (filter) {
            case "1.0": return rating == 1.0;
            case "1.5": return rating == 1.5;
            case "2.0": return rating == 2.0;
            case "2.5": return rating == 2.5;
            case "3.0": return rating == 3.0;
            case "3.5": return rating == 3.5;
            case "4.0": return rating == 4.0;
            case "4.5": return rating == 4.5;
            case "5.0": return rating == 5.0;
            default: return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
