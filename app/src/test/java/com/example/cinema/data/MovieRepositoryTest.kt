@file:Suppress("UNCHECKED_CAST", "DEPRECATION")

package com.example.cinema.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.cinema.room.entity.MovieEntity
import com.example.cinema.room.entity.TVShowEntity
import com.example.cinema.ui.utils.PagedListUtil
import com.example.cinema.utils.DataDummy
import com.example.cinema.utils.Resource
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val movieRepository = MovieRepository(remote, local)

    private val movieResponse = DataDummy.generateMovieDB()
    private val movieResponseRemote = DataDummy.getMoviesPopular()
    private val showResponse = DataDummy.generateShowDB()
    private val showResponseRemote = DataDummy.getTVShowPopular()

    @Test
    fun testGetAllMoviesDB() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getAllMoviesDB()).thenReturn(dataSourceFactory)
        movieRepository.getAllMoviesDB()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateMovieDB()))
        verify(local).getAllMoviesDB()
        Assert.assertNotNull(movieEntities.data)
        assertEquals(movieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun testInsertMoviesDB() {
        val movieEntity = MovieEntity(movieId = "4", movieTitle = "Dummy 4", moviePoster = "Dummy Poster 4")
        val tempMovieResponse : ArrayList<MovieEntity> = ArrayList()

        Mockito.`when`(local.insertMoviesDB(movieEntity)).then {
            tempMovieResponse.addAll(movieResponse)
            tempMovieResponse.add(movieEntity)
        }

        movieRepository.insertMoviesDB(movieEntity)
        val movieEntities = Resource.success(PagedListUtil.mockPagedList(tempMovieResponse))
        verify(local).insertMoviesDB(movieEntity)
        Assert.assertNotNull(movieEntities.data)
        assertEquals(tempMovieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun testGetMoviesByIdDB() {
        val movie = MovieEntity(movieId = "1", movieTitle = "Dummy 1", moviePoster = "Dummy Poster 1")
        Mockito.`when`(local.getMoviesByIdDB("1")).thenReturn(movie)

        val movieEntities = movieRepository.getMoviesByIdDB("1")
        verify(local).getMoviesByIdDB("1")
        Assert.assertNotNull(movieEntities)
        assertEquals(movie.movieId, movieEntities.movieId)
    }

    @Test
    fun testGetMoviesByIdDBLiveData() {
        val movieLiveData = MutableLiveData<MovieEntity>()
        val movie = MovieEntity(movieId = "1", movieTitle = "Dummy 1", moviePoster = "Dummy Poster 1")
        movieLiveData.value = movie
        Mockito.`when`(local.getMoviesByIdDBLiveData("1")).thenReturn(movieLiveData)

        val movieEntities = movieRepository.getMoviesByIdDBLiveData("1")
        verify(local).getMoviesByIdDBLiveData("1")
        Assert.assertNotNull(movieEntities)
        assertEquals(movieLiveData.value!!.movieId, movieEntities.value!!.movieId)
    }

    @Test
    fun testDeleteMoviesDB() {
        val tempMovieResponse : ArrayList<MovieEntity> = ArrayList()
        tempMovieResponse.addAll(movieResponse)

        Mockito.`when`(local.deleteMoviesDB("1")).then {
            tempMovieResponse.removeAt(0)
        }

        movieRepository.deleteMoviesDB("1")
        val movieEntities = Resource.success(PagedListUtil.mockPagedList(tempMovieResponse))
        verify(local).deleteMoviesDB("1")
        Assert.assertNotNull(movieEntities.data)
        assertEquals(tempMovieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun testGetAllTVShowDB() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TVShowEntity>
        Mockito.`when`(local.getAllTVShowDB()).thenReturn(dataSourceFactory)
        movieRepository.getAllTVShowDB()

        val showEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateShowDB()))
        verify(local).getAllTVShowDB()
        Assert.assertNotNull(showEntities.data)
        assertEquals(showResponse.size.toLong(), showEntities.data?.size?.toLong())
    }

    @Test
    fun testInsertTVShowDB() {
        val showEntity = TVShowEntity(showId = "4", showTitle = "Dummy 4", showPoster = "Dummy Poster 4")
        val tempShowResponse : ArrayList<TVShowEntity> = ArrayList()

        Mockito.`when`(local.insertTVShowDB(showEntity)).then {
            tempShowResponse.addAll(showResponse)
            tempShowResponse.add(showEntity)
        }

        movieRepository.insertTVShowDB(showEntity)
        val showEntities = Resource.success(PagedListUtil.mockPagedList(tempShowResponse))
        verify(local).insertTVShowDB(showEntity)
        Assert.assertNotNull(showEntities.data)
        assertEquals(tempShowResponse.size.toLong(), showEntities.data?.size?.toLong())
    }

    @Test
    fun testGetTVShowByIdDB() {
        val show = TVShowEntity(showId = "1", showTitle = "Dummy 1", showPoster = "Dummy Poster 1")
        Mockito.`when`(local.getTVShowByIdDB("1")).thenReturn(show)

        val tvShowEntity = movieRepository.getTVShowByIdDB("1")
        verify(local).getTVShowByIdDB("1")
        Assert.assertNotNull(tvShowEntity)
        assertEquals(show.showId, tvShowEntity.showId)
    }

    @Test
    fun testGetTVShowByIdDBLiveData() {
        val showData = MutableLiveData<TVShowEntity>()
        val show = TVShowEntity(showId = "1", showTitle = "Dummy 1", showPoster = "Dummy Poster 1")
        showData.value = show
        Mockito.`when`(local.getTVShowByIdDBLiveData("1")).thenReturn(showData)

        val movieEntities = movieRepository.getTVShowByIdDBLiveData("1")
        verify(local).getTVShowByIdDBLiveData("1")
        Assert.assertNotNull(movieEntities)
        assertEquals(showData.value!!.showId, movieEntities.value!!.showId)
    }

    @Test
    fun testDeleteTVShowDB() {
        val tempShowResponse : ArrayList<TVShowEntity> = ArrayList()
        tempShowResponse.addAll(showResponse)

        Mockito.`when`(local.deleteTVShowDB("1")).then {
            tempShowResponse.removeAt(0)
        }

        movieRepository.deleteTVShowDB("1")
        val showEntities = Resource.success(PagedListUtil.mockPagedList(tempShowResponse))
        verify(local).deleteTVShowDB("1")
        Assert.assertNotNull(showEntities.data)
        assertEquals(tempShowResponse.size.toLong(), showEntities.data?.size?.toLong())
    }

    @Test
    fun testGetMoviesPopular() {
        Mockito.`when`(remote.getMoviesPopular()).thenReturn(movieResponseRemote)
        val response = movieRepository.getMoviesPopular()

        verify(remote).getMoviesPopular()
        Assert.assertNotNull(response)
        assertEquals(response.value?.data?.size?.toLong(), movieResponseRemote.value?.data?.size?.toLong())
    }

    @Test
    fun testGetTVShowPopular() {
        Mockito.`when`(remote.getTVShowPopular()).thenReturn(showResponseRemote)
        val response = movieRepository.getTVShowPopular()

        verify(remote).getTVShowPopular()
        Assert.assertNotNull(response)
        assertEquals(response.value?.data?.size?.toLong(), showResponseRemote.value?.data?.size?.toLong())
    }

    @Test
    fun testGetMoviesSearch() {
        Mockito.`when`(remote.getMoviesSearch("1")).thenReturn(DataDummy.getMoviesSearch("1"))
        val response = movieRepository.getMoviesSearch("1")

        verify(remote).getMoviesSearch("1")
        Assert.assertNotNull(response)
        assertEquals(response.value?.data?.size?.toLong(), DataDummy.getMoviesSearch("1").value?.data?.size?.toLong())
    }

    @Test
    fun testGetShowSearch() {
        Mockito.`when`(remote.getShowSearch("1")).thenReturn(DataDummy.getShowSearch("1"))
        val response = movieRepository.getShowSearch("1")

        verify(remote).getShowSearch("1")
        Assert.assertNotNull(response)
        assertEquals(response.value?.data?.size?.toLong(), DataDummy.getShowSearch("1").value?.data?.size?.toLong())
    }

    @Test
    fun testGetMovieDetail() {
        Mockito.`when`(remote.getMovieDetail("1")).thenReturn(DataDummy.getMovieDetail("1"))
        val response = movieRepository.getMovieDetail("1")

        verify(remote).getMovieDetail("1")
        Assert.assertNotNull(response)
        assertEquals(response.value?.data?.id, DataDummy.getMovieDetail("1").value?.data?.id)
    }

    @Test
    fun testGetTVShowDetail() {
        Mockito.`when`(remote.getTVShowDetail("1")).thenReturn(DataDummy.getTVShowDetail("1"))
        val response = movieRepository.getTVShowDetail("1")

        verify(remote).getTVShowDetail("1")
        Assert.assertNotNull(response)
        assertEquals(response.value?.data?.id, DataDummy.getTVShowDetail("1").value?.data?.id)
    }
}