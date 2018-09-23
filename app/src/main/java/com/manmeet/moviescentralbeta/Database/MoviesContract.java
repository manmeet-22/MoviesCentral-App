package com.manmeet.moviescentralbeta.Database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class MoviesContract {
    public static final String AUTHORITY = "com.manmeet.moviescentralbeta.Database";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY;

    public MoviesContract(){}

    public static class MovieProvider implements BaseColumns{
        public static final String TABLE_NAME = "moviesTable";

        public static final String PATH_TABLE = "movies";
        public static final String PATH_TABLE_ID = "movies/#";

        public static final String _ID = BaseColumns._ID;
        public static final String MOVIE_ID = "movieId";
    }
}
