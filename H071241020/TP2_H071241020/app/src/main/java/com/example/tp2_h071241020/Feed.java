package com.example.tp2_h071241020;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Feed implements Parcelable {
    private String username;
    private String fullName;
    private String bio;
    private int profileImage;
    private int postImage;
    private Uri postImageUri;
    private String caption;
    private ArrayList<Highlight> userHighlights;

    public Feed(String username, String fullName, String bio, int profileImage, int postImage, String caption, ArrayList<Highlight> userHighlights) {
        this.username = username;
        this.fullName = fullName;
        this.bio = bio;
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.caption = caption;
        this.userHighlights = userHighlights;
    }

    public Feed(String username, String fullName, String bio, int profileImage, Uri postImageUri, String caption) {
        this.username = username;
        this.fullName = fullName;
        this.bio = bio;
        this.profileImage = profileImage;
        this.postImageUri = postImageUri;
        this.caption = caption;
        this.userHighlights = new ArrayList<>();
    }

    protected Feed(Parcel in) {
        username = in.readString();
        fullName = in.readString();
        bio = in.readString();
        profileImage = in.readInt();
        postImage = in.readInt();
        postImageUri = in.readParcelable(Uri.class.getClassLoader());
        caption = in.readString();
        userHighlights = in.createTypedArrayList(Highlight.CREATOR);
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) { return new Feed(in); }
        @Override
        public Feed[] newArray(int size) { return new Feed[size]; }
    };

    @Override public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(fullName);
        dest.writeString(bio);
        dest.writeInt(profileImage);
        dest.writeInt(postImage);
        dest.writeParcelable(postImageUri, flags);
        dest.writeString(caption);
        dest.writeTypedList(userHighlights);
    }

    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getBio() { return bio; }
    public int getProfileImage() { return profileImage; }
    public int getPostImage() { return postImage; }
    public Uri getPostImageUri() { return postImageUri; }
    public String getCaption() { return caption; }
    public ArrayList<Highlight> getUserHighlights() { return userHighlights; }
}