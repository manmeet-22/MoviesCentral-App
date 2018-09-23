package com.manmeet.moviescentralbeta.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.manmeet.moviescentralbeta.Database.MoviesContract;
public class MoviesDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "moviesDatabase.db";
    public static final int DATABASE_VERSION = 1;
    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String MOVIE_CREATE_TABLE_QUERY = "CREATE TABLE " + MoviesContract.MovieProvider.TABLE_NAME + "( " +
                MoviesContract.MovieProvider._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                MoviesContract.MovieProvider.MOVIE_ID + " INTEGER NOT NULL);";
        Log.i("MovieDbHelper", MOVIE_CREATE_TABLE_QUERY);
        db.execSQL(MOVIE_CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
