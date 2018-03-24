package com.manmeet.moviescentralbeta.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by Manmeet on 24-Oct-17.
 */

public class NetworkHelper {

    private static String BASE_URL_KEY = "https://api.themoviedb.org/3/discover/movie?api_key=27ce7b8fb2e60db18a43f0bf31f202c6";
    private static String BASE_THUMB_URL = "http://image.tmdb.org/t/p/";
    private static String THUMB_SIZE = "w342";
    private static String PARAM_LANGUAGE = "language";
    private static String PARAM_REGION = "region";
    private static String PARAM_SORT = "sort_by";
    private static String PARAM_RELEASE = "primary_release_year";
    private static String year = "2017";

    public static URL buildURL(Boolean sortByRating) {

        URL url = null;
        Uri uri;
        Uri.Builder builder = new Uri.Builder();
        builder = Uri.parse(BASE_URL_KEY).buildUpon()
                .appendQueryParameter(PARAM_LANGUAGE, "en-US")
                .appendQueryParameter(PARAM_REGION, "US")
                .appendQueryParameter(PARAM_RELEASE, year);

        if (sortByRating)
            builder.appendQueryParameter(PARAM_SORT, "popular");
        else
            builder.appendQueryParameter(PARAM_SORT, "top_rated");
        uri = builder.build();
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String buildImageURL(String end_path) {
        String url;
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_THUMB_URL)
                .append(THUMB_SIZE)
                .append(end_path);
        url = builder.toString();
        Log.i("URL", url);
        return url;
    }

    public static String getHTTPData(URL url) throws IOException {
        HttpURLConnection httpURLConnection = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "getHTTPData: IO EXCEPTION");
            return null;
        }
        if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            Log.e(TAG, "getHTTPData: BAD HTTP CONNECTION");
            return null;
        }

        InputStream is = null;
        try {
            assert httpURLConnection != null;
            is = httpURLConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(is);
        scanner.useDelimiter("\\A");
        if (scanner.hasNext()) {
            return scanner.next();
        } else
            return null;
    }

}
