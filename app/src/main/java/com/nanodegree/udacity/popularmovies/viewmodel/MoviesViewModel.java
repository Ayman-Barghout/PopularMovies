package com.nanodegree.udacity.popularmovies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nanodegree.udacity.popularmovies.model.MoviesResultsWrapper;
import com.nanodegree.udacity.popularmovies.model.Result;
import com.nanodegree.udacity.popularmovies.utils.MoviesRepository;

import java.util.List;
import java.util.Objects;


public class MoviesViewModel extends ViewModel {
    private MutableLiveData<MoviesResultsWrapper> mMoviesLiveData;
    private MutableLiveData<String> mSortOption;
    private MutableLiveData<List<Result>> moviesList;
    private MutableLiveData<Integer> pageNumber;
    private MutableLiveData<Boolean> isDataLoading;
    private MoviesResultsWrapper moviesResultsWrapper;

    public LiveData<MoviesResultsWrapper> getMoviesLiveData() {
        if (mMoviesLiveData == null) {
            moviesResultsWrapper = new MoviesResultsWrapper();
            mMoviesLiveData = new MutableLiveData<>();
        }
        return mMoviesLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading(){
        if(isDataLoading == null){
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public LiveData<Integer> getPageNumber(){
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

    public LiveData<List<Result>> getMoviesList() {
        return moviesList;
    }

    public void addToMoviesList(List<Result> newList) {
        List<Result> resultsList;

        if (moviesList == null) {
            moviesList = new MutableLiveData<>();
            resultsList = newList;
            moviesList.setValue(resultsList);
        } else {
            resultsList = Objects.requireNonNull(moviesList.getValue());
            if(!resultsList.containsAll(newList)){
                resultsList.addAll(newList);
            }
            moviesList.setValue(resultsList);
        }
    }

    public void resetMoviesList(){
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
        MoviesRepository.getInstance().callApi(mSortOption == null ? "popularity.desc" : mSortOption.getValue()
                , moviesResultsWrapper, mMoviesLiveData, pageNumber.getValue(), getIsLoading());
    }


    public void resetMessage() {
        moviesResultsWrapper.setMessage(null);
    }
}
