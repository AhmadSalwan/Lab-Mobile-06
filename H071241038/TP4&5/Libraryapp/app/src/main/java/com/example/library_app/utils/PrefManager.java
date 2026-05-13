package com.example.library_app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static final String PREF_NAME = "LibraryPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DARK_MODE = "isDarkMode";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Fungsi menyimpan data login
    public void setLogin(boolean isLoggedIn, String username, String password) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "Guest");
    }

    // Fungsi Logout (untuk menghapus data)
    public void logout() {
        editor.clear();
        editor.apply();
    }

    // Fungsi untuk menyimpan pilihan Tema Gelap/Terang
    public void setDarkMode(boolean isDark) {
        editor.putBoolean(KEY_DARK_MODE, isDark);
        editor.apply();
    }

    // Fungsi untuk mengecek status Tema saat ini
    public boolean isDarkMode() {
        return pref.getBoolean(KEY_DARK_MODE, false); // Default-nya Terang (false)
    }

}