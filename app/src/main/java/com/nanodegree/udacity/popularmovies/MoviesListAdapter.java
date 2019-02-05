package com.nanodegree.udacity.popularmovies;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.nanodegree.udacity.popularmovies.model.Result;

import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieTileViewHolder> {
    private List<Result> movieResults;
    private final MovieTileClickListener mOnMovieTileClicked;


    MoviesListAdapter(List<Result> results, MovieTileClickListener listener) {
        this.movieResults = results;
        this.mOnMovieTileClicked = listener;
    }

    void setMovieResults(List<Result> list) {
        movieResults = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieTileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutId = R.layout.movie_tile_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutId, viewGroup, false);

        return new MovieTileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTileViewHolder movieTileViewHolder, int i) {
        Result result = movieResults.get(i);
        GlideUrl posterPath = new GlideUrl("http://image.tmdb.org/t/p/w500" + result.getPosterPath());
        movieTileViewHolder.bind(movieTileViewHolder.getAdapterPosition(), result.getTitle(), posterPath, mOnMovieTileClicked);

    }

    @Override
    public int getItemCount() {
        return movieResults.size();
    }

    class MovieTileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mMoviePosterIV;
        TextView mMovieTitleTV;

        MovieTileViewHolder(@NonNull View itemView) {
            super(itemView);

            mMoviePosterIV = itemView.findViewById(R.id.iv_movie_poster);
            mMovieTitleTV = itemView.findViewById(R.id.tv_movie_title);

            itemView.setOnClickListener(this);
        }

        void bind(final int index, String movieTitle, GlideUrl posterPath, final MovieTileClickListener listener) {
            mMovieTitleTV.setText(movieTitle);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.override(500, 800);
            requestOptions.placeholder(R.drawable.image_placeholder);
            requestOptions.fitCenter();
            requestOptions.dontAnimate();

            Glide.with(mMoviePosterIV.getContext())
                    .load(posterPath)
                    .apply(requestOptions)
                    .into(mMoviePosterIV);
            itemView.setOnClickListener(v -> listener.onMovieTileClick(index));
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnMovieTileClicked.onMovieTileClick(clickedPosition);
        }
    }

    public interface MovieTileClickListener {
        void onMovieTileClick(int clickedItemIndex);
    }
}


