package com.manmeet.moviescentralbeta;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.manmeet.moviescentralbeta.Adapters.CategoryAdapter;
import com.manmeet.moviescentralbeta.Adapters.GenreAdapter;
import com.manmeet.moviescentralbeta.Adapters.MovieAdapter;
import com.manmeet.moviescentralbeta.Adapters.ReviewsAdapter;
import com.manmeet.moviescentralbeta.Database.MoviesContract;
import com.manmeet.moviescentralbeta.Pojos.movie_details.Genres;
import com.manmeet.moviescentralbeta.Pojos.movie_details.MovieDetail;
import com.manmeet.moviescentralbeta.Pojos.movie_details.ReviewResults;
import com.manmeet.moviescentralbeta.Pojos.movie_details.Reviews;
import com.manmeet.moviescentralbeta.Pojos.movie_videos.VideoResults;
import com.manmeet.moviescentralbeta.Pojos.movie_videos.Videos;
import com.manmeet.moviescentralbeta.Retrofit.ApiInterface;
import com.manmeet.moviescentralbeta.Retrofit.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.manmeet.moviescentralbeta.ApiUtility.API_KEY;

/*
 Created by Manmeet on 24-Oct-17.
*/

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    public static List<VideoResults> mVideosList;
    public static String id;
    private static ApiInterface mApiInterface;
    public Context mContext;
    @BindView(R.id.detail_textView_movie_title)
    TextView mTitleText;
    @BindView(R.id.detail_textView_movie_rating_value)
    TextView mRatingText;
    @BindView(R.id.detail_textView_movie_releaseDate)
    TextView mReleaseDateText;
    @BindView(R.id.detail_textView_movie_overview_value)
    TextView mPlotText;
    @BindView(R.id.detail_imageView_movie_poster_small)
    ImageView mPosterSmall;
    @BindView(R.id.detail_imageView_movie_poster)
    ImageView mPoster;
    @BindView(R.id.detail_toggleButton_movie_favourite)
    ToggleButton mFavButton;
    @BindView(R.id.detail_textView_movie_rating_times)
    TextView mRatingTimesText;
    @BindView(R.id.detail_textView_movie_runtime_value)
    TextView mRunTimeText;
    @BindView(R.id.detail_recyclerView_genre)
    RecyclerView mGenreRecycler;
    @BindView(R.id.detail_recyclerView_reviews)
    RecyclerView mReviewsRecycler;
    @BindView(R.id.detail_movie_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.detail_movie_tabs)
    TabLayout mTabLayout;
    private List<Genres> mGenresList;
    private List<ReviewResults> mReviewsList;
    private MovieAdapter mMovieAdapter;
    private GenreAdapter mGenreAdpater;
    private ReviewsAdapter mReviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        ButterKnife.bind(this);
        //For Transparent ActionBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mVideosList = new ArrayList<>();
        mApiInterface = ServiceGenerator.createService(ApiInterface.class);
        Intent input_intent = getIntent();
        id = input_intent.getStringExtra("id");
        getMovieDetails(id);

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getSupportFragmentManager());

        mViewPager.setAdapter(categoryAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mGenresList = new ArrayList<>();
        mReviewsList = new ArrayList<>();

        mReviewsAdapter = new ReviewsAdapter(this, mReviewsList);
        mGenreAdpater = new GenreAdapter(this, mGenresList);

        mVideosList = new ArrayList<>();

    }

    /* Get the details of a particular movie.
     * @param id : Id of the movie whose details are needed.
     * @return
     */
    private MovieDetail getMovieDetails(String id) {
        Log.i(TAG, "getMovieDetails: " + id);
        Call<MovieDetail> call = mApiInterface.getMovieDetails(id, API_KEY, "images");
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                MovieDetail movieDetail;
                movieDetail = response.body();
                UpdateDetailActivity(movieDetail);
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.v(TAG, "onFailure()");
            }
        });
        return null;
    }

    private void UpdateDetailActivity(final MovieDetail movieDetail) {

        mGenreRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mGenreRecycler.setItemAnimator(new DefaultItemAnimator());
        mGenreRecycler.setAdapter(mGenreAdpater);

        mReviewsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mReviewsRecycler.setItemAnimator(new DefaultItemAnimator());
        mReviewsRecycler.setAdapter(mReviewsAdapter);

        final String[] projections = new String[]{
                MoviesContract.MovieProvider._ID,
                MoviesContract.MovieProvider.MOVIE_ID
        };

        final Cursor cursor = getContentResolver().query(
                Uri.withAppendedPath(MoviesContract.BASE_URI, MoviesContract.MovieProvider.PATH_TABLE),
                projections, null, null, null);

        cursor.moveToFirst();

        boolean isFavourite = false;
        for (int i = 0; i < cursor.getCount(); i++) {
            String movieId = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieProvider.MOVIE_ID));
            if (movieId.equals(movieDetail.getId())) {
                isFavourite = true;
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        if (isFavourite) {
            mFavButton.setChecked(true);
            mFavButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite));
        } else {
            mFavButton.setChecked(false);
            mFavButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
        }
        mFavButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Cursor cursor = getContentResolver().query(Uri.withAppendedPath(MoviesContract.BASE_URI, MoviesContract.MovieProvider.PATH_TABLE),
                        projections, null, null, null);
                cursor.moveToFirst();
                if (isChecked) {
                    //Log.d(TAG, "onCheckedChanged: True");
                    mFavButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite));
                    cursor.moveToFirst();
                    boolean isFav = false;
                    for (int i = 0; i < cursor.getCount(); i++) {
                        String id = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieProvider.MOVIE_ID));
                        if (movieDetail.getId().equals(id)) {
                            isFav = true;
                        }
                        cursor.moveToNext();
                    }
                    if (!isFav) {
                        //Log.d(TAG, "onCheckedChanged: Add item to DB");

                        mFavButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),
                                R.drawable.ic_favorite));
                        ContentValues values = new ContentValues();
                        values.put(MoviesContract.MovieProvider.MOVIE_ID, movieDetail.getId());
                        getContentResolver().insert(
                                Uri.withAppendedPath(MoviesContract.BASE_URI, MoviesContract.MovieProvider.PATH_TABLE),
                                values);
                    }
                } else {
                    //Log.d(TAG, "onCheckedChanged: Delete item from DB");
                    int rowId = 0;
                    mFavButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
                    cursor.moveToFirst();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        String id = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieProvider.MOVIE_ID));
                        if (movieDetail.getId().equals(id)) {
                            rowId = cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieProvider._ID));
                        }
                        cursor.moveToNext();
                    }
                    getContentResolver().delete(ContentUris.withAppendedId(
                            Uri.withAppendedPath(MoviesContract.BASE_URI, MoviesContract.MovieProvider.PATH_TABLE), rowId),
                            null, null);
                }
                cursor.close();
            }
        });

        getVideos(id);
        Log.i(TAG, "UpdateDetailActivity: " + mVideosList.size());
        mPoster.setImageAlpha(150);
        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + movieDetail.getBackdropPath()).into(mPoster);
        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + movieDetail.getBackdropPath())
                .centerCrop()
                .resize(225, 400).
                into(mPosterSmall);
        mTitleText.setText(movieDetail.getOriginalTitle());
        mRunTimeText.setText(movieDetail.getRuntime());
        mRatingText.setText(movieDetail.getVoteAverage());
        mRatingTimesText.setText(movieDetail.getVoteCount());
        mPlotText.setText(movieDetail.getOverview());
        mReleaseDateText.setText(movieDetail.getReleaseDate());

        mGenresList.clear();
        mGenresList.addAll(movieDetail.getGenres());
        mGenreAdpater.notifyDataSetChanged();

        getReviews(id);
    }

    public void setThumbnailVideo(VideoResults videoResults) {
        String key = videoResults.getKey();
        final String url = "https://www.youtube.com/watch?v=" + key;
        mPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
    }

    /**
     * Update the UI to show the video in the VideoFragment of the movie.
     *
     * @param id - Id of the current Movie
     */
    public void getVideos(@NonNull String id) {
        Call<Videos> call;
        Log.i(TAG, "getVideos: hi there!!");
        mApiInterface = ServiceGenerator.createService(ApiInterface.class);
        final List[] results = new List[]{new ArrayList<VideoResults>()};
        call = mApiInterface.getVideos(id, API_KEY);
        call.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                Videos videos = response.body();
                if (videos != null) {
                    results[0] = videos.getResults();
                    mVideosList.addAll(results[0]);
                    setThumbnailVideo(mVideosList.get(0));
                    Log.i(TAG, "onResponse: videos fetched " + videos.getResults().size());
                }
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {
                Log.d(TAG, "onFailure: Failed to get videos");
            }
        });
    }

    /**
     * Get the user reviews for a particular movie season.
     *
     * @param id : id of the movie whose reviews are needed.
     */
    private void getReviews(String id) {
        Call<Reviews> call;
        call = mApiInterface.getReviews(id, API_KEY);
        call.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                Reviews reviews = response.body();
                List<ReviewResults> results = new ArrayList<>();
                if (reviews != null) {
                    results = reviews.getResults();
                    mReviewsList.clear();
                    if (results != null && results.size() > 0) {
                        mReviewsList.addAll(results);
                        mReviewsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Log.d(TAG, "onFailure: Failed to get Reviews");
            }
        });
    }
}