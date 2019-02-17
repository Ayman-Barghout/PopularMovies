package com.nanodegree.udacity.popularmovies.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nanodegree.udacity.popularmovies.model.Movie;
import com.nanodegree.udacity.popularmovies.model.MoviesResultsWrapper;
import com.nanodegree.udacity.popularmovies.repository.MoviesRepository;

import java.util.List;
import java.util.Objects;


public class MoviesViewModel extends ViewModel {
    private MutableLiveData<MoviesResultsWrapper> mMoviesLiveData;
    private MutableLiveData<String> mSortOption;
    private LiveData<List<Movie>> moviesList;
    private MutableLiveData<Integer> pageNumber;
    private MutableLiveData<Boolean> isDataLoading;
    private MoviesResultsWrapper moviesResultsWrapper = new MoviesResultsWrapper();

    public MutableLiveData<MoviesResultsWrapper> getMoviesLiveData() {
        if (mMoviesLiveData == null) {
            moviesResultsWrapper = new MoviesResultsWrapper();
            mMoviesLiveData = new MutableLiveData<>();
        }
        return mMoviesLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isDataLoading == null) {
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public LiveData<Integer> getPageNumber() {
        return pageNumber;
    }

    public void initPageNumber() {
        if (pageNumber == null) {
            pageNumber = new MutableLiveData<>();
            pageNumber.setValue(1);
        }
    }

    public void setPageNumber(int num) {

        if (pageNumber == null) {
            pageNumber = new MutableLiveData<>();
        }

        pageNumber.setValue(num);
    }

    public void setMoviesList() {

        if (moviesList == null) {
            moviesList = new MutableLiveData<>();
        }

        if (mSortOption == null) {
            setSortOption("popularity.desc");
        }

        resetMoviesList();

        if (Objects.requireNonNull(mSortOption.getValue()).equals("popularity.desc")) {
            moviesList = MoviesRepository.getInstance().getMoviesListPopularity();
        } else {
            moviesList = MoviesRepository.getInstance().getMoviesListRating();
        }
    }

    public LiveData<List<Movie>> getMoviesList(){
        return moviesList;
    }

    private void resetMoviesList() {
        moviesList = null;
    }

    public void setSortOption(String s) {
        if (mSortOption == null) {
            mSortOption = new MutableLiveData<>();
        }
        mSortOption.setValue(s);
    }

    public void callApi() {
        getIsLoading().setValue(true);
        MoviesRepository.getInstance().updateDatabase(moviesResultsWrapper, getMoviesLiveData(), Objects.requireNonNull(pageNumber.getValue()), getIsLoading());
    }

    public void resetMessage() {
        moviesResultsWrapper.setMessage(null);
    }

}
