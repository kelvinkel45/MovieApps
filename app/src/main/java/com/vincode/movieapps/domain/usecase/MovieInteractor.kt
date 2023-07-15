package com.vincode.movieapps.domain.usecase

import com.vincode.movieapps.data.source.Resource
import com.vincode.movieapps.domain.model.DetailMovie
import com.vincode.movieapps.domain.model.Movie
import com.vincode.movieapps.domain.model.ReviewMovie
import com.vincode.movieapps.domain.model.Video
import com.vincode.movieapps.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow


class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        movieRepository.getAllMovies()

    override fun getDetailMovie(movieId:String): Flow<Resource<DetailMovie>> =
        movieRepository.getDetailMovie(movieId)

    override fun getListReviewMovie(movieId: String): Flow<Resource<List<ReviewMovie>>> =
        movieRepository.getListReviewMovie(movieId)

    override fun getVideoMovies(movieId: String): Flow<Resource<List<Video>>> =
        movieRepository.getVideoMovies(movieId)

}