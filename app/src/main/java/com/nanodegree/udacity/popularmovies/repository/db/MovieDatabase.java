package com.nanodegree.udacity.popularmovies.repository.db;

import android.content.Context;

import com.nanodegree.udacity.popularmovies.model.Movie;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();

    private static MovieDatabase movieDatabase;

    public static MovieDatabase getDatabase (final Context context){
        if (movieDatabase == null) {
            synchronized (MovieDatabase.class) {
                if (movieDatabase == null){
                    movieDatabase =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    MovieDatabase.class,
                                    "movie_database")
                                    .allowMainThreadQueries()
                                    .build();
                }
            }
        }
    return movieDatabase;}
}
