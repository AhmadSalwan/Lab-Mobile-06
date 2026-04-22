package com.example.tp3_h071241020.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp3_h071241020.R;
import com.example.tp3_h071241020.activities.DetailActivity;
import com.example.tp3_h071241020.activities.MainActivity;
import com.example.tp3_h071241020.adapters.BookAdapter;
import com.example.tp3_h071241020.models.Book;
import com.example.tp3_h071241020.models.BookRepository;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements BookAdapter.OnBookClickListener {
    private BookAdapter adapter;
    private LinearLayout selectionBar;
    private TextView tvCount;
    private Button btnEdit, btnDelete, btnFav;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        SearchView sv = view.findViewById(R.id.searchView);
        selectionBar = view.findViewById(R.id.selectionBar);
        tvCount = view.findViewById(R.id.tvCount);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnFav = view.findViewById(R.id.btnFav);

        adapter = new BookAdapter(BookRepository.getBooks(), this);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        btnDelete.setOnClickListener(v -> {
            for (Book b : adapter.getSelectedBooks()) {
                BookRepository.deleteBook(b.getId());
            }
            adapter.clearSelection();
            adapter.updateData(BookRepository.getBooks());
            Toast.makeText(getContext(), "Buku berhasil dihapus", Toast.LENGTH_SHORT).show();
        });

        btnFav.setOnClickListener(v -> {
            for (Book b : adapter.getSelectedBooks()) {
                b.setLiked(true);
            }
            adapter.clearSelection();
            Toast.makeText(getContext(), "Berhasil ditambahkan ke Favorit", Toast.LENGTH_SHORT).show();
        });

        btnEdit.setOnClickListener(v -> {
            if (adapter.getSelectedBooks().size() == 1) {
                String id = adapter.getSelectedBooks().get(0).getId();
                adapter.clearSelection();
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToEdit(id);
                }
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) { return false; }
            @Override
            public boolean onQueryTextChange(String q) {
                filterBooks(q);
                return true;
            }
        });
    }

    private void filterBooks(String q) {
        List<Book> filtered = new ArrayList<>();
        for (Book b : BookRepository.getBooks()) {
            if (b.getTitle().toLowerCase().contains(q.toLowerCase())) {
                filtered.add(b);
            }
        }
        adapter.updateData(filtered);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateData(BookRepository.getBooks());
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
            btnEdit.setVisibility(count == 1 ? View.VISIBLE : View.GONE);
        }
    }
}