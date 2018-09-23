package com.manmeet.moviescentralbeta.Pojos.movie_details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by manmeet on 29/3/18.
 */

public class Credits {

    @SerializedName("cast")
    private List<Cast> cast;

    @SerializedName("id")
    private String id;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public List<Cast> getCast () {
        return cast;
    }

    public void setCast (List<Cast> cast) {
        this.cast = cast;
    }

    @Override
    public String toString()
    {
        return "Pojo-MovieDetails-Credits [id = "+id+", cast = "+cast+"]";
    }
}
