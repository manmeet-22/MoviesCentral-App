package com.manmeet.moviescentralbeta;

import com.manmeet.moviescentralbeta.utilities.NetworkHelper;

import java.io.Serializable;

/**
 * Created by Manmeet on 24-Oct-17.
 */

public class Movie implements Serializable {
    private String mTitle;
    private String mPlot;
    private String mImageURL;
    private String mRating;
    private String mReleaseDate;

    public Movie(String title, String plot, String imageURL, String rating, String releaseDate) {
        this.mTitle = title;
        this.mPlot = plot;
        this.mImageURL = imageURL;
        this.mRating = rating;
        this.mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPlot() {
        return mPlot;
    }

    public void setPlot(String mPlot) {
        this.mPlot = mPlot;
    }

    public String getImageURL() {
        if(mImageURL.contains("null"))
            return null;
        return NetworkHelper.buildImageURL(mImageURL);

    }

    public void setImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String mrating) {
        this.mRating = mrating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }
}
