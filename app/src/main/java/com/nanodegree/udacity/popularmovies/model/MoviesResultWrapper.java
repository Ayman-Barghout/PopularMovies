package com.nanodegree.udacity.popularmovies.model;

public class MoviesResultWrapper {
    private MoviesResult moviesResult;
    private String message;

    public MoviesResultWrapper(MoviesResult moviesResult, String message) {
        this.moviesResult = moviesResult;
        this.message = message;
    }

    public MoviesResult getMoviesResult() {
        return moviesResult;
    }

    public void setMoviesResult(MoviesResult moviesResult) {
        this.moviesResult = moviesResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
