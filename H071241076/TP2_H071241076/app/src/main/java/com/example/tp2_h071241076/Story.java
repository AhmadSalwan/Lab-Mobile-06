package com.example.tp2_h071241076;

import android.os.Parcel;
import android.os.Parcelable;

public class Story implements Parcelable {
    private String title;
    private int image;

    public Story(String title, int image) {
        this.title = title;
        this.image = image;
    }

    protected Story(Parcel in) {
        title = in.readString();
        image = in.readInt();
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(image);
    }
}