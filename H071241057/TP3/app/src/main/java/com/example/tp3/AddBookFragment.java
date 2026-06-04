package com.example.tp3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class AddBookFragment extends Fragment {

    private EditText etTitle, etAuthor, etYear, etGenre, etBlurb;
    private RatingBar rbRating;
    private ImageView ivPreview;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivPreview.setImageURI(selectedImageUri);
                    ivPreview.setVisibility(View.VISIBLE);
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        etTitle = view.findViewById(R.id.et_title);
        etAuthor = view.findViewById(R.id.et_author);
        etYear = view.findViewById(R.id.et_year);
        etGenre = view.findViewById(R.id.et_genre);
        etBlurb = view.findViewById(R.id.et_blurb);
        rbRating = view.findViewById(R.id.rb_add_rating);
        ivPreview = view.findViewById(R.id.iv_preview);
        Button btnSelectImage = view.findViewById(R.id.btn_select_image);
        Button btnSave = view.findViewById(R.id.btn_save);

        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> saveBook());

        return view;
    }

    private void saveBook() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String genre = etGenre.getText().toString().trim();
        String blurb = etBlurb.getText().toString().trim();
        float rating = rbRating.getRating();

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int year = Integer.parseInt(yearStr);
        String imageUriStr = (selectedImageUri != null) ? selectedImageUri.toString() : "";

        Book newBook = new Book(
                UUID.randomUUID().toString(),
                title,
                author,
                year,
                blurb,
                imageUriStr,
                false,
                genre,
                rating
        );

        DataSource.getInstance().addBook(newBook);
        Toast.makeText(getContext(), "Book added successfully!", Toast.LENGTH_SHORT).show();

        clearFields();
    }

    private void clearFields() {
        etTitle.setText("");
        etAuthor.setText("");
        etYear.setText("");
        etGenre.setText("");
        etBlurb.setText("");
        rbRating.setRating(0);
        ivPreview.setVisibility(View.GONE);
        selectedImageUri = null;
    }
}
