package com.example.cinema.ui.favorite.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.data.MovieRepository
import com.example.cinema.room.entity.MovieEntity

class MoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getAllMovies() : LiveData<List<MovieEntity>> = movieRepository.getAllMoviesDB()
}