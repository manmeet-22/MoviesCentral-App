package com.manmeet.moviescentralbeta.Pojos.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manmeet on 29/3/18.
 */

public class DiscoverMoviesResults implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    public DiscoverMoviesResults(String id, String image){
        this.id = id;
        posterPath = image;
    }

    protected DiscoverMoviesResults(Parcel in) {
        id = in.readString();
        posterPath = in.readString();
    }

    public static final Creator<DiscoverMoviesResults> CREATOR = new Creator<DiscoverMoviesResults>() {
        @Override
        public DiscoverMoviesResults createFromParcel(Parcel in) {
            return new DiscoverMoviesResults(in);
        }

        @Override
        public DiscoverMoviesResults[] newArray(int size) {
            return new DiscoverMoviesResults[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return "Pojo-Movie-DiscoverMoviesResults [id = " + id + ", title = " + title + ", posterPath = " + posterPath + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(posterPath);
     }
}