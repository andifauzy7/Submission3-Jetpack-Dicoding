package com.example.cinema.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cinema.data.api.ApiConfig
import com.example.cinema.data.response.*
import com.example.cinema.room.MovieDao
import com.example.cinema.room.MovieDatabase
import com.example.cinema.room.entity.MovieEntity
import com.example.cinema.room.entity.TVShowEntity
import com.example.cinema.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MovieRepository(context: Context) {

    private val movieDao : MovieDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MovieDatabase.getInstance(context)
        movieDao = db.movieDao()
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(context: Context): MovieRepository =
            instance ?: synchronized(this) {
                MovieRepository(context).apply { instance = this }
            }
    }

    fun getAllMoviesDB() : LiveData<List<MovieEntity>> = movieDao.getMovies()

    fun insertMoviesDB(movie : MovieEntity){
        executorService.execute {
            movieDao.insertMovies(movie)
        }
    }

    fun getMoviesByIdDB(movieId: String) : MovieEntity = movieDao.getMoviesById(movieId)

    fun getMoviesByIdDBLiveData(movieId: String) : LiveData<MovieEntity> = movieDao.getMoviesByIdDBLiveData(movieId)

    fun deleteMoviesDB(movieId: String) {
        executorService.execute {
            movieDao.deleteMovies(movieId)
        }
    }

    fun getAllTVShowDB() : LiveData<List<TVShowEntity>> = movieDao.getTVShow()

    fun insertTVShowDB(show : TVShowEntity){
        executorService.execute {
            movieDao.insertTVShow(show)
        }
    }

    fun getTVShowByIdDB(showId: String) : LiveData<TVShowEntity> = movieDao.getTVShowById(showId)

    fun deleteTVShowDB(show : TVShowEntity) {
        executorService.execute {
            movieDao.deleteTVShow(show)
        }
    }

    fun getMoviesPopular() : MutableLiveData<Resource<List<ResultMovies>>>{
        val movies = MutableLiveData<Resource<List<ResultMovies>>>()
        val clients = ApiConfig.getApiService().getMoviesPopular()
        clients.enqueue(object : Callback<ResponseMovie> {
            override fun onResponse(
                    call: Call<ResponseMovie>,
                    response: Response<ResponseMovie>
            ) {
                if(response.isSuccessful) {
                    val result : List<ResultMovies> = response.body()!!.results
                    movies.postValue(Resource.success(result))
                } else {
                    movies.postValue(Resource.error(response.message().toString()))
                    Log.e("MainViewModel", "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                movies.postValue(Resource.error(t.message.toString()))
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })
        return movies
    }

    fun getTVShowPopular() : MutableLiveData<Resource<List<ResultTVShow>>>{
        val tvShow = MutableLiveData<Resource<List<ResultTVShow>>>()
        val clients = ApiConfig.getApiService().getTVShowPopular()
        clients.enqueue(object : Callback<ResponseTVShow> {
            override fun onResponse(
                    call: Call<ResponseTVShow>,
                    response: Response<ResponseTVShow>
            ) {
                if(response.isSuccessful) {
                    val result : List<ResultTVShow> = response.body()!!.results
                    tvShow.postValue(Resource.success(result))
                } else {
                    tvShow.postValue(Resource.error(response.message().toString()))
                    Log.e("MainViewModel", "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseTVShow>, t: Throwable) {
                tvShow.postValue(Resource.error(t.message.toString()))
                Log.e("MainViewModel", "onFailure : ${t.message.toString()}")
            }
        })
        return tvShow
    }

    fun getMoviesSearch(keyword : String) : MutableLiveData<Resource<List<ResultMovies>>>{
        val movies = MutableLiveData<Resource<List<ResultMovies>>>()
        val clients = ApiConfig.getApiService().getMoviesSearch(keyword)
        clients.enqueue(object : Callback<ResponseMovie> {
            override fun onResponse(
                    call: Call<ResponseMovie>,
                    response: Response<ResponseMovie>
            ) {
                if (response.isSuccessful) {
                    val result : List<ResultMovies> = response.body()!!.results
                    movies.postValue(Resource.success(result))
                } else {
                    movies.postValue(Resource.error(response.message().toString()))
                    Log.e("MainViewModel", "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                movies.postValue(Resource.error(t.message.toString()))
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })
        return movies
    }

    fun getShowSearch(keyword : String) : MutableLiveData<Resource<List<ResultTVShow>>>{
        val show = MutableLiveData<Resource<List<ResultTVShow>>>()
        val clients = ApiConfig.getApiService().getShowSearch(keyword)
        clients.enqueue(object : Callback<ResponseTVShow> {
            override fun onResponse(
                    call: Call<ResponseTVShow>,
                    response: Response<ResponseTVShow>
            ) {
                if (response.isSuccessful) {
                    val result : List<ResultTVShow> = response.body()!!.results
                    show.postValue(Resource.success(result))
                } else {
                    show.postValue(Resource.error(response.message().toString()))
                    Log.e("MainViewModel", "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseTVShow>, t: Throwable) {
                show.postValue(Resource.error(t.message.toString()))
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })
        return show
    }

    fun getMovieDetail(movieId: String) : MutableLiveData<Resource<ResponseMovieDetail>>  {
        val movies = MutableLiveData<Resource<ResponseMovieDetail>>()
        val clients = ApiConfig.getApiService().getMovieDetail(movieId)
        clients.enqueue(object : Callback<ResponseMovieDetail> {
            override fun onResponse(
                call: Call<ResponseMovieDetail>,
                response: Response<ResponseMovieDetail>
            ) {
                if (response.isSuccessful) {
                    val result : ResponseMovieDetail = response.body()!!
                    movies.postValue(Resource.success(result))
                } else {
                    movies.postValue(Resource.error(response.message().toString()))
                    Log.e("MainViewModel", "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseMovieDetail>, t: Throwable) {
                movies.postValue(Resource.error(t.message.toString()))
                Log.e("MainViewModel", "onFailure : ${t.message.toString()}")
            }
        })
        return movies
    }

    fun getTVShowDetail(tvId: String) : MutableLiveData<Resource<ResponseTVShowDetail>>  {
        val tv = MutableLiveData<Resource<ResponseTVShowDetail>>()
        val clients = ApiConfig.getApiService().getTVShowDetail(tvId)
        clients.enqueue(object : Callback<ResponseTVShowDetail> {
            override fun onResponse(
                    call: Call<ResponseTVShowDetail>,
                    response: Response<ResponseTVShowDetail>
            ) {
                if (response.isSuccessful) {
                    val result : ResponseTVShowDetail = response.body()!!
                    tv.postValue(Resource.success(result))
                } else {
                    tv.postValue(Resource.error(response.message().toString()))
                    Log.e("MainViewModel", "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseTVShowDetail>, t: Throwable) {
                tv.postValue(Resource.error(t.message.toString()))
                Log.e("MainViewModel", "onFailure : ${t.message.toString()}")
            }
        })
        return tv
    }
}