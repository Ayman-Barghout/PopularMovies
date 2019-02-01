package com.nanodegree.udacity.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.nanodegree.udacity.popularmovies.model.MoviesResults;
import com.nanodegree.udacity.popularmovies.model.MoviesResultsWrapper;
import com.nanodegree.udacity.popularmovies.utils.MoviesRepository;


public class MoviesViewModel extends ViewModel {
    private MutableLiveData<MoviesResultsWrapper> mMoviesLiveData;
    private MutableLiveData<String> mSortOption;
    private MoviesResultsWrapper moviesResultsWrapper;

    public LiveData<MoviesResultsWrapper> getMoviesLiveData() {
        if (mMoviesLiveData == null) {
            moviesResultsWrapper = new MoviesResultsWrapper();
            mMoviesLiveData = new MutableLiveData<>();
        }
        return mMoviesLiveData;
    }

    public void setSortOption(String s) {
        if (mSortOption == null) {
            mSortOption = new MutableLiveData<>();
        }
        mSortOption.setValue(s);
    }

    public void callApi() {
        MoviesRepository.getInstance().callApi(mSortOption == null ? "popularity.desc" : mSortOption.getValue()
                , moviesResultsWrapper, mMoviesLiveData);
    }

    public void resetMessage() {
        moviesResultsWrapper.setMessage(null);
    }
}
