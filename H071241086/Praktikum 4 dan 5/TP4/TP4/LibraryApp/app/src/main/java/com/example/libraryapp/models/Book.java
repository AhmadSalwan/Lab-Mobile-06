package com.example.libraryapp.models;

import android.net.Uri;
import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    private static int idCounter = 1;

    private int id;
    private String title;
    private String author;
    private int year;
    private String blurb;
    private String genre;
    private float rating;
    private String coverUrl; // URL or drawable name for dummy data
    private Uri coverUri;    // For user-added books from gallery
    private boolean liked;
    private List<String> reviews;
    private int pageCount;
    private String language;
    private String publisher;

    // Constructor for dummy data
    public Book(String title, String author, int year, String blurb,
                String genre, float rating, String coverUrl, int pageCount,
                String language, String publisher) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.genre = genre;
        this.rating = rating;
        this.coverUrl = coverUrl;
        this.pageCount = pageCount;
        this.language = language;
        this.publisher = publisher;
        this.liked = false;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getBlurb() { return blurb; }
    public void setBlurb(String blurb) { this.blurb = blurb; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
    public Uri getCoverUri() { return coverUri; }
    public void setCoverUri(Uri coverUri) { this.coverUri = coverUri; }
    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }
    public List<String> getReviews() { return reviews; }
    public void setReviews(List<String> reviews) { this.reviews = reviews; }
    public int getPageCount() { return pageCount; }
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
}
