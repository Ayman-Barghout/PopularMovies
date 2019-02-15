package com.nanodegree.udacity.popularmovies.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.nanodegree.udacity.popularmovies.R;
import com.nanodegree.udacity.popularmovies.model.Movie;

import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieTileViewHolder> {
    private List<Movie> movies;
    private final MovieTileClickListener mOnMovieTileClicked;


    public MoviesListAdapter(List<Movie> movies, MovieTileClickListener listener) {
        this.movies = movies;
        this.mOnMovieTileClicked = listener;
    }

    public void setMovieResults(List<Movie> list) {
        movies = list;
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
        String posterPathStr = movies.get(i).getPosterPath();
        GlideUrl posterPathUrl = new GlideUrl("http://image.tmdb.org/t/p/w342" + posterPathStr);
        movieTileViewHolder.bind(movieTileViewHolder.getAdapterPosition(), posterPathUrl, mOnMovieTileClicked);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieTileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mMoviePosterIV;

        MovieTileViewHolder(@NonNull View itemView) {
            super(itemView);

            mMoviePosterIV = itemView.findViewById(R.id.iv_movie_poster);

            itemView.setOnClickListener(this);
        }

        @SuppressLint("CheckResult")
        void bind(final int index, GlideUrl posterPath, final MovieTileClickListener listener) {

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


