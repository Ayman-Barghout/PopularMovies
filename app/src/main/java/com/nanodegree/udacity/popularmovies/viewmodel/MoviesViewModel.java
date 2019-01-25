package com.nanodegree.udacity.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nanodegree.udacity.popularmovies.model.MoviesResultWrapper;

public class MoviesViewModel extends ViewModel {
    private MutableLiveData<MoviesResultWrapper> moviesLiveData;

    public LiveData<MoviesResultWrapper> getMoviesLiveData() {
        if(moviesLiveData == null){
            moviesLiveData = new MutableLiveData<>();
        }
        return moviesLiveData;
    }


}
