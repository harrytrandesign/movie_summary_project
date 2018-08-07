package com.htdwps.udacitymovieprojectone.util;

import com.htdwps.udacitymovieprojectone.model.MovieResponse;
import com.htdwps.udacitymovieprojectone.model.ReviewList;
import com.htdwps.udacitymovieprojectone.model.TrailerList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HTDWPS on 5/29/18.
 */
public interface MovieApiService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewList> getMovieReviewsForMovie(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerList> getMovieTrailersForMovie(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

}
