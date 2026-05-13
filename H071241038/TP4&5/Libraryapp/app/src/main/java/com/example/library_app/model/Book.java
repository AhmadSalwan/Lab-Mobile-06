package com.example.library_app.model;

import android.net.Uri;

import java.io.Serializable;

public class Book implements Serializable {

    private String title;
    private String author;
    private String year;
    private String blurb;
    private int imageResId;
    private Uri imageUri;
    private boolean liked;
    private String genre;
    private float rating;

    // Constructor drawable
    public Book(String title, String author, String year,
                String blurb, int imageResId,
                boolean liked, String genre, float rating) {

        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.imageResId = imageResId;
        this.liked = liked;
        this.genre = genre;
        this.rating = rating;
    }

    // Constructor URI
    public Book(String title, String author, String year,
                String blurb, Uri imageUri,
                boolean liked, String genre, float rating) {

        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.imageUri = imageUri;
        this.liked = liked;
        this.genre = genre;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public String getBlurb() {
        return blurb;
    }

    public int getImageResId() {
        return imageResId;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getGenre() {
        return genre;
    }

    public float getRating() {
        return rating;
    }
}