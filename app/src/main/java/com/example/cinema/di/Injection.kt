@file:Suppress("UNUSED_PARAMETER")

package com.example.cinema.di

import android.content.Context
import com.example.cinema.data.LocalDataSource
import com.example.cinema.data.MovieRepository
import com.example.cinema.data.RemoteDataSource
import com.example.cinema.room.MovieDatabase

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val database = MovieDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())

        return MovieRepository.getInstance(remoteDataSource, localDataSource)
    }
}