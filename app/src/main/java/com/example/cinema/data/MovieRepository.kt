package com.example.cinema.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.cinema.data.response.*
import com.example.cinema.room.entity.MovieEntity
import com.example.cinema.room.entity.TVShowEntity
import com.example.cinema.utils.Resource

class MovieRepository(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource) {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource): MovieRepository =
                instance ?: synchronized(this) {
                    MovieRepository(remoteData, localData).apply {
                        instance = this
                    }
                }
    }

    fun getAllMoviesDB() : DataSource.Factory<Int, MovieEntity>  = localDataSource.getAllMoviesDB()

    fun insertMoviesDB(movie : MovieEntity){
        localDataSource.insertMoviesDB(movie)
    }

    fun getMoviesByIdDB(movieId: String) : MovieEntity = localDataSource.getMoviesByIdDB(movieId)

    fun getMoviesByIdDBLiveData(movieId: String) : LiveData<MovieEntity> = localDataSource.getMoviesByIdDBLiveData(movieId)

    fun deleteMoviesDB(movieId: String) {
        localDataSource.deleteMoviesDB(movieId)
    }

    fun getAllTVShowDB() : DataSource.Factory<Int, TVShowEntity> = localDataSource.getAllTVShowDB()

    fun insertTVShowDB(show : TVShowEntity){
        localDataSource.insertTVShowDB(show)
    }

    fun getTVShowByIdDB(showId: String) : TVShowEntity = localDataSource.getTVShowByIdDB(showId)

    fun getTVShowByIdDBLiveData(showId: String) : LiveData<TVShowEntity> = localDataSource.getTVShowByIdDBLiveData(showId)

    fun deleteTVShowDB(showId: String) {
        localDataSource.deleteTVShowDB(showId)
    }


    fun getMoviesPopular() : MutableLiveData<Resource<List<ResultMovies>>> = remoteDataSource.getMoviesPopular()

    fun getTVShowPopular() : MutableLiveData<Resource<List<ResultTVShow>>> = remoteDataSource.getTVShowPopular()

    fun getMoviesSearch(keyword : String) : MutableLiveData<Resource<List<ResultMovies>>> = remoteDataSource.getMoviesSearch(keyword)

    fun getShowSearch(keyword : String) : MutableLiveData<Resource<List<ResultTVShow>>> = remoteDataSource.getShowSearch(keyword)

    fun getMovieDetail(movieId: String) : MutableLiveData<Resource<ResponseMovieDetail>> = remoteDataSource.getMovieDetail(movieId)

    fun getTVShowDetail(tvId: String) : MutableLiveData<Resource<ResponseTVShowDetail>>  = remoteDataSource.getTVShowDetail(tvId)
}