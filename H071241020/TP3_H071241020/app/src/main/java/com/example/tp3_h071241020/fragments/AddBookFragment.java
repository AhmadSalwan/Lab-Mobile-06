package com.example.tp3_h071241020.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.tp3_h071241020.R;
import com.example.tp3_h071241020.models.Book;
import com.example.tp3_h071241020.models.BookRepository;

public class AddBookFragment extends Fragment {
    private String selectedImgUri = null;
    private ImageView ivPreview;
    private Book editBook = null;
    private EditText etT, etA, etY, etG, etE, etP, etB;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            res -> {
                if (res.getResultCode() == Activity.RESULT_OK && res.getData() != null) {
                    Uri uri = res.getData().getData();
                    if (uri != null) {
                        selectedImgUri = uri.toString();
                        ivPreview.setImageURI(uri);
                    }
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
        etT = view.findViewById(R.id.etTitle);
        etA = view.findViewById(R.id.etAuthor);
        etY = view.findViewById(R.id.etYear);
        etG = view.findViewById(R.id.etGenre);
        etE = view.findViewById(R.id.etEdition);
        etP = view.findViewById(R.id.etPublisher);
        etB = view.findViewById(R.id.etBlurb);
        ivPreview = view.findViewById(R.id.ivPreview);
        Button btnPick = view.findViewById(R.id.btnPickImage);
        Button btnSave = view.findViewById(R.id.btnSave);
        TextView tvHeader = view.findViewById(R.id.tvHeader);

        if (getArguments() != null && getArguments().containsKey("BOOK_ID_EDIT")) {
            String id = getArguments().getString("BOOK_ID_EDIT");
            editBook = BookRepository.getBookById(id);

            if (editBook != null) {
                tvHeader.setText("Edit Informasi Buku");
                btnSave.setText("Simpan Perubahan");

                etT.setText(editBook.getTitle());
                etA.setText(editBook.getAuthor());
                etY.setText(editBook.getYear());
                etG.setText(editBook.getGenre());
                etE.setText(editBook.getEdition());
                etP.setText(editBook.getPublisher());
                etB.setText(editBook.getBlurb());

                if (editBook.getCoverUri() != null) {
                    selectedImgUri = editBook.getCoverUri();
                    ivPreview.setImageURI(Uri.parse(selectedImgUri));
                } else {
                    ivPreview.setImageResource(editBook.getCoverResId());
                }
            }
        }

        btnPick.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            launcher.launch(i);
        });

        btnSave.setOnClickListener(v -> {
            String title = etT.getText().toString();
            if (title.isEmpty()) {
                Toast.makeText(getContext(), "Judul buku wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (editBook != null) {
                editBook.setTitle(title);
                editBook.setAuthor(etA.getText().toString());
                editBook.setYear(etY.getText().toString());
                editBook.setGenre(etG.getText().toString());
                editBook.setEdition(etE.getText().toString());
                editBook.setPublisher(etP.getText().toString());
                editBook.setBlurb(etB.getText().toString());
                editBook.setCoverUri(selectedImgUri);
                BookRepository.updateBook(editBook);
                Toast.makeText(getContext(), "Perubahan berhasil disimpan!", Toast.LENGTH_SHORT).show();
            } else {
                Book nb = new Book("ID-" + System.currentTimeMillis(), title, etA.getText().toString(),
                        etY.getText().toString(), etB.getText().toString(), etG.getText().toString(),
                        etE.getText().toString(), etP.getText().toString(), 5.0f, R.drawable._1, selectedImgUri);
                BookRepository.addBook(nb);
                Toast.makeText(getContext(), "Buku baru ditambahkan!", Toast.LENGTH_SHORT).show();
            }
            clearInputs();
        });
    }

    private void clearInputs() {
        editBook = null;
        etT.setText(""); etA.setText(""); etY.setText(""); etG.setText("");
        etE.setText(""); etP.setText(""); etB.setText("");
        ivPreview.setImageResource(android.R.drawable.ic_menu_gallery);
        selectedImgUri = null;
        setArguments(null);
    }
}