package com.example.cinema

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cinema.data.MovieRepository
import com.example.cinema.data.response.ResultMovies
import com.example.cinema.data.response.ResultTVShow
import com.example.cinema.room.entity.MovieEntity
import com.example.cinema.ui.favorite.movies.MoviesViewModel
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
    private lateinit var dummyFavoriteMovies : DataSource.Factory<Int, MovieEntity>
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var favoriteMoviesViewModel: MoviesViewModel
    private lateinit var movieRepository: MovieRepository
    private lateinit var instrumentationContext: Context

    @Before
    fun setUp() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        movieRepository = MovieRepository(instrumentationContext)
        homeViewModel = HomeViewModel(movieRepository)
        favoriteMoviesViewModel = MoviesViewModel(movieRepository)
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

    @Test
    fun loadFavorite() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.title_favorite)).perform(ViewActions.click())
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.movies)).perform(ViewActions.click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
    }

    @Test
    fun insertFavorite(){
        dummyMovies = homeViewModel.getMoviesPopular()
        onView(withId(R.id.rv_popular)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.value?.data?.size!!))
        onView(withId(R.id.rv_popular)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.tv_title_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_movies)).check(matches(withText(dummyMovies.value?.data!![0].title)))
        onView(withId(R.id.fab_favorite_movies)).perform(ViewActions.click())
        onView(withId(R.id.fab_favorite_movies)).perform(ViewActions.pressBack())

        onView(withContentDescription(R.string.title_favorite)).perform(ViewActions.click())
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.movies)).perform(ViewActions.click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.tv_title_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_movies)).check(matches(withText(dummyMovies.value?.data!![0].title)))
    }

    @Test
    fun deleteFavorite(){
        dummyMovies = homeViewModel.getMoviesPopular()
        onView(withId(R.id.rv_popular)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.value?.data?.size!!))
        onView(withId(R.id.rv_popular)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.tv_title_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_movies)).check(matches(withText(dummyMovies.value?.data!![0].title)))
        onView(withId(R.id.fab_favorite_movies)).perform(ViewActions.click())
        onView(withId(R.id.fab_favorite_movies)).perform(ViewActions.pressBack())

        onView(withContentDescription(R.string.title_favorite)).perform(ViewActions.click())
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.movies)).perform(ViewActions.click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).check(matches(hasChildCount(0)))
    }
}