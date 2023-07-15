package com.vincode.movieapps.data.source.remote

import com.vincode.movieapps.data.source.remote.network.ApiService
import com.vincode.movieapps.data.source.remote.response.ApiResponse
import com.vincode.movieapps.data.source.remote.response.DetailMovieResponse
import com.vincode.movieapps.data.source.remote.response.ResultsItem
import com.vincode.movieapps.data.source.remote.response.ReviewResultResponse
import com.vincode.movieapps.data.source.remote.response.VideoResultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource private constructor(private val apiService: ApiService){

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    suspend fun getAllMovies(): Flow<ApiResponse<List<ResultsItem>>> =
        flow {
            try {
                val response = apiService.getListMovie()
                emit(ApiResponse.Success(response.results))
            }catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailMovies(movieId : String): Flow<ApiResponse<DetailMovieResponse>> =
        flow{
            try {
                val response = apiService.getDataDetailMovie(movieId)
                emit(ApiResponse.Success(response))
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)


    suspend fun getReviewMovies(movieId: String): Flow<ApiResponse<List<ReviewResultResponse>>> =
        flow {
            try {
                val response = apiService.getReviewMovie(movieId)
                emit(ApiResponse.Success(response.results))
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getVideoMovies(movieId: String): Flow<ApiResponse<List<VideoResultResponse>>> =
        flow {
            try {
                val response = apiService.getVideoMovie(movieId)
                emit(ApiResponse.Success(response.results))
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
}