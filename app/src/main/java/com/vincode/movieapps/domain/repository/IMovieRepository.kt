package com.vincode.movieapps.domain.repository

import com.vincode.movieapps.data.source.Resource
import com.vincode.movieapps.domain.model.DetailMovie
import com.vincode.movieapps.domain.model.Movie
import com.vincode.movieapps.domain.model.ReviewMovie
import com.vincode.movieapps.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getAllMovies() : Flow<Resource<List<Movie>>>

    fun getDetailMovie(movieId:String) : Flow<Resource<DetailMovie>>

    fun getListReviewMovie(movieId: String) : Flow<Resource<List<ReviewMovie>>>

    fun getVideoMovies(movieId: String) : Flow<Resource<List<Video>>>
}