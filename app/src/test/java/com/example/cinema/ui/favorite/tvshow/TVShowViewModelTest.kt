package com.example.cinema.ui.favorite.tvshow

import androidx.paging.DataSource
import com.example.cinema.data.MovieRepository
import com.example.cinema.room.entity.TVShowEntity
import com.example.cinema.ui.utils.PagedListUtil
import com.example.cinema.utils.DataDummy
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TVShowViewModelTest {
    private lateinit var viewModel: TVShowViewModel

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        viewModel = TVShowViewModel(movieRepository)
    }

    @Test
    fun getAllMShows() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TVShowEntity>
        Mockito.`when`(movieRepository.getAllTVShowDB()).thenReturn(dataSourceFactory)
        val resultViewModel = viewModel.getAllTVShow()

        val showEntities = PagedListUtil.mockPagedList(DataDummy.generateShowDB())
        Mockito.verify(movieRepository).getAllTVShowDB()
        Assert.assertNotNull(showEntities)
        assertEquals(DataDummy.generateMovieDB().size, showEntities.size)
    }
}