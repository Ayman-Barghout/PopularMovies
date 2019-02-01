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
    private MutableLiveData<Integer> pageNumber;
    private MoviesResultsWrapper moviesResultsWrapper;

    public LiveData<MoviesResultsWrapper> getMoviesLiveData() {
        if (mMoviesLiveData == null) {
            moviesResultsWrapper = new MoviesResultsWrapper();
            mMoviesLiveData = new MutableLiveData<>();
        }
        return mMoviesLiveData;
    }

    public LiveData<Integer> getPageNumber() {
        if (pageNumber == null) {
            pageNumber = new MutableLiveData<>();
            pageNumber.setValue(1);
        }
        return pageNumber;
    }

    public void setPageNumber(int num) {

        if (pageNumber == null) {
            pageNumber = new MutableLiveData<>();
        }

        pageNumber.setValue(num);
    }

    //    TODO: Add logic to make the list feed the UI
    public LiveData<List<Result>> getMoviesList() {
        if (moviesList == null) {
            moviesList = new MutableLiveData<>();
        }

        return moviesList;
    }

    public void addToMoviesList(List<Result> newList) {
        List<Result> resultsList;

        if (moviesList == null) {
            moviesList = new MutableLiveData<>();
            resultsList = newList;
            moviesList.setValue(resultsList);
        } else {
            resultsList = moviesList.getValue();
            resultsList.addAll(newList);
            moviesList.setValue(resultsList);
        }
    }

    public void setSortOption(String s) {
        if (mSortOption == null) {
            mSortOption = new MutableLiveData<>();
        }
        mSortOption.setValue(s);
    }

    public void callApi() {
        MoviesRepository.getInstance().callApi(mSortOption == null ? "popularity.desc" : mSortOption.getValue()
                , moviesResultsWrapper, mMoviesLiveData, pageNumber.getValue());
    }


    public void resetMessage() {
        moviesResultsWrapper.setMessage(null);
    }
}
