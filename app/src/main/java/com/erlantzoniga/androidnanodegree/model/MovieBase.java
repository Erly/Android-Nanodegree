package com.erlantzoniga.androidnanodegree.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Erlantz on 13/07/2015.
 */
public class MovieBase implements Parcelable {
    public int id;
    public String title, synopsis;
    public Uri posterUri;
    public double rating;
    public Date releaseDate;

    public MovieBase(int id, String title, Uri posterUri, String synopsis, double rating, Date releaseDate) {
        this.id = id;
        this.title = title;
        this.posterUri = posterUri;
        this.rating = rating;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
    }

    public MovieBase(Parcel in) {
        this.id = in.readInt();
        this.rating = in.readDouble();
        this.releaseDate = new Date(in.readLong());
        this.title = in.readString();
        this.synopsis = in.readString();
        this.posterUri = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(rating);
        dest.writeLong(releaseDate.getTime());
        dest.writeString(title);
        dest.writeString(synopsis);
        dest.writeParcelable(posterUri, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MovieBase createFromParcel(Parcel in) {
            return new MovieBase(in);
        }

        public MovieBase[] newArray(int size) {
            return new MovieBase[size];
        }
    };
}
