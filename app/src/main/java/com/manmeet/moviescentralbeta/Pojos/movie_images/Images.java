package com.manmeet.moviescentralbeta.Pojos.movie_images;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by manmeet on 29/3/18.
 */
public class Images {
    @SerializedName("backdrops")
    private List<Backdrops> backdrops;

    public List<Backdrops> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Backdrops> backdrops) {
        this.backdrops = backdrops;
    }

    @Override
    public String toString() {
        return "Pojo-MovieImages-Images [backdrops = " + backdrops + "]";
    }
}