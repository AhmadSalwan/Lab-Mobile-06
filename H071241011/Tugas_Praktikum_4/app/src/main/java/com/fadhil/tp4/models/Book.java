package com.fadhil.tp4.models;

import java.io.Serializable;

public class Book implements Serializable {
    private String id;
    private String title;
    private String author;
    private int year;
    private String blurb;
    private String coverImageUri;
    private int coverImageRes;
    private boolean isLiked;
    
    // Bonus properties
    private String genre;
    private float rating;

    public Book(String id, String title, String author, int year, String blurb, int coverImageRes, String genre, float rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.coverImageRes = coverImageRes;
        this.genre = genre;
        this.rating = rating;
        this.isLiked = false;
        this.coverImageUri = null;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getBlurb() { return blurb; }
    public String getCoverImageUri() { return coverImageUri; }
    public void setCoverImageUri(String coverImageUri) { this.coverImageUri = coverImageUri; }
    public int getCoverImageRes() { return coverImageRes; }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }
    public String getGenre() { return genre; }
    public float getRating() { return rating; }
}
