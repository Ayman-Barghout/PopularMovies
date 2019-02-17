package com.nanodegree.udacity.popularmovies;

import android.app.Application;

import com.nanodegree.udacity.popularmovies.repository.db.MovieDatabase;

public class PopularMoviesApplication extends Application {
    public static MovieDatabase moviesDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        moviesDatabase = MovieDatabase.getDatabase(this);
    }
}
