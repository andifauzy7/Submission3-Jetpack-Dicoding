package com.example.cinema.ui.detail

import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.cinema.MainActivity
import com.example.cinema.R
import com.example.cinema.data.MovieRepository
import com.example.cinema.data.response.ResultMovies
import com.example.cinema.data.response.ResultTVShow
import com.example.cinema.ui.detail.movies.DetailMoviesViewModel
import com.example.cinema.ui.detail.tvshow.DetailTVShowViewModel
import com.example.cinema.ui.home.HomeViewModel
import com.example.cinema.utils.EspressoIdlingResource
import com.example.cinema.utils.Resource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailActivityTest {
    private lateinit var dummyMovies : LiveData<Resource<List<ResultMovies>>>
    private lateinit var dummyTVShow : LiveData<Resource<List<ResultTVShow>>>

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var detailMoviesViewModel: DetailMoviesViewModel
    private lateinit var detailTVShowViewModel: DetailTVShowViewModel

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        movieRepository = MovieRepository()
        homeViewModel = HomeViewModel(movieRepository)
        detailMoviesViewModel = DetailMoviesViewModel(movieRepository)
        detailTVShowViewModel = DetailTVShowViewModel(movieRepository)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loadMovies() {
        dummyMovies = homeViewModel.getMoviesPopular()
        Espresso.onView(ViewMatchers.withId(R.id.rv_popular))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rv_popular)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
            dummyMovies.value?.data?.size!!
        ))
        Espresso.onView(ViewMatchers.withId(R.id.rv_popular)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_title_movies)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_title_movies)).check(ViewAssertions.matches(ViewMatchers.withText(
            dummyMovies.value?.data!![0].title
        )))
    }

    @Test
    fun loadTVShow() {
        dummyTVShow = homeViewModel.getTVShowPopular()
        Espresso.onView(ViewMatchers.withId(R.id.scroll_view_home)).perform(ViewActions.swipeUp())
        Espresso.onView(ViewMatchers.withId(R.id.rv_popular_tv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rv_popular_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
            dummyTVShow.value?.data?.size!!
        ))
        Espresso.onView(ViewMatchers.withId(R.id.rv_popular_tv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_title_show)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_title_show)).check(ViewAssertions.matches(ViewMatchers.withText(
            dummyTVShow.value?.data!![0].name
        )))
    }
}