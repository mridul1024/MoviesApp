package com.example.moviesapp.networkAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieClient {
    private static final String BASE_URL = "https://api.themoviedb.org/";

    private Retrofit trendingMovieClient;

    //Function for creating a client to receive movie data
    public Retrofit getTrendingMovieClient(){
        if(trendingMovieClient == null) {
            trendingMovieClient = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return trendingMovieClient;
    }
}
