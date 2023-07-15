package com.vincode.movieapps.di

import com.vincode.movieapps.data.source.MovieRepository
import com.vincode.movieapps.data.source.remote.RemoteDataSource
import com.vincode.movieapps.data.source.remote.network.ApiClient
import com.vincode.movieapps.domain.repository.IMovieRepository
import com.vincode.movieapps.domain.usecase.MovieInteractor
import com.vincode.movieapps.domain.usecase.MovieUseCase

object Injection {
    private fun provideRepository(): IMovieRepository {
        val remoteDataSource = RemoteDataSource.getInstance(ApiClient.provideApiService())
        return MovieRepository.getInstance(remoteDataSource)
    }

    fun provideMovieUsecase(): MovieUseCase{
        val repository = provideRepository()
        return MovieInteractor(repository)
    }
}