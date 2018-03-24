package com.manmeet.moviescentralbeta;

/**
 * Created by Manmeet on 24-Oct-17.
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> movieList;

    public MovieAdapter() {

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        ImageView thumbView;
        public MovieViewHolder(View itemView) {
            super(itemView);
            thumbView = (ImageView) itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie currMovie = movieList.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra("currMovie", currMovie);
            view.getContext().startActivity(intent);
        }
    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View returnHolderView;
        returnHolderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(returnHolderView);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MovieViewHolder holder, int position) {
        Movie currMovie = movieList.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(currMovie.getImageURL())
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

    public void setMovieData(ArrayList<Movie> list) {
        if (movieList != null)
            movieList.clear();
        movieList = list;
        notifyDataSetChanged();
    }
}
