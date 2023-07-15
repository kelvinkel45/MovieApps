package com.vincode.movieapps.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vincode.movieapps.di.Injection
import com.vincode.movieapps.domain.usecase.MovieUseCase
import com.vincode.movieapps.ui.detail.DetailMovieViewModel
import com.vincode.movieapps.ui.list.MovieViewModel

class ViewModelFactory private constructor(private val movieUseCase: MovieUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance
                        ?: ViewModelFactory(
                            Injection.provideMovieUsecase()
                        )
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movieUseCase) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(movieUseCase) as T
            }
//            modelClass.isAssignableFrom(DetailTourismViewModel::class.java) -> {
//                DetailTourismViewModel(tourismUseCase) as T
//            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}