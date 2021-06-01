package com.example.cinema.ui.home

import com.example.cinema.data.MovieRepository
import com.example.cinema.utils.DataDummy
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        viewModel = HomeViewModel(movieRepository)
    }

    @Test
    fun getMoviesPopular() {
        `when`(movieRepository.getMoviesPopular()).thenReturn(DataDummy.getMoviesPopular())
        val resultViewModel = viewModel.getMoviesPopular()
        verify(movieRepository).getMoviesPopular()
        assertNotNull(resultViewModel)
        assertEquals(1, resultViewModel.value?.data?.size)
    }

    @Test
    fun getTVShowPopular() {
        `when`(movieRepository.getTVShowPopular()).thenReturn(DataDummy.getTVShowPopular())
        val resultViewModel = viewModel.getTVShowPopular()
        verify(movieRepository).getTVShowPopular()
        assertNotNull(resultViewModel)
        assertEquals(1, resultViewModel.value?.data?.size)
    }
}