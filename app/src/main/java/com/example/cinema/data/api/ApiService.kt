package com.example.cinema.data.api

import com.example.cinema.data.response.ResponseMovie
import com.example.cinema.data.response.ResponseMovieDetail
import com.example.cinema.data.response.ResponseTVShow
import com.example.cinema.data.response.ResponseTVShowDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("tv/popular?api_key=${ApiConfig.ApiKey}")
    fun getTVShowPopular() : Call<ResponseTVShow>

    @GET("movie/popular?api_key=${ApiConfig.ApiKey}")
    fun getMoviesPopular() : Call<ResponseMovie>

    @GET("search/movie")
    fun getMoviesSearch(@Query("query") keyword: String, @Query("api_key") api_key: String = ApiConfig.ApiKey) : Call<ResponseMovie>

    @GET("search/tv")
    fun getShowSearch(@Query("query") keyword: String, @Query("api_key") api_key: String = ApiConfig.ApiKey) : Call<ResponseTVShow>

    @GET("movie/{movie_id}?api_key=${ApiConfig.ApiKey}")
    fun getMovieDetail(@Path("movie_id") id: String): Call<ResponseMovieDetail>

    @GET("tv/{tv_id}?api_key=${ApiConfig.ApiKey}")
    fun getTVShowDetail(@Path("tv_id") id: String): Call<ResponseTVShowDetail>
}