package com.example.cinema

import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.cinema.data.MovieRepository
import com.example.cinema.data.response.ResultMovies
import com.example.cinema.data.response.ResultTVShow
import com.example.cinema.ui.home.HomeViewModel
import com.example.cinema.utils.EspressoIdlingResource
import com.example.cinema.utils.Resource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    private lateinit var dummyMovies : LiveData<Resource<List<ResultMovies>>>
    private lateinit var dummyTVShow : LiveData<Resource<List<ResultTVShow>>>
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        movieRepository = MovieRepository()
        homeViewModel = HomeViewModel(movieRepository)
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
        onView(withId(R.id.rv_popular)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
            dummyMovies.value?.data?.size!!
        ))
    }

    @Test
    fun loadTVShow() {
        dummyTVShow = homeViewModel.getTVShowPopular()
        onView(withId(R.id.scroll_view_home)).perform(ViewActions.swipeUp())
        onView(withId(R.id.rv_popular_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
            dummyTVShow.value?.data?.size!!
        ))
    }
}