package com.example.movierating.domain.di.repositories

import com.example.movierating.domain.network.apiRepositories.movesRequests.MovieApiRepository
import com.example.movierating.domain.network.apiRepositories.movesRequests.MovieApiRepositoryImpl
import com.example.movierating.domain.network.apiRepositories.movesRequests.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieRepositoryModule {

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)

    @Provides
    @Singleton
    fun provideMovieApiRepository(movieApiService: MovieApiService): MovieApiRepository =
        MovieApiRepositoryImpl(movieApiService)
}