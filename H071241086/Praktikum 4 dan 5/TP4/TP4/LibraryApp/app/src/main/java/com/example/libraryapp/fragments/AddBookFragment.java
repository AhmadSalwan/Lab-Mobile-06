package com.example.libraryapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.libraryapp.R;
import com.example.libraryapp.models.Book;
import com.example.libraryapp.models.BookRepository;

public class AddBookFragment extends Fragment {

    private ImageView ivPreview;
    private EditText etTitle, etAuthor, etYear, etBlurb, etPages, etLanguage, etPublisher;
    private Spinner spinnerGenre;
    private Button btnPickImage, btnSave;
    private Uri selectedImageUri = null;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivPreview.setImageURI(selectedImageUri);
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivPreview = view.findViewById(R.id.iv_preview);
        etTitle = view.findViewById(R.id.et_title);
        etAuthor = view.findViewById(R.id.et_author);
        etYear = view.findViewById(R.id.et_year);
        etBlurb = view.findViewById(R.id.et_blurb);
        etPages = view.findViewById(R.id.et_pages);
        etLanguage = view.findViewById(R.id.et_language);
        etPublisher = view.findViewById(R.id.et_publisher);
        spinnerGenre = view.findViewById(R.id.spinner_genre_add);
        btnPickImage = view.findViewById(R.id.btn_pick_image);
        btnSave = view.findViewById(R.id.btn_save);

        String[] genres = {"Fiction", "Non-Fiction", "Fantasy", "Sci-Fi", "Mystery",
                "Thriller", "Romance", "Horror", "Classic", "Dystopia",
                "Self-Help", "Historical Fiction", "Biography", "Adventure", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, genres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

        btnPickImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> saveBook());
    }

    private void saveBook() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String blurb = etBlurb.getText().toString().trim();
        String pagesStr = etPages.getText().toString().trim();
        String language = etLanguage.getText().toString().trim();
        String publisher = etPublisher.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) ||
                TextUtils.isEmpty(yearStr) || TextUtils.isEmpty(blurb)) {
            Toast.makeText(getContext(),
                    "Judul, penulis, tahun, dan deskripsi wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        int year, pages;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            etYear.setError("Masukkan tahun yang valid");
            return;
        }

        try {
            pages = TextUtils.isEmpty(pagesStr) ? 0 : Integer.parseInt(pagesStr);
        } catch (NumberFormatException e) {
            pages = 0;
        }

        Book newBook = new Book(title, author, year, blurb, genre, 0f,
                "", pages,
                TextUtils.isEmpty(language) ? "Unknown" : language,
                TextUtils.isEmpty(publisher) ? "Unknown" : publisher);

        if (selectedImageUri != null) {
            newBook.setCoverUri(selectedImageUri);
        }

        BookRepository.getInstance().addBook(newBook);
        Toast.makeText(getContext(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
        clearForm();
    }

    private void clearForm() {
        etTitle.setText("");
        etAuthor.setText("");
        etYear.setText("");
        etBlurb.setText("");
        etPages.setText("");
        etLanguage.setText("");
        etPublisher.setText("");
        ivPreview.setImageResource(R.drawable.ic_add_photo);
        selectedImageUri = null;
        spinnerGenre.setSelection(0);
    }
}
