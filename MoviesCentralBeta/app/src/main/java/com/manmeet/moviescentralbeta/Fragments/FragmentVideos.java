package com.manmeet.moviescentralbeta.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manmeet.moviescentralbeta.Adapters.VideosAdapter;
import com.manmeet.moviescentralbeta.DetailActivity;
import com.manmeet.moviescentralbeta.MainActivity;
import com.manmeet.moviescentralbeta.Pojos.movie_details.MovieDetail;
import com.manmeet.moviescentralbeta.Pojos.movie_images.Backdrops;
import com.manmeet.moviescentralbeta.Pojos.movie_videos.VideoResults;
import com.manmeet.moviescentralbeta.Pojos.movie_videos.Videos;
import com.manmeet.moviescentralbeta.R;
import com.manmeet.moviescentralbeta.Retrofit.ApiInterface;
import com.manmeet.moviescentralbeta.Retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.manmeet.moviescentralbeta.ApiUtility.API_KEY;

public class FragmentVideos extends android.support.v4.app.Fragment {
    /*@BindView(R.id.fragment_recycler_list)
    RecyclerView mCastRecycler;
    */
    private RecyclerView mVideosRecycler;
    private static List<VideoResults> mVideosList;
    private static VideosAdapter mVideosAdapter;
    private List<Backdrops> mPhotosList;
    private String id;
    private static ApiInterface mApiInterface;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        mVideosRecycler = rootView.findViewById(R.id.fragment_recycler_list);
        id = DetailActivity.id;
        mVideosList = new ArrayList<>();
        mPhotosList = new ArrayList<>();
        mVideosAdapter = new VideosAdapter(getContext(), DetailActivity.mVideosList, mPhotosList);
        mApiInterface = ServiceGenerator.createService(ApiInterface.class);
        getMovieDetails(id);
        return rootView;
    }

    private MovieDetail getMovieDetails(String id) {
        Call<MovieDetail> call = mApiInterface.getMovieDetails(id, API_KEY, "images");
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                MovieDetail movieDetail;
                movieDetail = response.body();
                UpdateDetailVideos(movieDetail);
            }
            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.v(TAG, "onFailure()");
            }
        });
        return null;
    }

    private void UpdateDetailVideos(MovieDetail movieDetail) {
        mPhotosList.clear();
        mPhotosList.addAll(movieDetail.getImages().getBackdrops());
        mPhotosList.remove(0);
        mVideosRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        mVideosRecycler.setItemAnimator(new DefaultItemAnimator());
        mVideosRecycler.setAdapter(mVideosAdapter);
    }

}