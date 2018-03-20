package com.manmeet.moviescentralbeta.utilities;

import android.util.Log;

import com.manmeet.moviescentralbeta.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Manmeet on 24-Oct-17.
 */

public class JSONHelper {


    public static ArrayList<Movie> getArrayListFromJSON(String jsonString) throws JSONException {
        ArrayList<Movie> list = new ArrayList<>();
        JSONObject baseObject = new JSONObject(jsonString);
        JSONArray resultsArray = null;
        if (baseObject.has("results")) {
            resultsArray = baseObject.getJSONArray("results");
            //Log.d(TAG, "getArrayListFromJSON: " + resultsArray.toString());
        } else {
            Log.d(TAG, "getArrayListFromJSON: No Result");
            return null;
        }
        for (int i = 0; i < resultsArray.length(); i++) {
            String mTitle;
            String mPlot;
            String mRating;
            String mReleaseDate;
            String mThumbURL = "https://cache-graphicslib.viator.com/graphicslib/page-images/360x240/142106_no-photo.jpg";
            JSONObject currentMovie = resultsArray.getJSONObject(i);
            mTitle = currentMovie.getString("title");
            mPlot = currentMovie.getString("overview");
            mRating = currentMovie.getString("vote_average");
            mReleaseDate = currentMovie.getString("release_date");
            if (currentMovie.has("poster_path"))
                mThumbURL = currentMovie.getString("poster_path");
            Movie movieObject = new Movie(mTitle, mPlot, mThumbURL, mRating, mReleaseDate);
            list.add(movieObject);
        }
        return list;
    }

}
