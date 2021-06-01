package com.example.cinema.ui.detail.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cinema.databinding.FragmentDetailMoviesBinding
import com.example.cinema.ui.detail.GenreAdapter
import com.example.cinema.utils.EspressoIdlingResource
import com.example.cinema.utils.Resource
import com.example.cinema.viewmodel.ViewModelFactory
import java.text.DecimalFormat
import java.text.NumberFormat

class DetailMoviesFragment(idContent: String?) : Fragment() {
    private var idContent : String = idContent.toString()
    private lateinit var viewModel: DetailMoviesViewModel
    private lateinit var fragmentDetailMoviesBinding: FragmentDetailMoviesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        fragmentDetailMoviesBinding = FragmentDetailMoviesBinding.inflate(layoutInflater, container, false)
        return fragmentDetailMoviesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[DetailMoviesViewModel::class.java]
        val genreAdapter = GenreAdapter()
        EspressoIdlingResource.increment()
        fragmentDetailMoviesBinding.progressBarMovieDetail.visibility = View.VISIBLE
        viewModel.getMovieDetail(idContent).observe(viewLifecycleOwner, { movies ->
            EspressoIdlingResource.decrement()
            if (movies.status == Resource.Status.SUCCESS) {
                fragmentDetailMoviesBinding.progressBarMovieDetail.visibility = View.GONE
                context?.let {
                    Glide.with(it)
                            .load("https://image.tmdb.org/t/p/w500" + movies.data?.posterPath)
                            .centerCrop()
                            .transform(RoundedCorners(16))
                            .into(fragmentDetailMoviesBinding.imgDetailMovies)
                }
                genreAdapter.setGenre(movies.data?.genres)
                genreAdapter.notifyDataSetChanged()
                fragmentDetailMoviesBinding.tvTitleMovies.text = movies.data?.title
                val formatter: NumberFormat = DecimalFormat("#,###")
                val formattedNumber: String = formatter.format(movies.data?.budget)
                "$$formattedNumber,00".also { fragmentDetailMoviesBinding.tvBudgetMovies.text = it }

                fragmentDetailMoviesBinding.ratingBarMovies.rating = (movies.data?.voteAverage?.div(2))?.toFloat()!!
                fragmentDetailMoviesBinding.tvReleasedMovies.text = movies.data?.releaseDate
                fragmentDetailMoviesBinding.tvHomepageMovies.text = movies.data?.homepage
                fragmentDetailMoviesBinding.tvOverviewMovies.text = movies.data?.overview
            } else if (movies.status == Resource.Status.ERROR) {
                fragmentDetailMoviesBinding.progressBarMovieDetail.visibility = View.GONE
                Toast.makeText(context, "Error : " + movies.message, Toast.LENGTH_LONG).show()
            }
        })

        with(fragmentDetailMoviesBinding.rvGenreMovies) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = genreAdapter
        }
    }
}