package com.example.tp2_h071241020;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Highlight implements Parcelable {
    private String title;
    private int image;

    public Highlight(String title, int image) {
        this.title = title;
        this.image = image;
    }

    protected Highlight(Parcel in) {
        title = in.readString();
        image = in.readInt();
    }

    public static final Creator<Highlight> CREATOR = new Creator<Highlight>() {
        @Override
        public Highlight createFromParcel(Parcel in) {
            return new Highlight(in);
        }

        @Override
        public Highlight[] newArray(int size) {
            return new Highlight[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(image);
    }

    public String getTitle() { return title; }
    public int getImage() { return image; }
}