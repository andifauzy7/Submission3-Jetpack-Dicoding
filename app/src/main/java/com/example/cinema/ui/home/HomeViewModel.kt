package com.example.cinema.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.data.MovieRepository
import com.example.cinema.data.response.ResultMovies
import com.example.cinema.data.response.ResultTVShow
import com.example.cinema.utils.Resource

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMoviesPopular(): LiveData<Resource<List<ResultMovies>>> {
        return movieRepository.getMoviesPopular()
    }

    fun getTVShowPopular(): LiveData<Resource<List<ResultTVShow>>> {
        return movieRepository.getTVShowPopular()
    }
}