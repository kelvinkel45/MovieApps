package com.vincode.movieapps.data.source

import com.vincode.movieapps.data.source.remote.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, RequestType>{

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading(null))
        when(val apiResponse = createCall().first()){
            is ApiResponse.Success->{
                emitAll(loadFromNetwork(apiResponse.data).map {
                    Resource.Success(it)
                })
            }
            is ApiResponse.Error->{
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract fun loadFromNetwork(data: RequestType): Flow<ResultType>

    fun asFlow(): Flow<Resource<ResultType>> = result



}