package com.sefa.movies.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.sefa.movies.data.datasources.remote.RemoteDataSources
import com.sefa.movies.data.model.MoviesResponse
import com.sefa.movies.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetDataRepository

@Inject constructor(private val remoteDataSources: RemoteDataSources, private val context: Context)
{
    suspend fun fetchData() : Flow<Resource<MoviesResponse?>?>
    {
        return flow {
            val result = remoteDataSources.getPopularMovies()
            if (result.isSuccessful)
                emit(Resource.Success(result.body()))
        }.flowOn(Dispatchers.IO)
    }
}