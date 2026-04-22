package com.example.tp3_h071241020.models;

public class Book {
    private String id;
    private String title;
    private String author;
    private String year;
    private String blurb;
    private String genre;
    private String edition;
    private String publisher;
    private float rating;
    private int coverResId;
    private String coverUri;
    private boolean isLiked;

    public Book(String id, String title, String author, String year, String blurb, String genre, String edition, String publisher, float rating, int coverResId, String coverUri) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.genre = genre;
        this.edition = edition;
        this.publisher = publisher;
        this.rating = rating;
        this.coverResId = coverResId;
        this.coverUri = coverUri;
        this.isLiked = false;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    public String getBlurb() { return blurb; }
    public void setBlurb(String blurb) { this.blurb = blurb; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getEdition() { return edition; }
    public void setEdition(String edition) { this.edition = edition; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public int getCoverResId() { return coverResId; }
    public String getCoverUri() { return coverUri; }
    public void setCoverUri(String coverUri) { this.coverUri = coverUri; }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }
}