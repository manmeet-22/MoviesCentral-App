package com.manmeet.moviescentralbeta;

/**
 * Created by Manmeet on 24-Oct-17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends ArrayAdapter<Movie> {

    private ArrayList<Movie> movieList;
    private Context mContext;

    public MovieAdapter(Context context, List<Movie> movie) {
        super(context,0,movie);
        mContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        Movie currMovie = movieList.get(position);
        Picasso.with(getContext()).load(currMovie.getImageURL()).into(holder.thumbView);
        return imageView;
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

}
