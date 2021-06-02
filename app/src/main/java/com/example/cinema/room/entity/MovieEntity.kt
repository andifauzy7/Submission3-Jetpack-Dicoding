package com.example.cinema.room.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviesentities")
data class MovieEntity(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "movieId")
        var movieId: String,

        @ColumnInfo(name = "movieTitle")
        var movieTitle: String,

        @ColumnInfo(name = "moviePoster")
        var moviePoster: String,
)