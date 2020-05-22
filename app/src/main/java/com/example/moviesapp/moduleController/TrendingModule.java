package com.example.moviesapp.moduleController;

import android.view.Menu;

import com.example.moviesapp.adapter.MovieAdapter;


public interface TrendingModule {
    interface Model{
    }

    interface Presenter{
        void initPresenter();
        void fetchData();
    }

    interface View{
        void initView();
        void setupSharedPreferenceListener();
        void displayMovieData(MovieAdapter adapter);
        void progressBarVisible();
        void recyclerViewVisible();
        void noResponse();
        void openSettings();
        void showDetails(int itemId);
    }
}
