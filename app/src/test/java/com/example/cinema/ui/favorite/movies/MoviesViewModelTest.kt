@file:Suppress("UNCHECKED_CAST", "DEPRECATION")

package com.example.cinema.ui.favorite.movies

import androidx.paging.DataSource
import com.example.cinema.data.MovieRepository
import com.example.cinema.room.entity.MovieEntity
import com.example.cinema.ui.utils.PagedListUtil
import com.example.cinema.utils.DataDummy
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {
    private lateinit var viewModel: MoviesViewModel

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(movieRepository)
    }

    @Test
    fun getAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(movieRepository.getAllMoviesDB()).thenReturn(dataSourceFactory)
        viewModel.getAllMovies()

        val movieEntities = PagedListUtil.mockPagedList(DataDummy.generateMovieDB())
        Mockito.verify(movieRepository).getAllMoviesDB()
        Assert.assertNotNull(movieEntities)
        assertEquals(DataDummy.generateMovieDB().size, movieEntities.size)
    }
}