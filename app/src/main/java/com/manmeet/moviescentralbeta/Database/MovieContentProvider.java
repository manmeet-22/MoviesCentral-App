package com.manmeet.moviescentralbeta.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieContentProvider extends ContentProvider {

    private MoviesDbHelper moviesDbHelper;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int CODE_MOVIES = 1001;
    public static final int CODE_MOVIE_ITEM = 1002;

    static {
        uriMatcher.addURI(MoviesContract.AUTHORITY,MoviesContract.MovieProvider.PATH_TABLE,CODE_MOVIES);
        uriMatcher.addURI(MoviesContract.AUTHORITY,MoviesContract.MovieProvider.PATH_TABLE_ID,CODE_MOVIE_ITEM);
    }

    @Override
    public boolean onCreate() {
        moviesDbHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase = moviesDbHelper.getReadableDatabase();
        Cursor cursor;
        int code = uriMatcher.match(uri);

        switch (code){
            case CODE_MOVIES:
                cursor = sqLiteDatabase.query(MoviesContract.MovieProvider.TABLE_NAME,
                        projection,selection,selectionArgs,null,null,null,null);
                return cursor;
            case CODE_MOVIE_ITEM :
                selection = MoviesContract.MovieProvider._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(MoviesContract.MovieProvider.TABLE_NAME,
                        projection,selection,selectionArgs,null,null,null,null);
                return cursor;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int code =uriMatcher.match(uri);
        if(code == CODE_MOVIE_ITEM){
            return MoviesContract.CONTENT_ITEM_TYPE;
        } else {
            return MoviesContract.CONTENT_DIR_TYPE;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase sqLiteDatabase = moviesDbHelper.getWritableDatabase();
        long rowId = 0;
        rowId = sqLiteDatabase.insert(MoviesContract.MovieProvider.TABLE_NAME,null,values);
        if (rowId == -1){
            throw new IllegalArgumentException("Failed to insert at uri "+ uri.toString());
        }
        return ContentUris.withAppendedId(MoviesContract.BASE_URI,rowId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = moviesDbHelper.getWritableDatabase();
        int code = uriMatcher.match(uri);
        switch (code){
            case CODE_MOVIES :
                return sqLiteDatabase.delete(MoviesContract.MovieProvider.TABLE_NAME,selection,selectionArgs);
            case CODE_MOVIE_ITEM :
                selection = MoviesContract.MovieProvider._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return sqLiteDatabase.delete(MoviesContract.MovieProvider.TABLE_NAME,selection,selectionArgs);
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = moviesDbHelper.getWritableDatabase();
        int code =uriMatcher.match(uri);
        switch (code){
            case CODE_MOVIES:
                return sqLiteDatabase.update(MoviesContract.MovieProvider.TABLE_NAME,
                        values, selection, selectionArgs);
            case CODE_MOVIE_ITEM:
                selection = MoviesContract.MovieProvider._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return sqLiteDatabase.update(MoviesContract.MovieProvider.TABLE_NAME,
                        values, selection, selectionArgs);
        }
        return 0;
    }
}
