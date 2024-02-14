package com.sefa.movies.di

import com.sefa.movies.data.datasources.local.MovieDAO
import com.sefa.movies.data.datasources.remote.service.MovieService
import com.sefa.movies.data.repository.MovieRepositoryImpl
import com.sefa.movies.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule
{
    @Singleton
    @Provides
    fun provideMovieRepository(
        movieService: MovieService,
        movieDAO: MovieDAO
    ): MovieRepository {
        return MovieRepositoryImpl(movieService,movieDAO)
    }
}