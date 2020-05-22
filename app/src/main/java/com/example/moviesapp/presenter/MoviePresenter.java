package com.example.moviesapp.presenter;

import com.example.moviesapp.adapter.MovieAdapter;
import com.example.moviesapp.model.MovieJsonData;
import com.example.moviesapp.moduleController.TrendingModule;
import com.example.moviesapp.networkAPI.MovieClient;
import com.example.moviesapp.networkAPI.MovieService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.internal.EverythingIsNonNull;

public class MoviePresenter implements TrendingModule.Presenter{
    private TrendingModule.View moduleView;
    private MovieAdapter.ClickListener listener;
    private MovieAdapter adapter;

    private static final String API_KEY = "";
    private String sort_key;

    private static final String trending = "trending";
    private static final String most_popular = "most_popular";
    private static final String top_rated = "top_rated";

    //POPULAR MOVIES
    private static final String LANGUAGE_MOST_POPULAR = "en-US";
    private static final String SORT_MOST_POPULAR = "popularity.desc";
    private static final String PAGE_MOST_POPULAR = "1";

    //TOP RATED MOVIES
    private static final String LANGUAGE_TOP_RATED = "en-US";
    private static final String SORT_TOP_RATED = "popularity.desc";
    private static final String PAGE_TOP_RATED = "1";
    private static final String VOTE_COUNT_GTE = "100";
    private static final String VOTE_COUNT_LTE = "1500";
    private static final String VOTE_AVERAGE_GTE = "8";

    public MoviePresenter(TrendingModule.View view, MovieAdapter.ClickListener listener, String key){
        this.moduleView = view;
        this.listener = listener;
        this.sort_key = key;
        moduleView.initView();
        initPresenter();
    }

    @Override
    public void initPresenter() {
        fetchData();
    }

    @Override
    public void fetchData() {
        moduleView.progressBarVisible();

        MovieClient movieClient = new MovieClient();
        Retrofit networkClient = movieClient.getTrendingMovieClient();

        //Starting a service to fetch JSON data
        MovieService trendingService = networkClient.create(MovieService.class);

        Call<MovieJsonData> movieData = trendingService.getTrendingMovies(API_KEY);

        //Fetching results through the service
        switch (sort_key) {
            case "":
            case trending:
                movieData = trendingService.getTrendingMovies(API_KEY);
                break;
            case most_popular:
                movieData = trendingService.
                        getMostPopularMovies(
                                API_KEY,
                                LANGUAGE_MOST_POPULAR,
                                SORT_MOST_POPULAR,
                                PAGE_MOST_POPULAR);
                break;
            case top_rated:
                movieData = trendingService.
                        getTopRatedMovies(
                                API_KEY,
                                LANGUAGE_TOP_RATED,
                                SORT_TOP_RATED,
                                PAGE_TOP_RATED,
                                VOTE_COUNT_GTE,
                                VOTE_COUNT_LTE,
                                VOTE_AVERAGE_GTE);
                break;
            default:
                moduleView.noResponse();
                break;
        }

        movieData.enqueue(new Callback<MovieJsonData>() {
            @Override @EverythingIsNonNull
            public void onResponse(Call<MovieJsonData> call, Response<MovieJsonData> response) {
                moduleView.recyclerViewVisible();
                MovieJsonData movieJsonData = response.body();
                adapter = new MovieAdapter(movieJsonData, listener);

                moduleView.displayMovieData(adapter);
            }

            @Override @EverythingIsNonNull
            public void onFailure(Call<MovieJsonData> call, Throwable t) {
                moduleView.noResponse();
            }
        });
    }

}