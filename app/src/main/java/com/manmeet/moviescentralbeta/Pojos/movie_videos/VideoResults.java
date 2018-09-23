package com.manmeet.moviescentralbeta.Pojos.movie_videos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manmeet on 29/3/18.
 */

public class VideoResults {

    @SerializedName("id")
    private String id;
    @SerializedName("key")
    private String key;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getKey ()
    {
        return key;
    }

    public void setKey (String key)
    {
        this.key = key;
    }


    @Override
    public String toString()
    {
        return "Pojo-MovieVideo-VideoResults [ id = "+id+ ", key = "+key+"]";
    }
}