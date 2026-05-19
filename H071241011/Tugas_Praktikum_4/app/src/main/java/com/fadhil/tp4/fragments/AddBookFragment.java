package com.fadhil.tp4.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fadhil.tp4.R;
import com.fadhil.tp4.data.BookProvider;
import com.fadhil.tp4.models.Book;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class AddBookFragment extends Fragment {

    private ImageView imgPreview;
    private Button btnPickImage, btnSave;
    private TextInputEditText etTitle, etAuthor, etYear, etBlurb;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                        imgPreview.setImageURI(selectedImageUri);
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgPreview = view.findViewById(R.id.img_preview);
        btnPickImage = view.findViewById(R.id.btn_pick_image);
        btnSave = view.findViewById(R.id.btn_save);

        etTitle = view.findViewById(R.id.et_title);
        etAuthor = view.findViewById(R.id.et_author);
        etYear = view.findViewById(R.id.et_year);
        etBlurb = view.findViewById(R.id.et_blurb);

        btnPickImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> saveBook());
    }

    private void saveBook() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String blurb = etBlurb.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty() || blurb.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Invalid year", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = UUID.randomUUID().toString();
        Book newBook = new Book(id, title, author, year, blurb, R.drawable.ic_dummy_cover, "General", 0.0f);
        if (selectedImageUri != null) {
            newBook.setCoverImageUri(selectedImageUri.toString());
        }

        BookProvider.getInstance().addBook(newBook);
        Toast.makeText(requireContext(), "Book added successfully!", Toast.LENGTH_SHORT).show();

        // Clear fields
        etTitle.setText("");
        etAuthor.setText("");
        etYear.setText("");
        etBlurb.setText("");
        imgPreview.setImageURI(null);
        imgPreview.setBackgroundResource(R.color.cool_gray);
        selectedImageUri = null;
    }
}
