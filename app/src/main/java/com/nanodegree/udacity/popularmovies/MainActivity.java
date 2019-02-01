package com.nanodegree.udacity.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import com.nanodegree.udacity.popularmovies.viewmodel.MoviesViewModel;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private MoviesListAdapter mAdapter;

    private MoviesViewModel moviesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        observeResponseData();

        RecyclerView mMoviesListRV = findViewById(R.id.rv_movie_tiles_list);
        moviesViewModel.callApi();

        mAdapter = new MoviesListAdapter(new ArrayList<>(), clickedItemIndex -> {
            Gson gson = new Gson();

            Intent intent = new Intent(MainActivity.this, MovieDescriptionActivity.class);
            intent.putExtra("obj", gson.toJson(moviesViewModel.getMoviesLiveData().getValue().getMoviesResult().getResults().get(clickedItemIndex)));

            startActivity(intent);
        });

        mMoviesListRV.setAdapter(mAdapter);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
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
        switch (itemId) {
            case R.id.menu_sort_action_popularity:
                moviesViewModel.setSortOption(getString(R.string.sort_popularity_value));
                break;
            case R.id.menu_sort_action_rating:
                moviesViewModel.setSortOption(getString(R.string.sort_rating_value));
                break;
        }
        moviesViewModel.callApi();
        return true;
    }


    private void observeResponseData() {

//        TODO: Fix the freaking code here
        moviesViewModel.getMoviesLiveData().observe(MainActivity.this, moviesResults -> {
            if(moviesResults.getMessage()!=null)
            {
                Toast.makeText(this,"WorkersApp: "+ moviesResults.getMessage(),Toast.LENGTH_SHORT).show();
                moviesViewModel.resetMessage();
                return;
            }
            mAdapter.setMovieResults(moviesResults.getMoviesResult().getResults());
        });
    }
}
