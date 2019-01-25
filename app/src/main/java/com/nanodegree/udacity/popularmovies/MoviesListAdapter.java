package com.nanodegree.udacity.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.udacity.popularmovies.model.Results;
import com.squareup.picasso.Picasso;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieTileViewHolder> {
    Results[] movieResults;
    final private MovieTileClickListener mOnMovieTileClicked;

    public MoviesListAdapter(Results[] results, MovieTileClickListener listener) {
        this.movieResults = results;
        this.mOnMovieTileClicked = listener;
    }

    @NonNull
    @Override
    public MovieTileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutId = R.layout.movie_tile_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutId, viewGroup, shouldAttachToParentImmediately);
        MovieTileViewHolder viewHolder = new MovieTileViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTileViewHolder movieTileViewHolder, int i) {
        Results result = movieResults[i];
        Uri posterPath = Uri.parse("http://image.tmdb.org/t/p/w500" + result.getPoster_path());
        movieTileViewHolder.bind(result.getTitle(), posterPath);
    }

    @Override
    public int getItemCount() {
        return movieResults.length;
    }

    class MovieTileViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ImageView mMoviePosterIV;
        TextView mMovieTitleTV;

        public MovieTileViewHolder(@NonNull View itemView) {
            super(itemView);

            mMoviePosterIV = itemView.findViewById(R.id.iv_movie_poster);
            mMovieTitleTV = itemView.findViewById(R.id.tv_movie_title);

            itemView.setOnClickListener(this);
        }

        public void bind(String movieTitle, Uri posterPath){
            mMovieTitleTV.setText(movieTitle);
            Picasso.get().load(posterPath).into(mMoviePosterIV);
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


