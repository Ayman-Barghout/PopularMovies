package com.nanodegree.udacity.popularmovies;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.udacity.popularmovies.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieTileViewHolder> {
    private  List<Result> movieResults;
    private final MovieTileClickListener mOnMovieTileClicked;


    MoviesListAdapter(List<Result> results, MovieTileClickListener listener) {
        this.movieResults = results;
        this.mOnMovieTileClicked = listener;
    }

    void setMovieResults(List<Result> list){
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
        Uri posterPath = Uri.parse("http://image.tmdb.org/t/p/w500" + result.getPosterPath());
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

        void bind(final int index, String movieTitle, Uri posterPath, final MovieTileClickListener listener) {
            mMovieTitleTV.setText(movieTitle);
            Picasso.get().setIndicatorsEnabled(true);
            Picasso.get().load(posterPath).placeholder(R.color.colorAccent).into(mMoviePosterIV);
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


