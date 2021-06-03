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
import com.example.cinema.R
import com.example.cinema.databinding.FragmentDetailMoviesBinding
import com.example.cinema.room.entity.MovieEntity
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
    private lateinit var movieEntity: MovieEntity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        fragmentDetailMoviesBinding = FragmentDetailMoviesBinding.inflate(layoutInflater, container, false)
        return fragmentDetailMoviesBinding.root
    }

    override fun onResume() {
        super.onResume()
        fragmentDetailMoviesBinding.shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        fragmentDetailMoviesBinding.shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[DetailMoviesViewModel::class.java]
        val genreAdapter = GenreAdapter()
        EspressoIdlingResource.increment()
        viewModel.getMovieDetail(idContent).observe(viewLifecycleOwner, { movies ->
            EspressoIdlingResource.decrement()
            if (movies.status == Resource.Status.SUCCESS) {
                movieEntity = MovieEntity(idContent, movies.data?.title!!, "https://image.tmdb.org/t/p/w500" + movies.data?.posterPath)
                fragmentDetailMoviesBinding.shimmerFrameLayout.stopShimmerAnimation()
                fragmentDetailMoviesBinding.shimmerFrameLayout.visibility = View.GONE
                fragmentDetailMoviesBinding.containerDetailMovies.visibility = View.VISIBLE
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
                Toast.makeText(context, "Error : " + movies.message, Toast.LENGTH_LONG).show()
            }
        })

        with(fragmentDetailMoviesBinding.rvGenreMovies) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = genreAdapter
        }

        checkIsFavorite()
        fragmentDetailMoviesBinding.fabFavoriteMovies.setOnClickListener {
            @Suppress("SENSELESS_COMPARISON")
            if(viewModel.getMovieFavorite(idContent) == null){
                viewModel.addMovieFavorite(movieEntity)
                Toast.makeText(requireContext(), "Added To Favorite", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.deleteMovieFavorite(idContent)
                Toast.makeText(requireContext(), "Deleted From Favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkIsFavorite(){
        viewModel.isMovieFavorite(idContent).observe(viewLifecycleOwner, { movies ->
            if(movies == null){
                fragmentDetailMoviesBinding.fabFavoriteMovies.setImageResource(R.drawable.ic_baseline_favorite_border_24dp)
            } else {
                fragmentDetailMoviesBinding.fabFavoriteMovies.setImageResource(R.drawable.ic_baseline_favorite_24dp)
            }
        })
    }
}