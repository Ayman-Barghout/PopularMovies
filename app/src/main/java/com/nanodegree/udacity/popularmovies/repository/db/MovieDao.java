package com.nanodegree.udacity.popularmovies.repository.db;

import com.nanodegree.udacity.popularmovies.model.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    public LiveData<List<Movie>> loadAllMovies();

    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    public LiveData<List<Movie>> loadMoviesByPopularity();

    @Query("SELECT * FROM movie ORDER BY vote_average DESC")
    public LiveData<List<Movie>> loadMoviesByRating();

    @Insert(onConflict = REPLACE)
    public void insertMoviesPage(List<Movie> movies);

}
