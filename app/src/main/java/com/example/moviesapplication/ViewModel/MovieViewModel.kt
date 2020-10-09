package com.example.moviesapplication.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapplication.api.response.Movie
import com.example.moviesapplication.repository.DataRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieViewModel(private val dataRepository: DataRepository) : ViewModel(){

    val popularMovie = MutableLiveData<List<Movie>>()
    val ratedMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies = MutableLiveData<List<Movie>>()
    val movieError :MutableLiveData<Throwable> = MutableLiveData()


    fun initializeAsync() {
        viewModelScope.launch {
            try {
                val popularResult = async { dataRepository.getPopularMovies(1) }
                val topRatedResult = async { dataRepository.getTopRatedMovies(1) }
                val upcomingResult = async { dataRepository.getUpcomingMovies(1) }

                popularMovie.postValue(popularResult.await().movies)
                ratedMovies.postValue(topRatedResult.await().movies)
                upcomingMovies.postValue(upcomingResult.await().movies)
            } catch (throwable: Throwable) {
                movieError.postValue(throwable)
            }
        }
    }



}