package com.example.tp2_h071241076;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private String username;
    private String caption;
    private int profileImage;
    private int postImage;
    private String imageUri;

    public Post(String username, String caption, int profileImage, int postImage) {
        this.username = username;
        this.caption = caption;
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.imageUri = null;
    }

    public Post(String username, String caption, int profileImage, String imageUri) {
        this.username = username;
        this.caption = caption;
        this.profileImage = profileImage;
        this.imageUri = imageUri;
        this.postImage = 0;
    }

    protected Post(Parcel in) {
        username = in.readString();
        caption = in.readString();
        profileImage = in.readInt();
        postImage = in.readInt();
        imageUri = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getUsername() { return username; }
    public String getCaption() { return caption; }
    public int getProfileImage() { return profileImage; }
    public int getPostImage() { return postImage; }
    public String getImageUri() { return imageUri; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(caption);
        dest.writeInt(profileImage);
        dest.writeInt(postImage);
        dest.writeString(imageUri);
    }
}