package com.example.cinema.ui.detail.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.data.MovieRepository
import com.example.cinema.data.response.ResponseTVShowDetail
import com.example.cinema.room.entity.MovieEntity
import com.example.cinema.room.entity.TVShowEntity
import com.example.cinema.utils.Resource

class DetailTVShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getTVShowDetail(id : String): LiveData<Resource<ResponseTVShowDetail>> {
        return movieRepository.getTVShowDetail(id)
    }

    fun addShowFavorite(show : TVShowEntity){
        movieRepository.insertTVShowDB(show)
    }

    fun deleteShowFavorite(id: String){
        movieRepository.deleteTVShowDB(id)
    }

    fun getShowFavorite(id: String) : TVShowEntity {
        return movieRepository.getTVShowByIdDB(id)
    }

    fun isShowFavorite(id: String) : LiveData<TVShowEntity> {
        return movieRepository.getTVShowByIdDBLiveData(id)
    }
}