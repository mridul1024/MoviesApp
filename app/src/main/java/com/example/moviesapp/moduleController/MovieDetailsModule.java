package com.example.moviesapp.moduleController;

public interface MovieDetailsModule {

    interface DetailsModel{
    }

    interface DetailsPresenter{
        void initPresenter();
        void fetchDetails();
    }

    interface DetailsView{
        void initView();
        void createSupportActionBar();
        void showProgressBar();
        void hideProgressBar();
        void displayMovieDetails(String imageUrl, String originalName, String userRating, String releaseDate, String plotSummary);
    }
}
