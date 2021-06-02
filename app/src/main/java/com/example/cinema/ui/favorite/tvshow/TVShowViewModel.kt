package com.example.cinema.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.data.MovieRepository
import com.example.cinema.room.entity.TVShowEntity

class TVShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getAllTVShow() : LiveData<List<TVShowEntity>> = movieRepository.getAllTVShowDB()
}