package com.example.tp3;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String id;
    private String title;
    private String author;
    private int year;
    private String blurb;
    private String imageUri;
    private boolean isLiked;
    private String genre;
    private float rating;

    public Book(String id, String title, String author, int year, String blurb, String imageUri, boolean isLiked, String genre, float rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.imageUri = imageUri;
        this.isLiked = isLiked;
        this.genre = genre;
        this.rating = rating;
    }

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        author = in.readString();
        year = in.readInt();
        blurb = in.readString();
        imageUri = in.readString();
        isLiked = in.readByte() != 0;
        genre = in.readString();
        rating = in.readFloat();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getBlurb() { return blurb; }
    public String getImageUri() { return imageUri; }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }
    public String getGenre() { return genre; }
    public float getRating() { return rating; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeInt(year);
        dest.writeString(blurb);
        dest.writeString(imageUri);
        dest.writeByte((byte) (isLiked ? 1 : 0));
        dest.writeString(genre);
        dest.writeFloat(rating);
    }
}
