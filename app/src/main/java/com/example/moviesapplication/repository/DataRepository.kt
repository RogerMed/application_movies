package com.example.moviesapplication.repository


import com.example.moviesapplication.api.response.GetMovieResponse
import com.example.moviesapplication.api.response.request.MovieApi


class DataRepository(private val moviesApi: MovieApi) {

    suspend fun getPopularMovies(page: Int): GetMovieResponse {
        return moviesApi.getPopularMovies(page)
    }

    suspend fun getTopRatedMovies(page: Int): GetMovieResponse {
        return moviesApi.getTopRatedMovies(page)
    }

    suspend fun getUpcomingMovies(page: Int): GetMovieResponse {
        return moviesApi.getUpcomingMovies(page)
    }
}