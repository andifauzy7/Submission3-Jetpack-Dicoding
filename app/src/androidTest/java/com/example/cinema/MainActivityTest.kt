package com.example.cinema

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cinema.data.LocalDataSource
import com.example.cinema.data.MovieRepository
import com.example.cinema.data.RemoteDataSource
import com.example.cinema.data.response.ResultMovies
import com.example.cinema.data.response.ResultTVShow
import com.example.cinema.room.MovieDatabase
import com.example.cinema.ui.home.HomeViewModel
import com.example.cinema.utils.EspressoIdlingResource
import com.example.cinema.utils.Resource
import org.junit.*
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainActivityTest {
    private lateinit var dummyMovies : LiveData<Resource<List<ResultMovies>>>
    private lateinit var dummyTVShow : LiveData<Resource<List<ResultTVShow>>>
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var movieRepository: MovieRepository
    private lateinit var instrumentationContext: Context

    @Before
    fun setUp() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        val database = MovieDatabase.getInstance(instrumentationContext)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        movieRepository = MovieRepository(remoteDataSource, localDataSource)
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
    fun test1loadMovies() {
        dummyMovies = homeViewModel.getMoviesPopular()
        onView(withId(R.id.rv_popular)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
            dummyMovies.value?.data?.size!!
        ))
    }

    @Test
    fun test2loadTVShow() {
        dummyTVShow = homeViewModel.getTVShowPopular()
        onView(withId(R.id.scroll_view_home)).perform(ViewActions.swipeUp())
        onView(withId(R.id.rv_popular_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
            dummyTVShow.value?.data?.size!!
        ))
    }

    @Test
    fun test3loadFavoriteMovie() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.title_favorite)).perform(ViewActions.click())
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.movies)).perform(ViewActions.click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
    }

    @Test
    fun test4insertFavoriteMovie(){
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
    fun test5deleteFavoriteMovie(){
        onView(withContentDescription(R.string.title_favorite)).perform(ViewActions.click())
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.movies)).perform(ViewActions.click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.fab_favorite_movies)).perform(ViewActions.click())
        onView(withId(R.id.fab_favorite_movies)).perform(ViewActions.pressBack())
        onView(withId(R.id.rv_movies)).check(matches(hasChildCount(0)))
    }

    @Test
    fun test6loadFavoriteTVShow() {
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.title_favorite)).perform(ViewActions.click())
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.tv_show)).perform(ViewActions.click())
        onView(withId(R.id.rv_show)).check(matches(isDisplayed()))
    }

    @Test
    fun test7insertFavoriteTVShow(){
        dummyTVShow = homeViewModel.getTVShowPopular()
        onView(withId(R.id.scroll_view_home)).perform(ViewActions.swipeUp())
        onView(withId(R.id.rv_popular_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTVShow.value?.data?.size!!))
        onView(withId(R.id.rv_popular_tv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.tv_title_show)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_show)).check(matches(withText(dummyTVShow.value?.data!![0].name)))
        onView(withId(R.id.fab_favorite_tvshow)).perform(ViewActions.click())
        onView(withId(R.id.fab_favorite_tvshow)).perform(ViewActions.pressBack())

        onView(withContentDescription(R.string.title_favorite)).perform(ViewActions.click())
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.tv_show)).perform(ViewActions.click())
        onView(withId(R.id.rv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.tv_title_show)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_show)).check(matches(withText(dummyTVShow.value?.data!![0].name)))
    }

    @Test
    fun test8deleteFavoriteTVShow(){
        onView(withContentDescription(R.string.title_favorite)).perform(ViewActions.click())
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.tv_show)).perform(ViewActions.click())
        onView(withId(R.id.rv_show)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.fab_favorite_tvshow)).perform(ViewActions.click())
        onView(withId(R.id.fab_favorite_tvshow)).perform(ViewActions.pressBack())
        onView(withId(R.id.rv_show)).check(matches(hasChildCount(0)))

    }
}