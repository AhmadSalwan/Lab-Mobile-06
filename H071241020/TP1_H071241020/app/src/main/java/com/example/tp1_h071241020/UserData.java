package com.example.tp1_h071241020;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {
    private String nama;
    private String username;
    private String bio;
    private Uri photoUri;

    public UserData(String nama, String username, String bio, Uri photoUri) {
        this.nama = nama;
        this.username = username;
        this.bio = bio;
        this.photoUri = photoUri;
    }

    protected UserData(Parcel in) {
        nama = in.readString();
        username = in.readString();
        bio = in.readString();
        photoUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) { return new UserData(in); }
        @Override
        public UserData[] newArray(int size) { return new UserData[size]; }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(username);
        dest.writeString(bio);
        dest.writeParcelable(photoUri, flags);
    }

    @Override
    public int describeContents() { return 0; }

    public String getNama() { return nama; }
    public String getUsername() { return username; }
    public String getBio() { return bio; }
    public Uri getPhotoUri() { return photoUri; }
}