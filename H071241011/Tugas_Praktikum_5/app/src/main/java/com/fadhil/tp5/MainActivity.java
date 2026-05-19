package com.fadhil.tp5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {

    private SharedPreferencesManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefsManager = new SharedPreferencesManager(this);

        TextView tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);
        TextView tvUserName       = findViewById(R.id.tvUserName);
        TextView tvSkinColorLabel = findViewById(R.id.tvSkinColorLabel);
        SwitchCompat switchSkin   = findViewById(R.id.switchSkinColor);
        EditText etNote           = findViewById(R.id.etNote);
        Button btnSaveNote        = findViewById(R.id.btnSaveNote);
        LinearLayout btnLogout    = findViewById(R.id.btnLogout);

        ImageView img1 = findViewById(R.id.imgPerson1);
        ImageView img2 = findViewById(R.id.imgPerson2);
        ImageView img3 = findViewById(R.id.imgPerson3);
        ImageView img4 = findViewById(R.id.imgPerson4);
        ImageView img5 = findViewById(R.id.imgPerson5);
        ImageView img6 = findViewById(R.id.imgPerson6);

        String name      = prefsManager.getName();
        String skinColor = prefsManager.getSkinColor();
        String savedNote = prefsManager.getNote();

        tvUserName.setText(name);
        etNote.setText(savedNote);

        applyUI(skinColor, tvWelcomeMessage, tvSkinColorLabel, switchSkin,
                img1, img2, img3, img4, img5, img6);

        switchSkin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String newSkin = isChecked ? "putih" : "hitam";
            prefsManager.setSkinColor(newSkin);

            if ("hitam".equals(newSkin)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        btnSaveNote.setOnClickListener(v -> {
            // Perubahan 2: Support Save Note
            String note = etNote.getText().toString();
            prefsManager.saveNote(note);
            Toast.makeText(this, "Catatan tersimpan!", Toast.LENGTH_SHORT).show();
        });

        btnLogout.setOnClickListener(v -> {
            prefsManager.logout();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void applyUI(String skinColor, TextView tvWelcome, TextView tvLabel,
                         SwitchCompat switchSkin,
                         ImageView img1, ImageView img2, ImageView img3,
                         ImageView img4, ImageView img5, ImageView img6) {

        boolean isHitam = "hitam".equals(skinColor);
        switchSkin.setChecked(!isHitam);

        if (isHitam) {
            tvWelcome.setText("Welcome, usual suspect");
            tvLabel.setText("Hitam");
            img1.setImageResource(getResId("hitam_1"));
            img2.setImageResource(getResId("hitam_2"));
            img3.setImageResource(getResId("hitam_3"));
            img4.setImageResource(getResId("hitam_4"));
            img5.setImageResource(getResId("hitam_5"));
            img6.setImageResource(getResId("hitam_6"));
        } else {
            tvWelcome.setText("Welcome, good citizen");
            tvLabel.setText("Putih");
            img1.setImageResource(getResId("putih_1"));
            img2.setImageResource(getResId("putih_2"));
            img3.setImageResource(getResId("putih_3"));
            img4.setImageResource(getResId("putih_4"));
            img5.setImageResource(getResId("putih_5"));
            img6.setImageResource(getResId("putih_6"));
        }
    }

    private int getResId(String name) {
        int resId = getResources().getIdentifier(name, "drawable", getPackageName());
        return resId != 0 ? resId : R.drawable.bg_photo_placeholder;
    }
}