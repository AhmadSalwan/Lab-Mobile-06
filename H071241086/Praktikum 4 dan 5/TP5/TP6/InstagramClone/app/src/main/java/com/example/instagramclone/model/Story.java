package com.example.instagramclone.model;

public class Story {
    private String id;
    private String title;
    private int coverResId;
    private String username;

    public Story(String id, String title, int coverResId, String username) {
        this.id = id;
        this.title = title;
        this.coverResId = coverResId;
        this.username = username;
    }

    public String getId()        { return id; }
    public String getTitle()     { return title; }
    public int getCoverResId()   { return coverResId; }
    public String getUsername()  { return username; }
}
