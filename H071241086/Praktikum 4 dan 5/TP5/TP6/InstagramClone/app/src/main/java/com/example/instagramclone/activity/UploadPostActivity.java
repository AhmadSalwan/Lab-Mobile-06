package com.example.instagramclone.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.instagramclone.R;
import com.example.instagramclone.model.DataDummy;
import com.example.instagramclone.model.Post;
import com.example.instagramclone.model.User;
import com.example.instagramclone.utils.SharedPrefManager;

public class UploadPostActivity extends AppCompatActivity {

    private ImageView ivPreview;
    private EditText etCaption;
    private Uri selectedImageUri;
    private SharedPrefManager prefManager;

    private final ActivityResultLauncher<Intent> picker = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                selectedImageUri = result.getData().getData();
                ivPreview.setImageURI(selectedImageUri);
                try {
                    getContentResolver().takePersistableUriPermission(selectedImageUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } catch (Exception e) { e.printStackTrace(); }
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        prefManager = new SharedPrefManager(this);
        // Terapkan tema
        if (prefManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_upload_post);

        ivPreview = findViewById(R.id.iv_preview);
        etCaption = findViewById(R.id.et_caption);
        Button btnPick = findViewById(R.id.btn_pick_image);
        Button btnShare = findViewById(R.id.btn_share);
        findViewById(R.id.btn_close).setOnClickListener(v -> finish());

        btnPick.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            picker.launch(intent);
        });

        btnShare.setOnClickListener(v -> {
            if (selectedImageUri == null || etCaption.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Gambar dan caption harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            User currentUser = DataDummy.getInstance(this).getCurrentUser();
            Post newPost = new Post("up_" + System.currentTimeMillis(), currentUser.getUsername(),
                    currentUser.getProfileImageRes(), selectedImageUri.toString(),
                    etCaption.getText().toString().trim(), 0, 0, "Baru saja");
            
            DataDummy.getInstance(this).addProfilePost(newPost);
            finish();
        });
    }
}
