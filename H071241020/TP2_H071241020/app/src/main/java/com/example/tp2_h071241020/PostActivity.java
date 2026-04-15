package com.example.tp2_h071241020;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {
    private Uri selectedUri;
    private ImageView ivPreview;

    private final ActivityResultLauncher<Intent> picker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            res -> {
                if (res.getResultCode() == RESULT_OK && res.getData() != null) {
                    selectedUri = res.getData().getData();
                    if (selectedUri != null) {
                        try {
                            final int takeFlags = res.getData().getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
                            getContentResolver().takePersistableUriPermission(selectedUri, takeFlags);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                        ivPreview.setImageURI(selectedUri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        ivPreview = findViewById(R.id.iv_preview);
        EditText etCap = findViewById(R.id.et_caption);

        findViewById(R.id.btn_choose_image).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            picker.launch(intent);
        });

        findViewById(R.id.btn_upload).setOnClickListener(v -> {
            if (selectedUri != null) {
                Feed newFeed = new Feed(
                        "agungallokraeng",
                        "Agung Allo K.",
                        "Sistem Informasi 24\nAir Putih",
                        R.drawable.ppa1,
                        selectedUri,
                        etCap.getText().toString()
                );

                DataSource.myProfileFeeds.add(0, newFeed);

                Toast.makeText(this, "Berhasil diupload ke Profil Anda!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Silakan pilih foto terlebih dahulu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}