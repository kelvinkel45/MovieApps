package com.vincode.movieapps.data.source

import com.vincode.movieapps.data.source.remote.RemoteDataSource
import com.vincode.movieapps.data.source.remote.response.ApiResponse
import com.vincode.movieapps.data.source.remote.response.DetailMovieResponse
import com.vincode.movieapps.data.source.remote.response.ResultsItem
import com.vincode.movieapps.data.source.remote.response.ReviewResultResponse
import com.vincode.movieapps.data.source.remote.response.VideoResultResponse
import com.vincode.movieapps.domain.model.DetailMovie
import com.vincode.movieapps.domain.model.Movie
import com.vincode.movieapps.domain.model.ReviewMovie
import com.vincode.movieapps.domain.model.Video
import com.vincode.movieapps.domain.repository.IMovieRepository
import com.vincode.movieapps.util.DataMapper.mapDetailMovieResponseToDomain
import com.vincode.movieapps.util.DataMapper.mapListMoviesResponseToDomain
import com.vincode.movieapps.util.DataMapper.mapReviewMovieResponseToDomain
import com.vincode.movieapps.util.DataMapper.mapVideoMovieResponseToDomain
import kotlinx.coroutines.flow.Flow

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource

): IMovieRepository{

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData)
            }
    }

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultsItem>>(){
            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>>  =
                remoteDataSource.getAllMovies()

            override fun loadFromNetwork(data: List<ResultsItem>): Flow<List<Movie>> =
                mapListMoviesResponseToDomain(data)

        }.asFlow()

    override fun getDetailMovie(movieId:String): Flow<Resource<DetailMovie>> =
        object : NetworkBoundResource<DetailMovie,DetailMovieResponse>(){
            override suspend fun createCall(): Flow<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovies(movieId)


            override fun loadFromNetwork(data: DetailMovieResponse): Flow<DetailMovie> =
                mapDetailMovieResponseToDomain(data)

        }.asFlow()

    override fun getListReviewMovie(movieId: String): Flow<Resource<List<ReviewMovie>>> =
        object : NetworkBoundResource<List<ReviewMovie>, List<ReviewResultResponse>>(){
            override suspend fun createCall(): Flow<ApiResponse<List<ReviewResultResponse>>> =
                remoteDataSource.getReviewMovies(movieId)


            override fun loadFromNetwork(data: List<ReviewResultResponse>): Flow<List<ReviewMovie>> =
                mapReviewMovieResponseToDomain(data)


        }.asFlow()

    override fun getVideoMovies(movieId: String): Flow<Resource<List<Video>>> =
        object : NetworkBoundResource<List<Video>, List<VideoResultResponse>>(){
            override suspend fun createCall(): Flow<ApiResponse<List<VideoResultResponse>>> =
                remoteDataSource.getVideoMovies(movieId)

            override fun loadFromNetwork(data: List<VideoResultResponse>): Flow<List<Video>> =
                mapVideoMovieResponseToDomain(data)

        }.asFlow()


}