package com.manmeet.moviescentralbeta.Retrofit;

import com.manmeet.moviescentralbeta.Pojos.movie.DiscoverMovies;
import com.manmeet.moviescentralbeta.Pojos.movie_details.Credits;
import com.manmeet.moviescentralbeta.Pojos.movie_details.MovieDetail;
import com.manmeet.moviescentralbeta.Pojos.movie_details.Reviews;
import com.manmeet.moviescentralbeta.Pojos.movie_videos.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by manmeet on 29/3/18.
 */

/**
 * Interface where all the GET and POST methods are defined which will be called to get the serialized JSON response.
 */
public interface ApiInterface {

    /**
     * GET method for list of movies.
     * @param sort : Define the type of movies needed. Either popular or top_rated.
     * @param key : Pass your api key.
     * @return : JSON response serialized into pojo.
     */

    @GET("3/movie/{movie}")
    Call<DiscoverMovies> getMovies(@Path("movie") String sort,
                                   @Query("api_key") String key);


    /**
     * GET method for details of a particular movie.
     * @param id : Id of the movie whose details are needed.
     * @param key : Your api key.
     * @param append : Any extra information you want to append in the response.
     * @return
     */

    @GET("3/movie/{movie_id}")
    Call<MovieDetail> getMovieDetails(@Path("movie_id") String id,
                                      @Query("api_key") String key,
                                      @Query("append_to_response") String append);

    /**
     * GET method to receive videos available for a particular movie.
     * @param id : Id of the movie whose videos are required.
     * @param key : Your api key.
     * @return
     */

    @GET("3/movie/{movie_id}/videos")
    Call<Videos> getVideos(@Path("movie_id") String id,
                           @Query("api_key") String key);


    /**
     * GET method for getting the Credits information of a particular movie such as cast and crew.
     * @param id : Id of the movie whose credits information is needed.
     * @param key : Your api key.
     * @return
     */

    @GET("3/movie/{movie_id}/credits")
    Call<Credits> getMovieCredits(@Path("movie_id") String id,
                                  @Query("api_key") String key);



    /**
     * Get user reviews for a particular tv show / movie.
     * @param id : id of tv / movie
     * @param key : Your api-key
     * @return
     */
    @GET("3/movie/{movie_id}/reviews")
    Call<Reviews> getReviews(@Path("movie_id") String id,
                             @Query("api_key") String key);

}
