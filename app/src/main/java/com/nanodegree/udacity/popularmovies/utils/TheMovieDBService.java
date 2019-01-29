package com.nanodegree.udacity.popularmovies.utils;

import com.nanodegree.udacity.popularmovies.model.MoviesResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDBService {
    @GET("/3/discover/movie")
    Call<MoviesResults> getMoviesResult(
            @Query("api_key") String API_KEY,
            @Query("sort_by") String sortOption
    );
}
