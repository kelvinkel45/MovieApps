package com.vincode.movieapps.util

import com.vincode.movieapps.data.source.remote.response.DetailMovieResponse
import com.vincode.movieapps.data.source.remote.response.ResultsItem
import com.vincode.movieapps.data.source.remote.response.ReviewResultResponse
import com.vincode.movieapps.data.source.remote.response.VideoResultResponse
import com.vincode.movieapps.domain.model.DetailMovie
import com.vincode.movieapps.domain.model.Movie
import com.vincode.movieapps.domain.model.ReviewMovie
import com.vincode.movieapps.domain.model.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapListMoviesResponseToDomain(input : List<ResultsItem>) : Flow<ArrayList<Movie>> {
        val listMovies = ArrayList<Movie>()
        input.map {
            val movie = Movie(
                it.id,
                it.title,
                it.posterPath
            )
            listMovies.add(movie)
        }
        return flowOf(listMovies)
    }

    fun mapReviewMovieResponseToDomain(input: List<ReviewResultResponse>) : Flow<ArrayList<ReviewMovie>>{
        val listReview = ArrayList<ReviewMovie>()
        input.map {
            val review = ReviewMovie(
                it.author,
                it.content,
                it.createdAt
            )
            listReview.add(review)
        }
        return flowOf(listReview)
    }

    fun mapVideoMovieResponseToDomain(input: List<VideoResultResponse>) : Flow<ArrayList<Video>>{
        val listVideo = ArrayList<Video>()
        input.map {
            val video = Video(
                it.type,
                it.key
            )
            listVideo.add(video)
        }
        return flowOf(listVideo)
    }

    fun mapDetailMovieResponseToDomain(input : DetailMovieResponse) : Flow<DetailMovie>{
        val genres = ArrayList<String>()

        input.genres.forEach {
            genres.add(it.name)
        }

        return flowOf(DetailMovie(
            input.title,
            input.backdropPath,
            input.id,
            input.overview,
            input.runtime,
            input.posterPath,
            input.voteAverage,
            genres
        ))
    }

}