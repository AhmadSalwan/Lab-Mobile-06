package com.fadhil.tp5;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "tp5_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_LOGGED_IN_NIM = "logged_in_nim";
    private static final String KEY_REGISTERED_NIMS = "registered_nims";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean isNimRegistered(String nim) {
        Set<String> nims = sharedPreferences.getStringSet(KEY_REGISTERED_NIMS, new HashSet<>());
        return nims.contains(nim);
    }

    public void saveUser(String name, String nim, String password, String skinColor) {
        // Perubahan 1: Support Multi User
        Set<String> nims = new HashSet<>(sharedPreferences.getStringSet(KEY_REGISTERED_NIMS, new HashSet<>()));
        nims.add(nim);
        editor.putStringSet(KEY_REGISTERED_NIMS, nims);
        editor.putString(nim + "_name", name);
        editor.putString(nim + "_password", password);
        editor.putString(nim + "_skin_color", skinColor);
        editor.apply();
    }

    public boolean login(String nim, String password) {
        if (!isNimRegistered(nim)) return false;
        String savedPass = sharedPreferences.getString(nim + "_password", "");
        if (savedPass.equals(password)) {
            editor.putBoolean(KEY_IS_LOGGED_IN, true);
            editor.putString(KEY_LOGGED_IN_NIM, nim);
            editor.apply();
            return true;
        }
        return false;
    }

    public void logout() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.putString(KEY_LOGGED_IN_NIM, "");
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getLoggedInNim() {
        return sharedPreferences.getString(KEY_LOGGED_IN_NIM, "");
    }

    public String getName() {
        String nim = getLoggedInNim();
        return sharedPreferences.getString(nim + "_name", "Mahasiswa");
    }

    public String getNim() {
        return getLoggedInNim();
    }

    public void setSkinColor(String skinColor) {
        String nim = getLoggedInNim();
        editor.putString(nim + "_skin_color", skinColor);
        editor.apply();
    }

    public String getSkinColor() {
        String nim = getLoggedInNim();
        return sharedPreferences.getString(nim + "_skin_color", "hitam");
    }

    public boolean isDarkMode() {
        return "hitam".equals(getSkinColor());
    }

    public void saveNote(String note) {
        // Perubahan 2: Support Save Note
        String nim = getLoggedInNim();
        editor.putString(nim + "_note", note);
        editor.apply();
    }

    public String getNote() {
        String nim = getLoggedInNim();
        return sharedPreferences.getString(nim + "_note", "");
    }
}
