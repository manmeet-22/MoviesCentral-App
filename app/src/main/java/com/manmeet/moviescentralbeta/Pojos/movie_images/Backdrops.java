package com.manmeet.moviescentralbeta.Pojos.movie_images;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manmeet on 29/3/18.
 */

public class Backdrops {

    @SerializedName("file_path")
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Pojo-MovieImages-Backdrops [ filePath = " + filePath + "]";
    }
}
