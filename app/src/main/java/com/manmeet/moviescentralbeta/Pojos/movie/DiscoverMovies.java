package com.manmeet.moviescentralbeta.Pojos.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by manmeet on 29/3/18.
 */

public class DiscoverMovies {

    @SerializedName("results")
    private List<DiscoverMoviesResults> results;


    public List<DiscoverMoviesResults> getResults() {
        return results;
    }

    public void setResults(List<DiscoverMoviesResults> results) {
        this.results = results;
    }


    @Override
    public String toString() {
        return "Pojo-Movie-DiscoverMovies [results = " + results +"]";
    }
}