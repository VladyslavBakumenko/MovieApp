package com.example.movierating.domain.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movierating.data.restModels.Movie
import com.example.movierating.data.restModels.MovieListResponse
import com.example.movierating.domain.network.apiRepositories.movesRequests.MovieApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MoviePagingSource @AssistedInject constructor(
    private val movieApiService: MovieApiService,
    @Assisted("moviePagingSourceRequestType") private var moviePagingSourceRequestType: MoviePagingSourceRequestType,
    @Assisted("searchedMovieText") private var searchedMovieText: String? = null,
    @Assisted("movieId") private var movieId: Int? = null
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = getResponse(page)
            LoadResult.Page(
                data = response?.results!!,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun getResponse(page: Int): MovieListResponse? {
        return when (moviePagingSourceRequestType) {
            MoviePagingSourceRequestType.GET_BY_POPULAR -> {
                movieApiService.getPopularMovies(page = page).body()
            }

            MoviePagingSourceRequestType.GET_BY_TOP_RATED -> {
                movieApiService.getTopRatedMovies(page = page).body()
            }

            MoviePagingSourceRequestType.GET_UPCOMING -> {
                movieApiService.getUpcomingMovies(page = page).body()
            }

            MoviePagingSourceRequestType.GET_SIMILAR -> {
               val response = movieApiService.getSimilarMovies(
                    movieId = movieId ?: throw IllegalArgumentException("this param cant be null"),
                    page = page
                )
                return response.body()
            }

            MoviePagingSourceRequestType.GET_SEARCHED -> {
                movieApiService.getSearchedMovie(
                    searedMovieText = searchedMovieText
                        ?: throw IllegalArgumentException("this param cant be null"),
                    page = page
                ).body()
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("moviePagingSourceRequestType") moviePagingSourceRequestType: MoviePagingSourceRequestType,
            @Assisted("searchedMovieText") searchedMovieText: String? = null,
            @Assisted("movieId") movieId: Int? = null,
        ): MoviePagingSource
    }
}