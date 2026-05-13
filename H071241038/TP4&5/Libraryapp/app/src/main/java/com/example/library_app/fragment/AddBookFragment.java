package com.example.library_app.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.library_app.R;
import com.example.library_app.model.Book;
import com.example.library_app.utils.BookRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddBookFragment extends Fragment {

    private Uri selectedImageUri = null;
    private TextView tvFileName;
    private ProgressBar progressBarAdd; 

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final ActivityResultLauncher<PickVisualMediaRequest> selectImageLauncher =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    //perbaikan dari error sebelumnya saat asistensi
                    requireContext().getContentResolver().takePersistableUriPermission(
                            uri,
                            android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                    );

                    String fileName = getFileName(uri);
                    tvFileName.setText("File terpilih: " + fileName);
                    Toast.makeText(getContext(), "Gambar berhasil dipilih!", Toast.LENGTH_SHORT).show();
                }
            });

    public AddBookFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvFileName = view.findViewById(R.id.tvFileName);
        progressBarAdd = view.findViewById(R.id.progressBarAdd); 
        Button btnChooseImage = view.findViewById(R.id.btnChooseImage);
        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etAuthor = view.findViewById(R.id.etAuthor);
        EditText etYear = view.findViewById(R.id.etYear);
        EditText etBlurb = view.findViewById(R.id.etBlurb);
        Button btnSave = view.findViewById(R.id.btnSave);

        btnChooseImage.setOnClickListener(v -> {
            selectImageLauncher.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();
            String year = etYear.getText().toString().trim();
            String blurb = etBlurb.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty() || year.isEmpty()) {
                Toast.makeText(getContext(), "Judul, Penulis, dan Tahun wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (progressBarAdd != null) progressBarAdd.setVisibility(View.VISIBLE);
            btnSave.setEnabled(false);

            executorService.execute(() -> {
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Book newBook = new Book(title, author, year, blurb, selectedImageUri, false, "General", 0.0f);
                BookRepository.bookList.add(0, newBook);
                
                // Simpan data secara permanen ke SharedPreferences
                if (getContext() != null) {
                    BookRepository.saveBooks(getContext());
                }

                handler.post(() -> {
                    if (progressBarAdd != null) progressBarAdd.setVisibility(View.GONE);
                    btnSave.setEnabled(true);

                    Toast.makeText(getContext(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();

                    etTitle.setText("");
                    etAuthor.setText("");
                    etYear.setText("");
                    etBlurb.setText("");
                    selectedImageUri = null;
                    tvFileName.setText("Belum ada file dipilih");
                });
            });
        });
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri != null && uri.getScheme() != null && uri.getScheme().equals("content")) {
            try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) result = cursor.getString(nameIndex);
                }
            }
        }
        if (result == null && uri != null) {
            result = uri.getPath();
            if (result != null) {
                int cut = result.lastIndexOf('/');
                if (cut != -1) result = result.substring(cut + 1);
            }
        }
        return result != null ? result : "Unknown file";
    }
}