package com.nanodegree.udacity.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nanodegree.udacity.popularmovies.model.MoviesResults;
import com.nanodegree.udacity.popularmovies.utils.MoviesRepository;


public class MoviesViewModel extends ViewModel {
    private MutableLiveData<MoviesResults> mMoviesLiveData;
    private MutableLiveData<String> mSortOption;

    public LiveData<MoviesResults> getMoviesLiveData() {
        if (mMoviesLiveData == null) {
            mMoviesLiveData = new MutableLiveData<>();
        }
        return mMoviesLiveData;
    }

    public LiveData<String> getSortOption() {
        if (mSortOption == null) {
            mSortOption = new MutableLiveData<>();
        }
        return mSortOption;
    }

    public void setSortOption(String s) {
        if (mSortOption == null) {
            mSortOption = new MutableLiveData<>();
        }
        mSortOption.setValue(s);
    }

    public void callApi() {
        MoviesRepository.callApi(mSortOption.getValue(), mMoviesLiveData);
    }

}
