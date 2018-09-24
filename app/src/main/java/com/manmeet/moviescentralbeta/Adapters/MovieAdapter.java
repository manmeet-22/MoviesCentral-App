package com.manmeet.moviescentralbeta.Adapters;

/**
 * Created by Manmeet on 24-Oct-17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.manmeet.moviescentralbeta.DetailActivity;
import com.manmeet.moviescentralbeta.R;
import com.manmeet.moviescentralbeta.Pojos.movie.DiscoverMoviesResults;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<DiscoverMoviesResults> movieList;
    private Context mContext;

    public MovieAdapter(Context context, List<DiscoverMoviesResults> moviesList) {
        this.mContext = context;
        this.movieList = moviesList;
    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View returnHolderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(returnHolderView);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MovieViewHolder holder, int position) {
        DiscoverMoviesResults currMovie = movieList.get(position);
        Picasso.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500"+currMovie.getPosterPath())
                .placeholder(R.drawable.no_poster)
                .error(R.drawable.error_image).
                into(holder.thumbView);
    }

    @Override
    public int getItemCount() {
        if (movieList != null)
            return movieList.size();
        else
            return 0;
    }

    public void setMovieData(ArrayList<DiscoverMoviesResults> list) {
        if (movieList != null)
            movieList.clear();
        movieList = list;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        ImageView thumbView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            thumbView = itemView.findViewById(R.id.imageView_movies_listItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            DiscoverMoviesResults currMovie = movieList.get(getAdapterPosition());
            String id = currMovie.getId();
            //Log.i("Id is", id);
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra("id", id);
            view.getContext().startActivity(intent);
        }
    }
}
