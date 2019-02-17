package com.nanodegree.udacity.popularmovies.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class Movie {

    @SerializedName("vote_count")
    @Expose
    @ColumnInfo(name = "vote_count")
    private int voteCount;
    @ColumnInfo
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("video")
    @Expose
    @Ignore
    private boolean video;
    @SerializedName("vote_average")
    @Expose
    @ColumnInfo(name = "vote_average")
    private double voteAverage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("popularity")
    @Expose
    private double popularity;
    @SerializedName("poster_path")
    @Expose
    @NonNull
    @ColumnInfo(name = "poster_path")
    private String posterPath;
    @SerializedName("original_language")
    @Expose
    @ColumnInfo(name = "original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    @ColumnInfo(name = "original_title")
    private String originalTitle;
    @SerializedName("genre_ids")
    @Expose
    @Ignore
    private List<Integer> genreIds = null;
    @SerializedName("backdrop_path")
    @Expose
    @ColumnInfo(name ="backdrop_path" )
    private String backdropPath;
    @SerializedName("adult")
    @Expose
    private boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    @ColumnInfo(name = "release_date")
    private String releaseDate;

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return  "Movie".concat("voteCount" + voteCount).concat("id" + id).concat("video" + video).concat("voteAverage" + voteAverage).concat("title" + title).concat("popularity" + popularity).concat("posterPath" + posterPath).concat("originalLanguage" + originalLanguage).concat("originalTitle" + originalTitle).concat("genreIds" + genreIds).concat("backdropPath" + backdropPath).concat("adult" + adult).concat("overview" + overview).concat("releaseDate" + releaseDate);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Movie){
            return ((Movie) obj).getId() == this.getId();
        }
        return false;
    }
}