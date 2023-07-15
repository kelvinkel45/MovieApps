package com.vincode.movieapps.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.vincode.movieapps.data.source.Resource
import com.vincode.movieapps.domain.model.Movie
import com.vincode.movieapps.domain.usecase.MovieUseCase
import com.vincode.movieapps.ui.DataResource

class MovieViewModel constructor(
    private val movieUseCase: MovieUseCase) : ViewModel() {

    private val loadTrigger = MutableLiveData(Unit)

    fun refreshData() {
        loadTrigger.value = Unit
    }

    fun getMovies(): LiveData<DataResource<List<Movie>>> = loadTrigger.switchMap {
        Transformations.map(movieUseCase.getAllMovies().asLiveData()){
            when(it){
                is Resource.Success -> {
                    if (it.data.isNullOrEmpty())
                        DataResource.Empty()
                    else
                        DataResource.Success(
                            it.data
                        )
                }
                is Resource.Loading->{
                    DataResource.Loading()
                }
                is Resource.Error->{
                    DataResource.Error(it.message.toString())
                }

            }
        }
    }
}
