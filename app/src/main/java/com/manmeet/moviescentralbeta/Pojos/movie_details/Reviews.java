package com.manmeet.moviescentralbeta.Pojos.movie_details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by manmeet on 29/3/18.
 */

public class Reviews {

    @SerializedName("id")
    private String id;
    @SerializedName("results")
    private List<ReviewResults> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ReviewResults> getResults() {
        return results;
    }

    public void setResults(List<ReviewResults> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Pojo-MovieDetails-Review [id = " + id + ", results = " + results + "]";
    }
}
