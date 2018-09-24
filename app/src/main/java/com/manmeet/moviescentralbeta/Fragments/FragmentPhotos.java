package com.manmeet.moviescentralbeta.Fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manmeet.moviescentralbeta.Adapters.PhotosAdapter;
import com.manmeet.moviescentralbeta.DetailActivity;
import com.manmeet.moviescentralbeta.Pojos.movie_details.MovieDetail;
import com.manmeet.moviescentralbeta.Pojos.movie_images.Backdrops;
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

public class FragmentPhotos extends android.support.v4.app.Fragment {
    /*@BindView(R.id.fragment_recycler_list)
    RecyclerView mCastRecycler;
    */
    private RecyclerView mPhotosRecycler;
    private List<Backdrops> mPhotosList;
    private PhotosAdapter mPhotosAdapter;
    private String id;
    private ApiInterface mApiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        mPhotosRecycler = rootView.findViewById(R.id.fragment_recycler_list);
        id = DetailActivity.id;
        mPhotosList = new ArrayList<>();
        mPhotosAdapter = new PhotosAdapter(getContext(), mPhotosList);

        mApiInterface = ServiceGenerator.createService(ApiInterface.class);
        getMovieDetails(id);
        return rootView;
    }

    private MovieDetail getMovieDetails(String id) {
        //Log.d(TAG, "getMovieDetails: " + id);
        Call<MovieDetail> call = mApiInterface.getMovieDetails(id, API_KEY, "images");
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                MovieDetail movieDetail;
                movieDetail = response.body();
                UpdateDetailPhotos(movieDetail);
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.v(TAG, "onFailure()");
            }
        });
        return null;
    }

    private void UpdateDetailPhotos(MovieDetail movieDetail) {
        mPhotosRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        mPhotosRecycler.setItemAnimator(new DefaultItemAnimator());
        mPhotosRecycler.setAdapter(mPhotosAdapter);
        mPhotosList.clear();
        mPhotosList.addAll(movieDetail.getImages().getBackdrops());
        mPhotosList.remove(0);
        mPhotosAdapter.notifyDataSetChanged();

    }

}
