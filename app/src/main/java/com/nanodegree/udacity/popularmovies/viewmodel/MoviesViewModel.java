package com.nanodegree.udacity.popularmovies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nanodegree.udacity.popularmovies.model.MoviesResultsWrapper;
import com.nanodegree.udacity.popularmovies.model.Result;
import com.nanodegree.udacity.popularmovies.utils.MoviesRepository;

import java.util.List;


public class MoviesViewModel extends ViewModel {
    private MutableLiveData<MoviesResultsWrapper> mMoviesLiveData;
    private MutableLiveData<String> mSortOption;
    private MutableLiveData<List<Result>> moviesList;
    private MoviesResultsWrapper moviesResultsWrapper;

    public LiveData<MoviesResultsWrapper> getMoviesLiveData() {
        if (mMoviesLiveData == null) {
            moviesResultsWrapper = new MoviesResultsWrapper();
            mMoviesLiveData = new MutableLiveData<>();
        }
        return mMoviesLiveData;
    }


//    TODO: Add logic to make the list feed the UI
    public LiveData<List<Result>> getMoviesList(){
        if(moviesList == null) {
            moviesList = new MutableLiveData<>();
        }

        return moviesList;
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
