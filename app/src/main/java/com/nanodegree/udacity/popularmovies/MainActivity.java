package com.nanodegree.udacity.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.nanodegree.udacity.popularmovies.model.MoviesResult;
import com.nanodegree.udacity.popularmovies.utils.TheMovieDBService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView mMoviesListRV;
    MoviesListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TheMovieDBService theMovieDBService = retrofit.create(TheMovieDBService.class);

        Call<MoviesResult> movies = theMovieDBService.getMoviesResult("1a76d61e08128d3000ad13a99de3e6fe","popularity.desc");

        movies.enqueue(new Callback<MoviesResult>() {

            @Override
            public void onResponse(Call<MoviesResult> call, Response<MoviesResult> response) {
                MoviesResult result = response.body();
                mAdapter = new MoviesListAdapter(result.getResults());
                mMoviesListRV.setAdapter(mAdapter);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this , 2);
                mMoviesListRV.setLayoutManager(layoutManager);

            }

            @Override
            public void onFailure(Call<MoviesResult> call, Throwable t) {
                Toast.makeText(MainActivity.this ,"Error fetching data", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

        mMoviesListRV = findViewById(R.id.rv_movie_tiles_list);

        }
}
