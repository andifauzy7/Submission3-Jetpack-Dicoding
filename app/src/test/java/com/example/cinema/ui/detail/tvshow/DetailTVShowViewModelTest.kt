package com.example.cinema.ui.detail.tvshow

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
class DetailTVShowViewModelTest {
    private lateinit var viewModel: DetailTVShowViewModel

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        viewModel = DetailTVShowViewModel(movieRepository)
    }
    @Test
    fun getTVShowDetail() {
        Mockito.`when`(movieRepository.getTVShowDetail("1")).thenReturn(DataDummy.getTVShowDetail("1"))
        val resultViewModel = viewModel.getTVShowDetail("1")
        Mockito.verify(movieRepository).getTVShowDetail("1")
        Assert.assertNotNull(resultViewModel)
        Assert.assertEquals(1, resultViewModel.value?.data?.id)
    }
}