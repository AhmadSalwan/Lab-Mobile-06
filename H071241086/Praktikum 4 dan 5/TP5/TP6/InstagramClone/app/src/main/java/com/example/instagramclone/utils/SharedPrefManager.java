package com.example.instagramclone.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.instagramclone.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SharedPrefManager {
    private static final String PREF_NAME = "InstagramClonePrefs";
    private static final String KEY_USERS = "all_users";
    private static final String KEY_CURRENT_USER = "current_user_name";
    private static final String KEY_DARK_MODE = "dark_mode";
    
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public void saveUser(User user) {
        Map<String, User> users = getAllUsers();
        users.put(user.getUsername(), user);
        String json = gson.toJson(users);
        editor.putString(KEY_USERS, json);
        editor.apply();
    }

    public Map<String, User> getAllUsers() {
        String json = sharedPreferences.getString(KEY_USERS, null);
        if (json == null) return new HashMap<>();
        Type type = new TypeToken<HashMap<String, User>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public User getUser(String username) {
        return getAllUsers().get(username);
    }

    public void setCurrentUser(String username) {
        editor.putString(KEY_CURRENT_USER, username);
        editor.apply();
    }

    public String getCurrentUsername() {
        return sharedPreferences.getString(KEY_CURRENT_USER, null);
    }

    public void setDarkMode(boolean isEnabled) {
        editor.putBoolean(KEY_DARK_MODE, isEnabled);
        editor.apply();
    }

    public boolean isDarkMode() {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false);
    }

    public void logout() {
        editor.remove(KEY_CURRENT_USER);
        editor.apply();
    }
}
