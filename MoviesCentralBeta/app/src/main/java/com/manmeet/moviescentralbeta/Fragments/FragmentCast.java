package com.manmeet.moviescentralbeta.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manmeet.moviescentralbeta.Adapters.CastAdapter;
import com.manmeet.moviescentralbeta.Adapters.GenreAdapter;
import com.manmeet.moviescentralbeta.Adapters.PhotosAdapter;
import com.manmeet.moviescentralbeta.DetailActivity;
import com.manmeet.moviescentralbeta.Pojos.movie_details.Cast;
import com.manmeet.moviescentralbeta.Pojos.movie_details.Credits;
import com.manmeet.moviescentralbeta.Pojos.movie_details.MovieDetail;
import com.manmeet.moviescentralbeta.R;
import com.manmeet.moviescentralbeta.Retrofit.ApiInterface;
import com.manmeet.moviescentralbeta.Retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.manmeet.moviescentralbeta.ApiUtility.API_KEY;

public class FragmentCast extends android.support.v4.app.Fragment{
    /*@BindView(R.id.fragment_recycler_list)
    RecyclerView mCastRecycler;
    */
    private RecyclerView mCastRecycler;
    private List<Cast> mCastList;
    private CastAdapter mCastAdapter;
    private String id ;
    private ApiInterface mApiInterface;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        mCastRecycler = rootView.findViewById(R.id.fragment_recycler_list);
        id = DetailActivity.id;
        mCastList = new ArrayList<>();
        mCastAdapter = new CastAdapter(getContext(), mCastList);

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
                UpdateDetailCast(movieDetail);
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.v(TAG, "onFailure()");
            }
        });
        return null;
    }

    private void UpdateDetailCast(MovieDetail movieDetail) {
        mCastRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        mCastRecycler.setItemAnimator(new DefaultItemAnimator());
        mCastRecycler.setAdapter(mCastAdapter);
        getCredits(id);

    }
    /**
     * Get the credits information of the tv show or the movie. It includes the crew and cast information.
     *
     * @param id : Id of the tv show or the movie whose information is needed.
     * @return
     */
    @Nullable
    private Credits getCredits(String id) {
        /*Log.d(TAG, "getMovieCredits: ");
       */ Call<Credits> call;
        call = mApiInterface.getMovieCredits(id, API_KEY);
        call.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                Credits credits = response.body();
                List<Cast> cast = new ArrayList<>();
                if (credits != null)
                    cast = credits.getCast();
                updateCast(cast);
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
                Log.d(TAG, "onFailure: Failed to fetch data");
            }
        });
        return null;
    }

    /**
     * Update the UI to show the cast members of the tv show or movie.
     *
     * @param cast : List of all the cast members of the tv show or the movie.
     */
    private void updateCast(List<Cast> cast) {
        if (cast != null) {
            List<Cast> list_cast = new ArrayList<>();
            int count = 0;
            while (count < 10 && count < cast.size()) {
                list_cast.add(cast.get(count));
                count++;
            }
            mCastList.clear();
            mCastList.addAll(list_cast);
            mCastAdapter.notifyDataSetChanged();
        }
    }

}
