package com.manmeet.moviescentralbeta;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.manmeet.moviescentralbeta.utilities.JSONHelper;
import com.manmeet.moviescentralbeta.utilities.NetworkHelper;
import com.manmeet.moviescentralbeta.utilities.NetworkUtility;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private ArrayList<Movie> movieArrayList;
    private Boolean sortByRating = true;
    private Toast mToast;
    private int mNoOfColumns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mErrorMessage = (TextView) findViewById(R.id.error_message);
        mProgressBar = (ProgressBar) findViewById(R.id.loading);
        if (getResources().getConfiguration().orientation == 1) mNoOfColumns = 2;
        else mNoOfColumns = 3;

        movieArrayList = new ArrayList<Movie>();

        GridLayoutManager grid = new GridLayoutManager(this, mNoOfColumns);
        mRecyclerView.setLayoutManager(grid);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter();
        mRecyclerView.setAdapter(mMovieAdapter);
        if (NetworkUtility.isNetworkConnected(this)) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mErrorMessage.setVisibility(View.GONE);
            new MovieAsyncTask().execute("okay");
        } else {
            mErrorMessage.setVisibility(View.VISIBLE);
            mErrorMessage.setText("No Network Connection");
            mProgressBar.setVisibility(View.INVISIBLE);
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
            case R.id.refresh_main_menu_item:
                if (NetworkUtility.isNetworkConnected(this)) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mErrorMessage.setVisibility(View.GONE);
                    new MovieAsyncTask().execute("Place");
                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(MainActivity.this, "Refresh!", Toast.LENGTH_SHORT);
                    mToast.show();
                } else {
                    mErrorMessage.setVisibility(View.VISIBLE);
                    mErrorMessage.setText(R.string.no_network_connection);
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.recent_main_menu_item:
                sortByRating = false;
                if (NetworkUtility.isNetworkConnected(this)) {
                    mErrorMessage.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    new MovieAsyncTask().execute("Place");
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(MainActivity.this, "Sorting By Popularity", Toast.LENGTH_SHORT);
                    mToast.show();
                } else {
                    mErrorMessage.setVisibility(View.VISIBLE);
                    mErrorMessage.setText(R.string.no_network_connection);
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.rating_main_menu_item:
                sortByRating = true;
                if (NetworkUtility.isNetworkConnected(this)) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mErrorMessage.setVisibility(View.GONE);
                    new MovieAsyncTask().execute("Place");
                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(MainActivity.this, "Sorting By Rating", Toast.LENGTH_SHORT);
                    mToast.show();
                } else {
                    mErrorMessage.setVisibility(View.VISIBLE);
                    mErrorMessage.setText(R.string.no_network_connection);
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
                break;
        }
        return true;
    }

    public class MovieAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {
        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            URL url = NetworkHelper.buildURL(sortByRating);
            String http_data = null;
            ArrayList<Movie> returnMovieList = null;
            try {
                http_data = NetworkHelper.getHTTPData(url);
            } catch (IOException e) {
                Log.e(getLocalClassName(), "doInBackground: BAD RESPONSE FROM HTTP HELPER");
                e.printStackTrace();
            }
            try {
                returnMovieList = JSONHelper.getArrayListFromJSON(http_data);
            } catch (JSONException e) {
                Log.e(getLocalClassName(), "doInBackground: BAD RESPONSE FROM JSON HELPER");
                e.printStackTrace();
            }

            return returnMovieList;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (NetworkUtility.isNetworkConnected(getBaseContext())) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mErrorMessage.setVisibility(View.GONE);
                mMovieAdapter.setMovieData(movies);
            } else {
                mErrorMessage.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                mErrorMessage.setText(R.string.no_network_connection);
            }

        }

    }
}
