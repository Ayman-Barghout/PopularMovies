package com.nanodegree.udacity.popularmovies.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.nanodegree.udacity.popularmovies.model.Movie;
import com.nanodegree.udacity.popularmovies.model.MoviesResults;
import com.nanodegree.udacity.popularmovies.model.MoviesResultsWrapper;
import com.nanodegree.udacity.popularmovies.repository.remote.TheMovieDBService;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nanodegree.udacity.popularmovies.PopularMoviesApplication.moviesDatabase;

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

    public void updateDatabase(MoviesResultsWrapper moviesResultsWrapper,
                        MutableLiveData<MoviesResultsWrapper> moviesLiveData, int pageNumber,
                        MutableLiveData<Boolean> isLoading) {

        String API_KEY = "1a76d61e08128d3000ad13a99de3e6fe";
        Call<MoviesResults> movies = theMovieDBService.getMoviesResult(API_KEY, pageNumber);

        movies.enqueue(new Callback<MoviesResults>() {

            @Override
            public void onResponse(@NonNull Call<MoviesResults> call, @NonNull Response<MoviesResults> response) {
                moviesResultsWrapper.setMoviesResult(response.body());
                moviesResultsWrapper.setMessage(null);
                moviesLiveData.postValue(moviesResultsWrapper);
                moviesDatabase.movieDao().insertMoviesPage(Objects.requireNonNull(response.body()).getMovies());
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

    public LiveData<List<Movie>> getMoviesListPopularity(){
        Log.d("RoomWTF", "Is the Dao null?: " + moviesDatabase.movieDao().loadMoviesByPopularity().getValue());
        return moviesDatabase.movieDao().loadMoviesByPopularity();
    }

    public LiveData<List<Movie>> getMoviesListRating(){
        Log.d("RoomWTF", "Is the Dao null?: " + moviesDatabase.movieDao().loadMoviesByRating().getValue());
        return moviesDatabase.movieDao().loadMoviesByRating();
    }


}
