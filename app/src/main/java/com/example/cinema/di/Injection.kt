@file:Suppress("UNUSED_PARAMETER")

package com.example.cinema.di

import android.content.Context
import com.example.cinema.data.MovieRepository

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        return MovieRepository.getInstance()
    }
}