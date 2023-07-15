package com.vincode.movieapps.data.source.remote.network

import com.vincode.movieapps.data.source.remote.response.DetailMovieResponse
import com.vincode.movieapps.data.source.remote.response.MovieResponse
import com.vincode.movieapps.data.source.remote.response.ReviewResponse
import com.vincode.movieapps.data.source.remote.response.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getListMovie (
        @Query("language") language : String = "en-US",
        @Query("page") page : String = "1"
    ) : MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getDataDetailMovie(
        @Path("movie_id") movieId: String,
        @Query("language") language : String = "en-US",
    ) : DetailMovieResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviewMovie(
        @Path("movie_id") movieId: String,
        @Query("language") language : String = "en-US",
    ) : ReviewResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getVideoMovie(
        @Path("movie_id") movieId: String,
        @Query("language") language : String = "en-US",
    ) : VideoResponse
}