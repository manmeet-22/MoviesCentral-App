package com.manmeet.moviescentralbeta;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.manmeet.moviescentralbeta.Adapters.MovieAdapter;
import com.manmeet.moviescentralbeta.Database.MoviesContract;
import com.manmeet.moviescentralbeta.Pojos.movie.DiscoverMovies;
import com.manmeet.moviescentralbeta.Pojos.movie.DiscoverMoviesResults;
import com.manmeet.moviescentralbeta.Pojos.movie_details.MovieDetail;
import com.manmeet.moviescentralbeta.Retrofit.ApiInterface;
import com.manmeet.moviescentralbeta.Retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.manmeet.moviescentralbeta.ApiUtility.API_KEY;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";
    static final String SORT_TOP_RATED = "top_rated";
    static final String SORT_POPULARITY = "popular";
    private Toast mToast;
    private int mNoOfColumns;
    private int visibleItemIndex = 0;
    private MovieAdapter mMovieAdapter;
    private GridLayoutManager mLayoutManager;
    private List<DiscoverMoviesResults> movies;
    private RecyclerView mRecyclerView;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private String movieType;


    /*
    @BindView(R.id.main_recyclerView_movies) RecyclerView mRecyclerView;
    @BindView(R.id.error_message) TextView mErrorMessage;
    @BindView(R.id.loading) ProgressBar mProgressBar;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.main_recyclerView_movies);
        mErrorMessage = findViewById(R.id.error_message);
        mProgressBar = findViewById(R.id.loading);
        movies = new ArrayList<>();
        movieType = SORT_POPULARITY;
        //Stetho for DB testing
        Stetho.initializeWithDefaults(this);

        // Check for network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetorkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetorkInfo != null && activeNetorkInfo.isConnectedOrConnecting()) {
            mErrorMessage.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mErrorMessage.setVisibility(View.VISIBLE);
        }
        mProgressBar.setVisibility(View.GONE);
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new GridLayoutManager(this, 2);
        } else {
            mLayoutManager = new GridLayoutManager(this, 3);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate: savedInstanceState != null");

            movies = savedInstanceState.getParcelableArrayList("movies_list");
            movieType = savedInstanceState.getString("movie_type");
            mMovieAdapter = new MovieAdapter(this, movies);
            mRecyclerView.setAdapter(mMovieAdapter);
            setTitle(savedInstanceState.getString("title"));
            visibleItemIndex = savedInstanceState.getInt("index");
            Log.d(TAG, "onCreate: visibleItemIndex " + String.valueOf(visibleItemIndex));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.scrollToPosition(visibleItemIndex);
                    mLayoutManager.scrollToPosition(visibleItemIndex);
                }
            }, 500);
            mProgressBar.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "onCreate: savedInstanceState == null");
            mMovieAdapter = new MovieAdapter(this, movies);
            mRecyclerView.setAdapter(mMovieAdapter);
            getMovies(movieType);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.recent_main_menu_item:
                setTitle("Popular Movies");
                getMovies(SORT_POPULARITY);
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(MainActivity.this, "Sorting By Popularity", Toast.LENGTH_SHORT);
                mToast.show();
                break;
            case R.id.rating_main_menu_item:
                setTitle("Top Rated Movies");
                getMovies(SORT_TOP_RATED);
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(MainActivity.this, "Sorting By Rating", Toast.LENGTH_SHORT);
                mToast.show();
                break;
            case R.id.favourite_main_menu_item:
                String[] projections = new String[]{MoviesContract.MovieProvider._ID, MoviesContract.MovieProvider.MOVIE_ID};
                Cursor cursor = getContentResolver().query(
                        Uri.withAppendedPath(MoviesContract.BASE_URI, MoviesContract.MovieProvider.PATH_TABLE),
                        projections, null, null, null);
                cursor.moveToFirst();
                ArrayList<String> movieIds = new ArrayList<String>();
                for (int i = 0; i < cursor.getCount(); i++) {
                    String movieId = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieProvider.MOVIE_ID));
                    cursor.moveToNext();
                    movieIds.add(movieId);
                }
                cursor.close();
                getFavMovies(movieIds);
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(MainActivity.this, "Showing Favourites", Toast.LENGTH_SHORT);
                mToast.show();
                mRecyclerView.setAdapter(mMovieAdapter);
                mMovieAdapter.notifyDataSetChanged();

                break;
        }
        return true;
    }

    private void getFavMovies(ArrayList<String> movieIds) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<MovieDetail> call;
        movies.clear();
        for (int i = 0; i < movieIds.size(); i++) {
            call = apiInterface.getMovieDetails(movieIds.get(i), API_KEY, "images");
            call.enqueue(new Callback<MovieDetail>() {
                @Override
                public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                    if (response != null && response.body() != null) {
                        MovieDetail movieDetail = response.body();
                        String id = movieDetail.getId();
                        String posterPath = movieDetail.getPosterPath();
                        DiscoverMoviesResults results = new DiscoverMoviesResults(id, posterPath);
                        movies.add(results);
                        mMovieAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onFailure(Call<MovieDetail> call, Throwable t) {
                    Log.d(TAG, "onFailure: Failed to fetch Favourites");
                    mProgressBar.setVisibility(View.GONE);
                    mErrorMessage.setText(R.string.error_message);
                }

            });
            if (i == movieIds.size() - 1) {
                mMovieAdapter.notifyDataSetChanged();
                Log.d(TAG, "getFavMovies: Adapter Updated");
            }
        }
        if (movies.size() > 0) {
            Log.d(TAG, "getFavMovies: size : " + movies.size());
            mProgressBar.setVisibility(View.GONE);
            mErrorMessage.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setAdapter(mMovieAdapter);
            mMovieAdapter.notifyDataSetChanged();
        }
    }

    public void getMovies(String sort_by) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<DiscoverMovies> call = apiInterface.getMovies(sort_by, API_KEY);
        call.enqueue(new Callback<DiscoverMovies>() {
            @Override
            public void onResponse(Call<DiscoverMovies> call, retrofit2.Response<DiscoverMovies> response) {
                movies.clear();
                DiscoverMovies discoverMovies = response.body();
                movies.addAll(discoverMovies.getResults());
                for (int i = 0; i < movies.size(); i++) {
                    if (movies.get(i).getPosterPath() == null || movies.get(i).getPosterPath().contains("null")
                            || movies.get(i).equals("null"))
                        movies.remove(i);
                }
                mRecyclerView.setAdapter(mMovieAdapter);
                mRecyclerView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mErrorMessage.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DiscoverMovies> call, Throwable t) {
                Log.v(TAG, "onFailure()");
                mProgressBar.setVisibility(View.GONE);
                mErrorMessage.setVisibility(View.VISIBLE);
                mErrorMessage.setText(R.string.error_message);
            }
        });
    }

    /**
     * Save the instance of the state when user navigates away from the activity.
     *
     * @param outState : Bundle in which the state is saved.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("movie_type", movieType);
        outState.putParcelableArrayList("movies_list", (ArrayList<DiscoverMoviesResults>) movies);
        int index = mLayoutManager.findFirstCompletelyVisibleItemPosition();
        outState.putInt("index", index);
        outState.putString("title", (String) getTitle());
        Log.d(TAG, "onSaveInstanceState: index = " + String.valueOf(index));
    }
}
