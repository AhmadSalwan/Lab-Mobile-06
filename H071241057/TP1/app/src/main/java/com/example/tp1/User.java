package com.example.tp1;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {
    private String nama;
    private String email;
    private String bio;
    private Uri imageUri;

    public User(String nama, String email, String bio, Uri imageUri) {
        this.nama = nama;
        this.email = email;
        this.bio = bio;
        this.imageUri = imageUri;
    }

    protected User(Parcel in) {
        nama = in.readString();
        email = in.readString();
        bio = in.readString();
        imageUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getNama() { return nama; }
    public String getEmail() { return email; }
    public String getBio() { return bio; }
    public Uri getImageUri() { return imageUri; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(email);
        dest.writeString(bio);
        dest.writeParcelable(imageUri, flags);
    }
}