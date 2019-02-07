package com.nanodegree.udacity.popularmovies.view.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.nanodegree.udacity.popularmovies.R;
import com.nanodegree.udacity.popularmovies.model.Result;

public class MovieDescriptionActivity extends AppCompatActivity {
    TextView mMovieTitleTV;
    ImageView mMoviePosterIV;
    TextView mReleaseDateTV;
    TextView mAverageVoteTV;
    TextView mSynopsisTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);

        mMovieTitleTV = findViewById(R.id.tv_movie_name);
        mMoviePosterIV = findViewById(R.id.iv_movie_thumbnail);
        mReleaseDateTV = findViewById(R.id.tv_release_date);
        mAverageVoteTV = findViewById(R.id.tv_vote_average);
        mSynopsisTV = findViewById(R.id.tv_synopsis);

        Gson gson = new Gson();
        String objString = getIntent().getStringExtra("obj");
        Result movieData = gson.fromJson(objString, Result.class);

        mMovieTitleTV.setText(movieData.getTitle());

        GlideUrl imageUrl = new GlideUrl("http://image.tmdb.org/t/p/w500" + movieData.getPosterPath());
        Glide.with(this)
                .load(imageUrl)
                .apply(new RequestOptions().override(500, 800))
                .into(mMoviePosterIV);

        mReleaseDateTV.setText(movieData.getReleaseDate());
        mAverageVoteTV.setText(String.valueOf(movieData.getVoteAverage()));
        mSynopsisTV.setText(movieData.getOverview());

    }
}
