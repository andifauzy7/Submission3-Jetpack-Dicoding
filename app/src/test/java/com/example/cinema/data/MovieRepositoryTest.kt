package com.example.cinema.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.example.cinema.room.MovieDao
import com.example.cinema.room.entity.MovieEntity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieDao: MovieDao

    @Mock
    private lateinit var mockContext: Context

    @Before
    fun setUp() {
        movieRepository = MovieRepository(mockContext)
    }

    @Test
    fun testGetAllMoviesDB() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(movieRepository.getAllMoviesDB()).thenReturn(dataSourceFactory)
        Mockito.`when`(movieDao.getMovies()).thenReturn(null)

        val resultViewModel = movieRepository.getAllMoviesDB()
        Mockito.verify(movieDao).getMovies()
    }

    @Test
    fun testInsertMoviesDB() {}

    @Test
    fun testGetMoviesByIdDB() {}

    @Test
    fun testGetMoviesByIdDBLiveData() {}

    @Test
    fun testDeleteMoviesDB() {}

    @Test
    fun testGetAllTVShowDB() {}

    @Test
    fun testInsertTVShowDB() {}

    @Test
    fun testGetTVShowByIdDB() {}

    @Test
    fun testGetTVShowByIdDBLiveData() {}

    @Test
    fun testDeleteTVShowDB() {}

    @Test
    fun testGetMoviesPopular() {}

    @Test
    fun testGetTVShowPopular() {}

    @Test
    fun testGetMoviesSearch() {}

    @Test
    fun testGetShowSearch() {}

    @Test
    fun testGetMovieDetail() {}

    @Test
    fun testGetTVShowDetail() {}
}