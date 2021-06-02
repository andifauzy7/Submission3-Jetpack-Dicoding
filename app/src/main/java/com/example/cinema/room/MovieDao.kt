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

    @Delete
    fun deleteMovies(movie: MovieEntity)

    @Query("SELECT * FROM tvshowentities")
    fun getTVShow(): LiveData<List<TVShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTVShow(show: TVShowEntity)

    @Delete
    fun deleteTVShow(show: TVShowEntity)
}