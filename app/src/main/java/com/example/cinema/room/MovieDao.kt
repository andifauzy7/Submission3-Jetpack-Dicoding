package com.example.cinema.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cinema.room.entity.MovieEntity
import com.example.cinema.room.entity.TVShowEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM moviesentities")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movie: MovieEntity)

    @Query("SELECT * FROM moviesentities WHERE movieId = :movieId")
    fun getMoviesById(movieId: String): MovieEntity

    @Query("SELECT * FROM moviesentities WHERE movieId = :movieId")
    fun getMoviesByIdDBLiveData(movieId: String): LiveData<MovieEntity>

    @Query("DELETE FROM moviesentities WHERE movieId = :movieId")
    fun deleteMovies(movieId: String)

    @Query("SELECT * FROM tvshowentities")
    fun getTVShow(): LiveData<List<TVShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTVShow(show: TVShowEntity)

    @Query("SELECT * FROM tvshowentities WHERE showId = :showId")
    fun getTVShowById(showId: String): LiveData<TVShowEntity>

    @Delete
    fun deleteTVShow(show: TVShowEntity)
}