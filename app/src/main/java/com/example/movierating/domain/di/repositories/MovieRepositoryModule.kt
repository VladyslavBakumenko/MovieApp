package com.example.movierating.domain.di.repositories

import com.example.movierating.domain.network.apiRepository.MovieApiRepository
import com.example.movierating.data.repository.MovieApiRepositoryImpl
import com.example.movierating.domain.network.apiRepository.MovieApiService
import com.example.movierating.domain.network.paging.MoviePagingSource
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
    fun provideMovieApiRepository(
        movieApiService: MovieApiService,
        moviePagingSourceFactory: MoviePagingSource.Factory
    ): MovieApiRepository = MovieApiRepositoryImpl(
        movieApiService,
        moviePagingSourceFactory
    )
}