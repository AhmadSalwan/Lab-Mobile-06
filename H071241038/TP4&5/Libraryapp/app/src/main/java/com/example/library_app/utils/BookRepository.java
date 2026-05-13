package com.example.library_app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.example.library_app.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static final String PREF_NAME = "BookPrefs";
    private static final String KEY_BOOK_COUNT = "book_count";
    
    public static List<Book> bookList = new ArrayList<>();

    public static void saveBooks(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        
        // Clear previous data
        editor.clear();
        
        editor.putInt(KEY_BOOK_COUNT, bookList.size());
        
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            editor.putString("book_" + i + "_title", book.getTitle());
            editor.putString("book_" + i + "_author", book.getAuthor());
            editor.putString("book_" + i + "_year", book.getYear());
            editor.putString("book_" + i + "_blurb", book.getBlurb());
            editor.putInt("book_" + i + "_imageResId", book.getImageResId());
            editor.putString("book_" + i + "_imageUri", book.getImageUri() != null ? book.getImageUri().toString() : "");
            editor.putBoolean("book_" + i + "_liked", book.isLiked());
            editor.putString("book_" + i + "_genre", book.getGenre());
            editor.putFloat("book_" + i + "_rating", book.getRating());
        }
        
        editor.apply();
    }

    public static void loadBooks(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int count = prefs.getInt(KEY_BOOK_COUNT, 0);
        
        if (count > 0) {
            bookList.clear();
            for (int i = 0; i < count; i++) {
                String title = prefs.getString("book_" + i + "_title", "");
                String author = prefs.getString("book_" + i + "_author", "");
                String year = prefs.getString("book_" + i + "_year", "");
                String blurb = prefs.getString("book_" + i + "_blurb", "");
                int imageResId = prefs.getInt("book_" + i + "_imageResId", 0);
                String uriString = prefs.getString("book_" + i + "_imageUri", "");
                boolean liked = prefs.getBoolean("book_" + i + "_liked", false);
                String genre = prefs.getString("book_" + i + "_genre", "General");
                float rating = prefs.getFloat("book_" + i + "_rating", 0.0f);
                
                Uri imageUri = null;
                if (!uriString.isEmpty()) {
                    imageUri = Uri.parse(uriString);
                }
                
                Book book;
                if (imageUri != null) {
                    book = new Book(title, author, year, blurb, imageUri, liked, genre, rating);
                } else {
                    book = new Book(title, author, year, blurb, imageResId, liked, genre, rating);
                }
                bookList.add(book);
            }
        }
    }
}