package com.nanodegree.udacity.popularmovies.utils;

import android.content.Context;
import android.widget.Toast;

import com.nanodegree.udacity.popularmovies.MoviesListAdapter;
import com.nanodegree.udacity.popularmovies.model.MoviesResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {
    private final static String API_KEY = "1a76d61e08128d3000ad13a99de3e6fe";

    private final static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static void getResponseBody(MoviesListAdapter mAdapter, final Context context,
                                       final MoviesListAdapter.MovieTileClickListener listener, String sortingOption) {
        TheMovieDBService theMovieDBService = retrofit.create(TheMovieDBService.class);
        Call<MoviesResult> movies = theMovieDBService.getMoviesResult(API_KEY, sortingOption);

        movies.enqueue(new Callback<MoviesResult>() {

            @Override
            public void onResponse(Call<MoviesResult> call, Response<MoviesResult> response) {
                MoviesResult result = response.body();
                mAdapter = new MoviesListAdapter(result.getResults(), listener);
            }

            @Override
            public void onFailure(Call<MoviesResult> call, Throwable t) {
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }


}
