package com.manmeet.moviescentralbeta.Pojos.movie_details;

import com.google.gson.annotations.SerializedName;
import com.manmeet.moviescentralbeta.Pojos.movie_images.Images;

import java.util.List;

/**
 * Created by manmeet on 29/3/18.
 */

public class MovieDetail {
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("genres")
    private List<Genres> genres;
    @SerializedName("status")
    private String status;
    @SerializedName("runtime")
    private String runtime;
    @SerializedName("adult")
    private String adult;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("id")
    private String id;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("images")
    private Images images;
    @SerializedName("vote_count")
    private String voteCount;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("videos")
    private String video;
    @SerializedName("tagline")
    private String tagline;
    @SerializedName("popularity")
    private String popularity;


    public String getVoteAverage()
    {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage)
    {
        this.voteAverage = voteAverage;
    }

    public String getBackdropPath()
    {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath)
    {
        this.backdropPath = backdropPath;
    }

    public List<Genres> getGenres ()
    {
        return genres;
    }

    public void setGenres (List<Genres> genres)
    {
        this.genres = genres;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getRuntime ()
    {
        return runtime;
    }

    public void setRuntime (String runtime)
    {
        this.runtime = runtime;
    }

    public String getAdult ()
    {
        return adult;
    }

    public void setAdult (String adult)
    {
        this.adult = adult;
    }

    public String getHomepage ()
    {
        return homepage;
    }

    public void setHomepage (String homepage)
    {
        this.homepage = homepage;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getOverview ()
    {
        return overview;
    }

    public void setOverview (String overview)
    {
        this.overview = overview;
    }

    public String getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle()
    {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle)
    {
        this.originalTitle = originalTitle;
    }

    public Images getImages ()
    {
        return images;
    }

    public void setImages (Images images)
    {
        this.images = images;
    }

    public String getVoteCount()
    {
        return voteCount;
    }

    public void setVoteCount(String voteCount)
    {
        this.voteCount = voteCount;
    }

    public String getPosterPath()
    {
        return posterPath;
    }

    public void setPosterPath(String posterPath)
    {
        this.posterPath = posterPath;
    }

    public String getVideo ()
    {
        return video;
    }

    public void setVideo (String video)
    {
        this.video = video;
    }

    public String getTagline ()
    {
        return tagline;
    }

    public void setTagline (String tagline)
    {
        this.tagline = tagline;
    }

    public String getPopularity ()
    {
        return popularity;
    }

    public void setPopularity (String popularity)
    {
        this.popularity = popularity;
    }

    @Override
    public String toString()
    {
        return "Pojo-MovieDetails-MovieDetail [voteAverage = "+ voteAverage +", backdropPath = "+ backdropPath +", genres = "+genres+", status = "+status+", runtime = "+runtime+", adult = "+adult+", homepage = "+homepage+", id = "+id+", overview = "+overview+", releaseDate = "+ releaseDate +", originalTitle = "+ originalTitle +", images = "+images+", voteCount = "+ voteCount +", posterPath = "+ posterPath +", video = "+video+", tagline = "+tagline+", popularity = "+popularity+"]";
    }
}
