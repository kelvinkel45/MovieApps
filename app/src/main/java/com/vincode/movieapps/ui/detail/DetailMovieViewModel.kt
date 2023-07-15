package com.vincode.movieapps.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.vincode.movieapps.data.source.Resource
import com.vincode.movieapps.domain.model.DetailMovie
import com.vincode.movieapps.domain.model.ReviewMovie
import com.vincode.movieapps.domain.model.Video
import com.vincode.movieapps.domain.usecase.MovieUseCase
import com.vincode.movieapps.ui.DataResource

class DetailMovieViewModel constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {

    private val refreshTrigger = MutableLiveData(Unit)

    fun refreshData(){
        refreshTrigger.value = Unit
    }

    fun getDetailMovie(movieId : String): LiveData<DataResource<DetailMovie>> = refreshTrigger.switchMap {
        Transformations.map(movieUseCase.getDetailMovie(movieId).asLiveData()){
            when(it){
                is Resource.Success -> {
                    if (it.data != null)
                        DataResource.Success(
                            it.data
                        )
                    else
                        DataResource.Empty()
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

    fun getAllReviewMovie(movieId: String) : LiveData<DataResource<List<ReviewMovie>>> = refreshTrigger.switchMap {
        Transformations.map(movieUseCase.getListReviewMovie(movieId).asLiveData()){
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

    fun getVideoMovies(movieId: String) : LiveData<DataResource<List<Video>>> = refreshTrigger.switchMap {
        Transformations.map(movieUseCase.getVideoMovies(movieId).asLiveData()){
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