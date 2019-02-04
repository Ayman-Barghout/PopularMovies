package com.nanodegree.udacity.popularmovies;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import com.nanodegree.udacity.popularmovies.model.Result;
import com.nanodegree.udacity.popularmovies.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private MoviesListAdapter mAdapter;

    private MoviesViewModel moviesViewModel;

    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.loading_progressBar);

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        observeResponseData();


        RecyclerView mMoviesListRV = findViewById(R.id.rv_movie_tiles_list);

        mAdapter = new MoviesListAdapter(new ArrayList<>(), clickedItemIndex -> {
            Gson gson = new Gson();

            Intent intent = new Intent(MainActivity.this, MovieDescriptionActivity.class);
            intent.putExtra("obj", gson.toJson(Objects.requireNonNull(moviesViewModel.getMoviesList().getValue().get(clickedItemIndex))));

            startActivity(intent);
        });

        mMoviesListRV.setAdapter(mAdapter);


        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        mMoviesListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    int totalPages = moviesViewModel.getMoviesLiveData().getValue().getMoviesResult().getTotalPages();
                    int currentPage = moviesViewModel.getPageNumber().getValue();

                    if (totalPages - 1 >= currentPage && !moviesViewModel.getIsLoading().getValue()) {
                        moviesViewModel.setPageNumber(moviesViewModel.getPageNumber().getValue() + 1);
                        moviesViewModel.callApi();
                    }

                }
            }
        });

        mMoviesListRV.setLayoutManager(layoutManager);


        if (mAdapter.getItemCount() == 0) {
            moviesViewModel.initPageNumber();
            moviesViewModel.callApi();
        }
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
        switch (itemId) {
            case R.id.menu_sort_action_popularity:
                moviesViewModel.setSortOption(getString(R.string.sort_popularity_value));
                break;
            case R.id.menu_sort_action_rating:
                moviesViewModel.setSortOption(getString(R.string.sort_rating_value));
                break;
        }
        if (itemId != R.id.menu_sort_by_item) {
            moviesViewModel.resetMoviesList();
            moviesViewModel.setPageNumber(1);
            moviesViewModel.callApi();
        }
        return true;
    }


    private void observeResponseData() {

        moviesViewModel.getMoviesLiveData().observe(MainActivity.this, moviesResults -> {
            if (moviesResults.getMessage() != null) {
                Toast.makeText(this, "WorkersApp: " + moviesResults.getMessage(), Toast.LENGTH_SHORT).show();
                moviesViewModel.resetMessage();
                return;
            }

            List<Result> currentMoviesList = moviesResults.getMoviesResult().getResults();

            moviesViewModel.addToMoviesList(currentMoviesList);

            mAdapter.setMovieResults(moviesViewModel.getMoviesList().getValue());
        });

        moviesViewModel.getIsLoading().observe(MainActivity.this, isLoading -> {
            int visibility = isLoading ? View.VISIBLE : View.INVISIBLE;
            mProgressBar.setVisibility(visibility);
        });
    }
}
