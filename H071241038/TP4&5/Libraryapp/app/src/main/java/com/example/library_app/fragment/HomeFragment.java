package com.example.library_app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_app.R;
import com.example.library_app.adapter.BookAdapter;
import com.example.library_app.model.Book;
import com.example.library_app.utils.BookRepository;
import com.example.library_app.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private BookAdapter adapterNewArrivals;
    private BookAdapter adapterMyBooks;
    private SearchView searchView;
    private ProgressBar progressBarHome;
    private PrefManager prefManager;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefManager = new PrefManager(requireContext());

        TextView tvUserGreeting = view.findViewById(R.id.tvDiscover);
        if (tvUserGreeting != null) {
            tvUserGreeting.setText("Hi, " + prefManager.getUsername() + "!");
        }

        // LOGIKA TOMBOL SWITCH MODE PIL KUSTOM
        ConstraintLayout btnModeSwitch = view.findViewById(R.id.btn_mode_switch);
        ImageView ivModeIcon = view.findViewById(R.id.iv_mode_icon);
        TextView tvModeLabel = view.findViewById(R.id.tv_mode_label);

        if (btnModeSwitch != null && ivModeIcon != null && tvModeLabel != null) {
            // Terapkan desain tombol di awal buka halaman
            boolean isCurrentlyDark = prefManager.isDarkMode();
            updateCustomSwitchView(btnModeSwitch, ivModeIcon, tvModeLabel, isCurrentlyDark);

            // Jika tombol pil diklik
            btnModeSwitch.setOnClickListener(v -> {
                boolean newNightMode = !prefManager.isDarkMode();
                prefManager.setDarkMode(newNightMode);

                // Ubah tampilan tombol seketika saat diklik
                updateCustomSwitchView(btnModeSwitch, ivModeIcon, tvModeLabel, newNightMode);

                // Terapkan perubahan ke seluruh aplikasi
                if (newNightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            });
        }

        searchView = view.findViewById(R.id.searchView);
        progressBarHome = view.findViewById(R.id.progressBarHome);

        RecyclerView rvNewArrivals = view.findViewById(R.id.recyclerView);
        if (rvNewArrivals != null) {
            rvNewArrivals.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            adapterNewArrivals = new BookAdapter(new ArrayList<>(), R.layout.item_book);
            rvNewArrivals.setAdapter(adapterNewArrivals);
        }

        RecyclerView rvMyBooks = view.findViewById(R.id.recyclerViewMyBooks);
        if (rvMyBooks != null) {
            rvMyBooks.setLayoutManager(new LinearLayoutManager(getContext()));
            rvMyBooks.setNestedScrollingEnabled(false);
            adapterMyBooks = new BookAdapter(new ArrayList<>(), R.layout.item_book_horizontal);
            rvMyBooks.setAdapter(adapterMyBooks);
        }

        if (searchView != null) {
            searchView.setIconifiedByDefault(false);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterBooksAsync(newText);
                    return true;
                }
            });
        }
    }

    // Method pembantu untuk menggambar tombol switch
    private void updateCustomSwitchView(ConstraintLayout container, ImageView icon, TextView label, boolean isNight) {
        if (getContext() == null) return;

        // Menggambar background kapsul/pil (melengkung 100f)
        android.graphics.drawable.GradientDrawable bgShape = new android.graphics.drawable.GradientDrawable();
        bgShape.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
        bgShape.setCornerRadius(100f);

        // Menggambar lingkaran putih/hitam di belakang ikon
        android.graphics.drawable.GradientDrawable iconBgShape = new android.graphics.drawable.GradientDrawable();
        iconBgShape.setShape(android.graphics.drawable.GradientDrawable.OVAL);

        if (isNight) {
            // Desain Mode Malam
            bgShape.setColor(ContextCompat.getColor(getContext(), R.color.mode_switch_bg_night));
            iconBgShape.setColor(android.graphics.Color.WHITE);
            label.setText("NIGHT MODE");
            label.setTextColor(android.graphics.Color.WHITE);
            // Ikon bintang bawaan Android yang aman
            icon.setImageResource(android.R.drawable.star_on);
        } else {
            // Desain Mode Siang
            bgShape.setColor(ContextCompat.getColor(getContext(), R.color.mode_switch_bg_day));
            iconBgShape.setColor(android.graphics.Color.WHITE);
            label.setText("DAY MODE");
            label.setTextColor(android.graphics.Color.BLACK);
            // Ikon target/bulat bawaan Android yang aman
            icon.setImageResource(android.R.drawable.ic_menu_mylocation);
        }

        icon.setBackground(iconBgShape);
        container.setBackground(bgShape);
    }

    private void filterBooksAsync(String text) {
        if (progressBarHome != null) {
            progressBarHome.setVisibility(View.VISIBLE);
        }

        executorService.execute(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Book> filteredList = new ArrayList<>();
            for (Book book : BookRepository.bookList) {
                if (book.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(book);
                }
            }

            handler.post(() -> {
                if (progressBarHome != null) {
                    progressBarHome.setVisibility(View.GONE);
                }

                if (adapterNewArrivals != null) adapterNewArrivals.setFilteredList(filteredList);
                if (adapterMyBooks != null) adapterMyBooks.setFilteredList(filteredList);

                if (filteredList.isEmpty() && !text.isEmpty() && getContext() != null) {
                    Toast.makeText(getContext(), "Buku tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String currentQuery = (searchView != null && searchView.getQuery() != null) ? searchView.getQuery().toString() : "";
        filterBooksAsync(currentQuery);

        View root = getView();
        if (root != null && prefManager != null) {
            TextView tvUserGreeting = root.findViewById(R.id.tvDiscover);
            if (tvUserGreeting != null) {
                tvUserGreeting.setText("Hi, " + prefManager.getUsername() + "!");
            }
        }
    }
}


