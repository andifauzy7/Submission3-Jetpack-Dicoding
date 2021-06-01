package com.example.cinema.ui.detail.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.data.MovieRepository
import com.example.cinema.data.response.ResponseTVShowDetail
import com.example.cinema.utils.Resource

class DetailTVShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getTVShowDetail(id : String): LiveData<Resource<ResponseTVShowDetail>> {
        return movieRepository.getTVShowDetail(id)
    }
}