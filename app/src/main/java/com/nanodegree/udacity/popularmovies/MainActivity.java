package com.nanodegree.udacity.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.nanodegree.udacity.popularmovies.model.MoviesResult;
import com.nanodegree.udacity.popularmovies.viewmodel.MoviesViewModel;


public class MainActivity extends AppCompatActivity implements MoviesListAdapter.MovieTileClickListener {
    private RecyclerView mMoviesListRV;
    private MoviesListAdapter mAdapter;

    private MoviesResult result;

    private String sortBy = "vote_average.desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        mMoviesListRV = findViewById(R.id.rv_movie_tiles_list);

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
        switch (itemId){
            case R.id.menu_sort_action_popularity:
                sortBy = "popularity.desc";
                break;
            case R.id.menu_sort_action_rating:
                sortBy = "vote_average.desc";
                break;
        }
        return true;
    }

    @Override
    public void onMovieTileClick(int clickedItemIndex) {
        Gson gson = new Gson();

        Intent intent = new Intent(MainActivity.this, MovieDescriptionActivity.class);
        intent.putExtra("obj", gson.toJson(result.getResults()[clickedItemIndex]));

        startActivity(intent);
    }
}
