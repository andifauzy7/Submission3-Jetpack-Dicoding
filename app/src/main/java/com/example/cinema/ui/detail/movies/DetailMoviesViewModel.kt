package com.example.cinema.ui.detail.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.data.MovieRepository
import com.example.cinema.data.response.ResponseMovieDetail
import com.example.cinema.utils.Resource

class DetailMoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getMovieDetail(id : String): LiveData<Resource<ResponseMovieDetail>> {
        return movieRepository.getMovieDetail(id)
    }
}