package com.example.moviesapp.networkAPI;

import com.example.moviesapp.model.MovieDetailsJson;
import com.example.moviesapp.model.MovieJsonData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("3/trending/all/day")
    Call<MovieJsonData> getTrendingMovies(@Query("api_key") String apiKey);

    @GET("3/discover/movie")
    Call<MovieJsonData> getMostPopularMovies(@Query("api_key") String apiKey,
                                             @Query("language") String language,
                                             @Query("sort_by") String sortBy,
                                             @Query("page") String page);

    @GET("3/discover/movie")
    Call<MovieJsonData> getTopRatedMovies(@Query("api_key") String apiKey,
                                          @Query("language") String language,
                                          @Query("sort_by") String sortBy,
                                          @Query("page") String page,
                                          @Query("vote_count.gte") String voteCountGte,
                                          @Query("vote_count.lte") String voteCountLte,
                                          @Query("vote_average.gte") String voteAverageGte);

    @GET("3/movie/{movie_id}")
    Call<MovieDetailsJson> getMovieDetails(@Path("movie_id") String movieId,
                                           @Query("api_key") String apiKey,
                                           @Query("language") String language);
}
