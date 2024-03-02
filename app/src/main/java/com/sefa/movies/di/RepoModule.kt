package com.sefa.movies.di

import com.sefa.data.datasources.local.MovieDAO
import com.sefa.data.datasources.remote.service.MovieService
import com.sefa.data.repository.MovieRepository
import com.sefa.domain.gateway.MovieGateway
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
    ) : MovieGateway {
        return MovieRepository(movieService,movieDAO)
    }
}