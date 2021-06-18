package com.example.cinema.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.cinema.room.MovieDao
import com.example.cinema.room.entity.MovieEntity
import com.example.cinema.room.entity.TVShowEntity

class LocalDataSource private constructor(private val movieDao: MovieDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
                INSTANCE ?: LocalDataSource(movieDao).apply {
                    INSTANCE = this
                }

    }

    fun getAllMoviesDB() : DataSource.Factory<Int, MovieEntity>  = movieDao.getMovies()

    fun insertMoviesDB(movie : MovieEntity){
        movieDao.insertMovies(movie)
    }

    fun getMoviesByIdDB(movieId: String) : MovieEntity = movieDao.getMoviesById(movieId)

    fun getMoviesByIdDBLiveData(movieId: String) : LiveData<MovieEntity> = movieDao.getMoviesByIdDBLiveData(movieId)

    fun deleteMoviesDB(movieId: String) {
        movieDao.deleteMovies(movieId)
    }

    fun getAllTVShowDB() : DataSource.Factory<Int, TVShowEntity> = movieDao.getTVShow()

    fun insertTVShowDB(show : TVShowEntity){
        movieDao.insertTVShow(show)
    }

    fun getTVShowByIdDB(showId: String) : TVShowEntity = movieDao.getTVShowById(showId)

    fun getTVShowByIdDBLiveData(showId: String) : LiveData<TVShowEntity> = movieDao.getTVShowByIdDBLiveData(showId)

    fun deleteTVShowDB(showId: String) {
        movieDao.deleteTVShow(showId)
    }
}