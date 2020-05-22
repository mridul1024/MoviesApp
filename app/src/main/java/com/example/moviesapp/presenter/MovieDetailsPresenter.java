package com.example.moviesapp.presenter;

import android.util.Log;

import com.example.moviesapp.model.MovieDetailsJson;
import com.example.moviesapp.moduleController.MovieDetailsModule;
import com.example.moviesapp.networkAPI.MovieClient;
import com.example.moviesapp.networkAPI.MovieService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.internal.EverythingIsNonNull;

public class MovieDetailsPresenter implements MovieDetailsModule.DetailsPresenter {
    private static final String TAG = "MovieDetailsPresenter";
    private MovieDetailsModule.DetailsView movieDetailView;

    private String MOVIE_ID;
    private String IMAGE_URL, ORIGINAL_NAME, USER_RATING, RELEASE_DATE, PLOT_SUMMARY;
    private static final String API_KEY = "";
    private static final String LANGUAGE = "en-US";


    public MovieDetailsPresenter(MovieDetailsModule.DetailsView view, int movie_id){
        this.movieDetailView = view;
        this.MOVIE_ID = String.valueOf(movie_id);
        
        if(MOVIE_ID.equals("0")){
            Log.d(TAG, "MovieDetailsPresenter: movie id is 0...no data to display");
        }
        initPresenter();
    }

    @Override
    public void initPresenter() {
        movieDetailView.initView();
        fetchDetails();
    }

    @Override
    public void fetchDetails() {
        movieDetailView.showProgressBar();
        MovieClient detailClient = new MovieClient();
        Retrofit retrofitObject = detailClient.getTrendingMovieClient();

        //service
        MovieService detailService = retrofitObject.create(MovieService.class);

        Call<MovieDetailsJson> movieDetailsJson = detailService.
                getMovieDetails(
                        MOVIE_ID,
                        API_KEY,
                        LANGUAGE);

        Log.d(TAG, "fetchDetails: "+ MOVIE_ID + "," + API_KEY + "," + LANGUAGE);
        
        if(movieDetailsJson == null){
            Log.d(TAG, "fetchDetails: movieDetailsJson null");
        }

        movieDetailsJson.enqueue(new Callback<MovieDetailsJson>() {
            @Override @EverythingIsNonNull
            public void onResponse(Call<MovieDetailsJson> call, Response<MovieDetailsJson> response) {
                movieDetailView.hideProgressBar();
                MovieDetailsJson jsonData = response.body();

                if(jsonData != null){
                    IMAGE_URL = "http://image.tmdb.org/t/p/w185" + jsonData.getPosterPath();
                    ORIGINAL_NAME = jsonData.getOriginalTitle();
                    USER_RATING = String.valueOf(jsonData.getVoteAverage());
                    RELEASE_DATE = jsonData.getReleaseDate();
                    PLOT_SUMMARY = jsonData.getOverview();

                    movieDetailView.displayMovieDetails(IMAGE_URL, ORIGINAL_NAME, USER_RATING, RELEASE_DATE, PLOT_SUMMARY);
                }

            }

            @Override @EverythingIsNonNull
            public void onFailure(Call<MovieDetailsJson> call, Throwable t) {
                String message = t.getMessage();
                Log.d(TAG, "onFailure: " + message);
            }
        });
    }
}
