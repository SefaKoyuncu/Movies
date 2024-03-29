package com.sefa.movies.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sefa.data.datasources.local.MovieDAO
import com.sefa.data.datasources.local.MovieDatabase
import com.sefa.data.datasources.remote.interceptor.AuthInterceptor
import com.sefa.data.datasources.remote.service.MovieService
import com.sefa.movies.BuildConfig
import com.sefa.movies.util.Constants
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
object DataModule
{
    @Provides
    fun provideBaseURL() = Constants.BASE_URL

    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient
    {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(BuildConfig.API_KEY))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL: String, okHttpClient: OkHttpClient): MovieService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MovieDatabase
    {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java, "MovieDatabase")
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryDao(database: MovieDatabase): MovieDAO =
        database.movieDao()

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }
}