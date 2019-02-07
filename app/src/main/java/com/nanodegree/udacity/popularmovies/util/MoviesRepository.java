package com.nanodegree.udacity.popularmovies.util;

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.nanodegree.udacity.popularmovies.model.MoviesResults;
import com.nanodegree.udacity.popularmovies.model.MoviesResultsWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {

    private static final MoviesRepository moviesRepository = new MoviesRepository();
    private final String BASE_URL = "https://api.themoviedb.org";
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private final TheMovieDBService theMovieDBService = retrofit.create(TheMovieDBService.class);

    public static MoviesRepository getInstance() {
        return moviesRepository;
    }

    public void callApi(String sortingOption, MoviesResultsWrapper moviesResultsWrapper,
                        MutableLiveData<MoviesResultsWrapper> moviesLiveData, int pageNumber,
                        MutableLiveData<Boolean> isLoading) {

        String API_KEY = "1a76d61e08128d3000ad13a99de3e6fe";
        Call<MoviesResults> movies = theMovieDBService.getMoviesResult(API_KEY, sortingOption, pageNumber);

        movies.enqueue(new Callback<MoviesResults>() {

            @Override
            public void onResponse(@NonNull Call<MoviesResults> call, @NonNull Response<MoviesResults> response) {
                moviesResultsWrapper.setMoviesResult(response.body());
                moviesResultsWrapper.setMessage(null);
                moviesLiveData.postValue(moviesResultsWrapper);
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResults> call, @NonNull Throwable t) {
                moviesResultsWrapper.setMessage("Failed to retrieve data");
                moviesLiveData.postValue(moviesResultsWrapper);
                isLoading.postValue(false);
            }
        });
    }


}
