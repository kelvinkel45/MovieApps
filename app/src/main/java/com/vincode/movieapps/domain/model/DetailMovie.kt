package com.vincode.movieapps.domain.model

data class DetailMovie(
    val title: String,
    val backdropPath: String,
    val id: Int,
    val overview: String,
    val runtime: Int,
    val posterPath: String,
    val voteAverage: Double,
    val genres: List<String>
)
