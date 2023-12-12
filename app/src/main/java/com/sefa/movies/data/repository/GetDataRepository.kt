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

    fun isOnline(): Boolean
    {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }


    /* return flow {
            if (isOnline()) {
                try {
                    val result = remoteDataSources.getPopularMovies()
                    if (result.isSuccessful) {
                        emit(Resource.Success(result.body()))
                    } else {
                        emit(Resource.Error(result.message()))
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(e.message))
                }
            } else {
                emit(Resource.Error("No Internet connection"))
            }
        }.flowOn(Dispatchers.IO)*/
}