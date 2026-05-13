package com.example.library_app.fragment;

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

import com.example.library_app.R;
import com.example.library_app.adapter.BookAdapter;
import com.example.library_app.model.Book;
import com.example.library_app.utils.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteFragment extends Fragment {

    private BookAdapter adapter;
    private List<Book> favoriteBooks = new ArrayList<>();
    private ProgressBar progressBarFavorite; // Tambahan variabel untuk animasi loading

    // Inisialisasi thread dan handler untuk pemrosesan background
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFavorite);
        progressBarFavorite = view.findViewById(R.id.progressBarFavorite); // Inisialisasi ProgressBar dari XML

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Menggunakan constructor baru dengan layout horizontal agar konsisten dengan desain "My Books"
        adapter = new BookAdapter(favoriteBooks, R.layout.item_book_horizontal);
        recyclerView.setAdapter(adapter);
    }

    // Fungsi baru untuk memuat buku favorit menggunakan Background Thread
    private void loadFavoritesAsync() {
        // 1. Tampilkan ProgressBar di Main UI Thread
        if (progressBarFavorite != null) {
            progressBarFavorite.setVisibility(View.VISIBLE);
        }

        // 2. Eksekusi pemfilteran data di Background Thread
        executorService.execute(() -> {
            try {
                // Simulasi proses delay 400ms agar animasi loading bisa terlihat
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Book> tempFavs = new ArrayList<>();
            for (Book book : BookRepository.bookList) {
                if (book.isLiked()) {
                    tempFavs.add(book);
                }
            }

            // 3. Kembali ke Main UI Thread untuk memperbarui RecyclerView
            handler.post(() -> {
                // Sembunyikan ProgressBar
                if (progressBarFavorite != null) {
                    progressBarFavorite.setVisibility(View.GONE);
                }

                // Perbarui data di dalam list dan beri tahu adapter
                favoriteBooks.clear();
                favoriteBooks.addAll(tempFavs);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Cukup panggil fungsi asinkron ini setiap kali tab Favorite dibuka
        loadFavoritesAsync();
    }
}