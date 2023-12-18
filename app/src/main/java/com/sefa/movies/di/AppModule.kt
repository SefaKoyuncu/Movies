package com.sefa.movies.di

import com.sefa.movies.BuildConfig
import com.sefa.movies.data.datasources.remote.datasource.RemoteDataSource
import com.sefa.movies.data.datasources.remote.interceptor.AuthInterceptor
import com.sefa.movies.data.datasources.remote.service.MovieService
import com.sefa.movies.data.mapper.MovieMapper
import com.sefa.movies.data.repository.MovieRepositoryImpl
import com.sefa.movies.domain.repository.MovieRepository
import com.sefa.movies.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule
{
    @Provides
    fun provideBaseURL() = Constants.BASE_URL

    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor;
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(BuildConfig.API_KEY))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL : String, okHttpClient: OkHttpClient) : MovieService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideMovieMapper(): MovieMapper {
        return MovieMapper()
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        remoteDataSource: RemoteDataSource,
        movieMapper: MovieMapper
    ): MovieRepository {
        return MovieRepositoryImpl(remoteDataSource, movieMapper)
    }
}