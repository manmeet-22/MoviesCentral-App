package com.manmeet.moviescentralbeta.Pojos.movie_videos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by manmeet on 29/3/18.
 */

public class Videos {

    @SerializedName("id")
    private String id;
    @SerializedName("results")
    private List<VideoResults> results;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public List<VideoResults> getResults ()
    {
        return results;
    }

    public void setResults (List<VideoResults> results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "Pojo-MovieVideo-Videos [id = "+id+", results = "+results+"]";
    }
}