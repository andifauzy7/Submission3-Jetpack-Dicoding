package com.example.cinema.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.cinema.data.MovieRepository
import com.example.cinema.room.entity.TVShowEntity

class TVShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getAllTVShow() : LiveData<PagedList<TVShowEntity>> = LivePagedListBuilder(movieRepository.getAllTVShowDB(), 4).build()
}