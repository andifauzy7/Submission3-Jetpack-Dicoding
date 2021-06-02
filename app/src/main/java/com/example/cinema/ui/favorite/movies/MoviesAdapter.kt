package com.example.cinema.ui.favorite.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cinema.databinding.ItemMoviesBinding
import com.example.cinema.room.entity.MovieEntity
import com.example.cinema.ui.detail.DetailActivity

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>(){
    private var listMovies = ArrayList<MovieEntity>()

    class MoviesViewHolder(private val binding: ItemMoviesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movies : MovieEntity){
            with(binding){
                tvMoviesTitle.text = movies.movieTitle
                Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/w500" + movies.moviePoster)
                        .centerCrop()
                        .transform(RoundedCorners(16))
                        .into(imgMoviesPoster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.ID_CONTENT, movies.movieId)
                    intent.putExtra(DetailActivity.TYPE_CONTENT, DetailActivity.TYPE_MOVIES)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    fun setMovies(movies: List<MovieEntity>?){
        if(movies == null) return
        this.listMovies.clear()
        this.listMovies.addAll(movies)
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): MoviesViewHolder {
        val itemsMoviesBinding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsMoviesBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movies = listMovies[position]
        holder.bind(movies)
    }

    override fun getItemCount(): Int = listMovies.size
}