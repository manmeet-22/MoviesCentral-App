package com.manmeet.moviescentralbeta.Pojos.movie_details;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manmeet on 29/3/18.
 */

public class Genres {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pojo-MovieDetails-Genres [id = " + id + ", name = " + name + "]";
    }
}