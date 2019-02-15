package com.nanodegree.udacity.popularmovies.view.activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import com.nanodegree.udacity.popularmovies.model.Movie;
import com.nanodegree.udacity.popularmovies.view.adapter.MoviesListAdapter;
import com.nanodegree.udacity.popularmovies.R;
import com.nanodegree.udacity.popularmovies.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private MoviesListAdapter mAdapter;

    private MoviesViewModel moviesViewModel;

    private ProgressBar mProgressBar;

    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.loading_progressBar);

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        observeResponseData();

        if (moviesViewModel.getMoviesList() == null) {
            moviesViewModel.initPageNumber();
            moviesViewModel.callApi();
        }

        RecyclerView mMoviesListRV = findViewById(R.id.rv_movie_tiles_list);

        mAdapter = new MoviesListAdapter(moviesViewModel.getMoviesList() == null ? new ArrayList<>() : moviesViewModel.getMoviesList().getValue()
                , clickedItemIndex -> {
            Gson gson = new Gson();

            Intent intent = new Intent(mContext, MovieDescriptionActivity.class);
            List<Movie> moviesList = Objects.requireNonNull(moviesViewModel.getMoviesList().getValue());
            intent.putExtra("obj", gson.toJson(Objects.requireNonNull(moviesList.get(clickedItemIndex))));

            startActivity(intent);
        });

        mMoviesListRV.setAdapter(mAdapter);

        GridLayoutManager layoutManager;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(mContext, 2);
        } else {
            layoutManager = new GridLayoutManager(mContext, 3);
        }

        mMoviesListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (mAdapter.getItemCount() > 0 && !recyclerView.canScrollVertically(1)) {
                    int totalPages = Objects.requireNonNull(moviesViewModel.getMoviesLiveData().getValue()).getMoviesResult().getTotalPages();
                    int currentPage = Objects.requireNonNull(moviesViewModel.getPageNumber().getValue());

                    if (totalPages - 1 >= currentPage && !Objects.requireNonNull(moviesViewModel.getIsLoading().getValue())) {
                        moviesViewModel.setPageNumber(moviesViewModel.getPageNumber().getValue() + 1);
                        moviesViewModel.callApi();
                    }

                }
            }
        });

        mMoviesListRV.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        if (itemId != R.id.menu_sort_by_item) {

            if(itemId == R.id.menu_sort_action_popularity) {
                moviesViewModel.setSortOption(getString(R.string.sort_popularity_value));
            } else if (itemId == R.id.menu_sort_action_rating){
                    moviesViewModel.setSortOption(getString(R.string.sort_rating_value));
            }

            moviesViewModel.resetMoviesList();
            moviesViewModel.setPageNumber(1);
            moviesViewModel.callApi();
        }
        return true;
    }


    private void observeResponseData() {

        moviesViewModel.getMoviesLiveData().observe(MainActivity.this, moviesResults -> {
            if (moviesResults.getMessage() != null) {
                Toast.makeText(mContext, "Error: " + moviesResults.getMessage(), Toast.LENGTH_SHORT).show();
                moviesViewModel.resetMessage();
                return;
            }

            List<Movie> currentMoviesList = moviesResults.getMoviesResult().getMovies();

            moviesViewModel.addToMoviesList(currentMoviesList);

            mAdapter.setMovieResults(moviesViewModel.getMoviesList().getValue());
        });

        moviesViewModel.getIsLoading().observe(MainActivity.this, isLoading -> {
            int visibility = isLoading ? View.VISIBLE : View.INVISIBLE;
            mProgressBar.setVisibility(visibility);
        });

    }
}
