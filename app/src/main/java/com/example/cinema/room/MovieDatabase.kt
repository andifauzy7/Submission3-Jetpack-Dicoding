package com.example.cinema.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinema.room.entity.MovieEntity
import com.example.cinema.room.entity.TVShowEntity

@Database(entities = [MovieEntity::class, TVShowEntity::class],
        version = 1,
        exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
                INSTANCE ?: synchronized(this) {
                    Room.databaseBuilder(
                            context.applicationContext,
                            MovieDatabase::class.java,
                            "Movies.db"
                    ).allowMainThreadQueries().build().apply {
                        INSTANCE = this
                    }
                }
    }
}