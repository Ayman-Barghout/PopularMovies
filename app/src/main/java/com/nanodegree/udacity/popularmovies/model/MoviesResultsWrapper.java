package com.nanodegree.udacity.popularmovies.model;

public class MoviesResultsWrapper {
    private MoviesResults moviesResult;
    private String message;

    public MoviesResultsWrapper(MoviesResults moviesResult, String message) {
        this.moviesResult = moviesResult;
        this.message = message;
    }

    public MoviesResults getMoviesResult() {
        return moviesResult;
    }

    public void setMoviesResult(MoviesResults moviesResult) {
        this.moviesResult = moviesResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
