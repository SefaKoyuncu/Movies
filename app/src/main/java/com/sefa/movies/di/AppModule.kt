package com.sefa.movies.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sefa.movies.BuildConfig
import com.sefa.movies.data.datasources.local.MovieDAO
import com.sefa.movies.data.datasources.local.MovieDatabase
import com.sefa.movies.data.datasources.remote.interceptor.AuthInterceptor
import com.sefa.movies.data.datasources.remote.service.MovieService
import com.sefa.movies.data.mapper.MovieMapper
import com.sefa.movies.data.repository.MovieRepositoryImpl
import com.sefa.movies.domain.repository.MovieRepository
import com.sefa.movies.domain.usecase.DeleteMovieFromDbUseCase
import com.sefa.movies.domain.usecase.GetIsMovieExistInDbUseCase
import com.sefa.movies.domain.usecase.InsertMovieToDbUseCase
import com.sefa.movies.presentation.viewmodel.DetailsViewModel
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
        movieService: MovieService,
        movieDAO: MovieDAO
    ): MovieRepository {
        return MovieRepositoryImpl(movieService,movieDAO)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context) : MovieDatabase
    {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,"CountryDatabase")
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryDao(database: MovieDatabase) : MovieDAO =
        database.movieDao()

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideGetIsMovieExistInDbUseCase(movieRepository: MovieRepository): GetIsMovieExistInDbUseCase
        = GetIsMovieExistInDbUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideInsertMovieToDbUseCase(movieRepository: MovieRepository): InsertMovieToDbUseCase
        = InsertMovieToDbUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideDeleteMovieFromDbUseCase(movieRepository: MovieRepository): DeleteMovieFromDbUseCase
        = DeleteMovieFromDbUseCase(movieRepository)

    @Provides
    fun provideDetailsViewModel(
        getIsMovieExistInDb: GetIsMovieExistInDbUseCase,
        insertMovieToDbUseCase: InsertMovieToDbUseCase,
        deleteMovieFromDbUseCase: DeleteMovieFromDbUseCase
    ): DetailsViewModel {
        return DetailsViewModel(
            getIsMovieExistInDb,
            insertMovieToDbUseCase,
            deleteMovieFromDbUseCase
        )
    }
}