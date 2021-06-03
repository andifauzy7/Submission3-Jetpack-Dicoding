package com.example.cinema.ui.detail.movies

import com.example.cinema.data.MovieRepository
import com.example.cinema.room.entity.MovieEntity
import com.example.cinema.utils.DataDummy
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMoviesViewModelTest {
    private lateinit var viewModel: DetailMoviesViewModel

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        viewModel = DetailMoviesViewModel(movieRepository)
    }

    @Test
    fun getMovieDetail() {
        Mockito.`when`(movieRepository.getMovieDetail("1")).thenReturn(DataDummy.getMovieDetail("1"))
        val resultViewModel = viewModel.getMovieDetail("1")
        Mockito.verify(movieRepository).getMovieDetail("1")
        Assert.assertNotNull(resultViewModel)
        Assert.assertEquals(1, resultViewModel.value?.data?.id)
    }

    @Test
    fun getMovieFavorite() {
        val movieEntity = MovieEntity("1", "Dummy", "Poster")
        Mockito.`when`(movieRepository.getMoviesByIdDB("1")).thenReturn(movieEntity)
        val resultViewModel = viewModel.getMovieFavorite("1")
        Mockito.verify(movieRepository).getMoviesByIdDB("1")
        Assert.assertNotNull(resultViewModel)
        Assert.assertEquals(movieEntity, resultViewModel)
    }
}