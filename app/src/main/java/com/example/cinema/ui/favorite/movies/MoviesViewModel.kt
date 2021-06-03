package com.example.cinema.ui.favorite.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.cinema.data.MovieRepository
import com.example.cinema.room.entity.MovieEntity

class MoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getAllMovies() : LiveData<PagedList<MovieEntity>> = LivePagedListBuilder(movieRepository.getAllMoviesDB(), 4).build()

}