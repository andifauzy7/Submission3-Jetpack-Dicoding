package com.example.cinema.utils

import androidx.lifecycle.MutableLiveData
import com.example.cinema.data.response.*

object DataDummy {
    fun getMoviesPopular(): MutableLiveData<Resource<List<ResultMovies>>> {
        val movies = ResultMovies(
            adult = false,
            backdropPath = "backdropSample",
            genreIds = emptyList(),
            id = 1,
            originalLanguage = "ID",
            originalTitle = "Original Title Sample",
            overview = "Overview Sample",
            popularity = 10.0,
            posterPath = "posterSample",
            releaseDate = "2021-05-21",
            title = "Sample Title",
            video = false,
            voteAverage = 10.0,
            voteCount = 100
        )
        val listMovies: List<ResultMovies> = listOf(movies)
        return MutableLiveData(Resource.success(listMovies))
    }

    fun getTVShowPopular() : MutableLiveData<Resource<List<ResultTVShow>>> {
        val show = ResultTVShow(
                backdropPath = "backdropSample",
                firstAirDate = "2021-05-21",
                genreIds = emptyList(),
                id = 1,
                name = "Sample Name",
                originCountry = emptyList(),
                originalLanguage = "ID",
                originalName = "Original Name Sample",
                overview = "Overview Sample",
                popularity = 10.0,
                posterPath = "PosterPath Sample",
                voteAverage = 10.0,
                voteCount = 100
        )
        val listTVShow: List<ResultTVShow> = listOf(show)
        return MutableLiveData(Resource.success(listTVShow))
    }

    fun getMoviesSearch(keyword : String) : MutableLiveData<Resource<List<ResultMovies>>> {
        val movies = ResultMovies(
                adult = false,
                backdropPath = "backdropSample",
                genreIds = emptyList(),
                id = 1,
                originalLanguage = "ID",
                originalTitle = "Original Title Sample",
                overview = "Overview Sample",
                popularity = 10.0,
                posterPath = "posterSample",
                releaseDate = "2021-05-21",
                title = keyword,
                video = false,
                voteAverage = 10.0,
                voteCount = 100
        )
        val listMovies: List<ResultMovies> = listOf(movies)
        return MutableLiveData(Resource.success(listMovies))
    }

    fun getShowSearch(keyword : String) : MutableLiveData<Resource<List<ResultTVShow>>>{
        val show = ResultTVShow(
                backdropPath = "backdropSample",
                firstAirDate = "2021-05-21",
                genreIds = emptyList(),
                id = 1,
                name = keyword,
                originCountry = emptyList(),
                originalLanguage = "ID",
                originalName = "Original Name Sample",
                overview = "Overview Sample",
                popularity = 10.0,
                posterPath = "PosterPath Sample",
                voteAverage = 10.0,
                voteCount = 100
        )
        val listTVShow: List<ResultTVShow> = listOf(show)
        return MutableLiveData(Resource.success(listTVShow))
    }

    fun getMovieDetail(movieId: String) : MutableLiveData<Resource<ResponseMovieDetail>> {
        val movie = ResponseMovieDetail(
                adult = false,
                backdropPath = "BackdropPath Sample",
                belongsToCollection = Any(),
                budget = 0,
                genres = emptyList(),
                homepage = "homePage Sample",
                id = movieId.toInt(),
                imdbId = "1",
                originalLanguage = "ID",
                originalTitle = "Original Title Sample",
                overview = "Overview Sample",
                popularity = 10.0,
                posterPath = "PosterPath Sample",
                productionCompanies = emptyList(),
                productionCountries = emptyList(),
                releaseDate = "2021-05-21",
                revenue = 0,
                spokenLanguages = emptyList(),
                runtime = 0,
                status = "Available",
                tagline = "Tagline Sample",
                title = "Title Sample",
                video = false,
                voteAverage = 10.0,
                voteCount = 100
        )
        return MutableLiveData(Resource.success(movie))
    }

    fun getTVShowDetail(tvId: String) : MutableLiveData<Resource<ResponseTVShowDetail>> {
        val show = ResponseTVShowDetail(
                backdropPath = "backdrop Sample",
                createdBy = emptyList(),
                episodeRunTime = emptyList(),
                firstAirDate = "2021-05-21",
                genres = emptyList(),
                homepage = "homepage Sample",
                id = tvId.toInt(),
                inProduction = true,
                languages = emptyList(),
                lastAirDate = "2021-05-21",
                lastEpisodeToAir = LastEpisodeToAir(
                        airDate = "2021-05-21",
                        episodeNumber = 1,
                        id = 1,
                        name = "Ep 1",
                        overview = "Overview Sample",
                        productionCode = "ID",
                        seasonNumber = 1,
                        stillPath = "stillPath Sample",
                        voteCount = 100,
                        voteAverage = 10.0
                ),
                name = "Name Sample",
                networks = emptyList(),
                nextEpisodeToAir = Any(),
                numberOfEpisodes = 1,
                numberOfSeasons = 1,
                originCountry = emptyList(),
                originalLanguage = "ID",
                originalName = "Name Sample Sample",
                overview = "Overview Sample Overall",
                popularity = 100.0,
                posterPath = "posterPath Sample",
                productionCompanies = emptyList(),
                productionCountries = emptyList(),
                seasons = emptyList(),
                spokenLanguages = emptyList(),
                status = "Available",
                tagline = "Tagline Sample",
                type = "Active",
                voteAverage = 10.0,
                voteCount = 100
        )
        return MutableLiveData(Resource.success(show))
    }
}