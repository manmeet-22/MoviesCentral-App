package com.manmeet.moviescentralbeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Manmeet on 24-Oct-17.
 */

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.movie_name) TextView titleText;
    @BindView(R.id.movie_rating) TextView ratingText;
    @BindView(R.id.movie_release) TextView releaseDateText;
    @BindView(R.id.movie_storyline) TextView plotText;
    @BindView(R.id.movie_poster) ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        ButterKnife.bind(this);
        Intent input_intent = getIntent();
        Movie currentMovie = (Movie) input_intent.getSerializableExtra("currMovie");

        if (currentMovie.getImageURL() == null)
            Picasso.with(this).load(R.drawable.no_poster).into(poster);
        else
            Picasso.with(this).load(currentMovie.getImageURL()).into(poster);
        titleText.setText(currentMovie.getTitle());
        ratingText.setText("Rating :" + currentMovie.getRating());
        releaseDateText.setText("Release Date:" + currentMovie.getReleaseDate());
        plotText.setText("Movie Plot:\n" + currentMovie.getPlot());
    }
}

