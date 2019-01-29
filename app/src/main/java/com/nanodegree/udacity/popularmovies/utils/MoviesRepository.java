package com.nanodegree.udacity.popularmovies.utils;

import android.arch.lifecycle.MutableLiveData;

import com.nanodegree.udacity.popularmovies.model.MoviesResults;
import com.nanodegree.udacity.popularmovies.model.MoviesResultsWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {

    private static final String API_KEY = "1a76d61e08128d3000ad13a99de3e6fe";
    private static final String BASE_URL = "https://api.themoviedb.org";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void callApi(String sortingOption, final MutableLiveData<MoviesResults> moviesLiveData) {
        TheMovieDBService theMovieDBService = retrofit.create(TheMovieDBService.class);
        Call<MoviesResults> movies = theMovieDBService.getMoviesResult(API_KEY, sortingOption);

        movies.enqueue(new Callback<MoviesResults>() {

            @Override
            public void onResponse(Call<MoviesResults> call, Response<MoviesResults> response) {
                moviesLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesResults> call, Throwable t) {
                MoviesResultsWrapper mMoviesWrapper = new MoviesResultsWrapper(null, "Failed to retrieve data");
                t.printStackTrace();
            }
        });
    }


}
