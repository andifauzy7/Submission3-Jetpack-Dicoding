package com.example.cinema.ui.explore

import com.example.cinema.data.MovieRepository
import com.example.cinema.utils.DataDummy
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ExploreViewModelTest {
    private lateinit var viewModel: ExploreViewModel

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        viewModel = ExploreViewModel(movieRepository)
    }

    @Test
    fun getMoviesSearch() {
        Mockito.`when`(movieRepository.getMoviesSearch("Flash")).thenReturn(DataDummy.getMoviesSearch("Flash"))
        val resultViewModel = viewModel.getMoviesSearch("Flash")
        Mockito.verify(movieRepository).getMoviesSearch("Flash")
        Assert.assertNotNull(resultViewModel)
        Assert.assertEquals(1, resultViewModel.value?.data?.size)
    }

    @Test
    fun getShowSearch() {
        Mockito.`when`(movieRepository.getShowSearch("Flash")).thenReturn(DataDummy.getShowSearch("Flash"))
        val resultViewModel = viewModel.getShowSearch("Flash")
        Mockito.verify(movieRepository).getShowSearch("Flash")
        Assert.assertNotNull(resultViewModel)
        Assert.assertEquals(1, resultViewModel.value?.data?.size)
    }
}