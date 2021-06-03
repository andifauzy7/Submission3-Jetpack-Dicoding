package com.example.cinema.ui.favorite.movies

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cinema.databinding.ItemMoviesFavoriteBinding
import com.example.cinema.room.entity.MovieEntity
import com.example.cinema.ui.detail.DetailActivity

class MoviesPagedListAdapter :
    PagedListAdapter<MovieEntity, MoviesPagedListAdapter.MoviesViewHolder>(DIFF_CALLBACK)  {
    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieEntity> = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldNote: MovieEntity, newNote: MovieEntity): Boolean {
                return oldNote.movieId == newNote.movieId
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldNote: MovieEntity, newNote: MovieEntity): Boolean {
                return oldNote == newNote
            }
        }
    }

    inner class MoviesViewHolder(private val binding: ItemMoviesFavoriteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movies : MovieEntity){
            with(binding){
                tvItemNameMovies.text = movies.movieTitle
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500" + movies.moviePoster)
                    .centerCrop()
                    .transform(RoundedCorners(16))
                    .into(imgPosterMovies)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.ID_CONTENT, movies.movieId)
                    intent.putExtra(DetailActivity.TYPE_CONTENT, DetailActivity.TYPE_MOVIES)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = ItemMoviesFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position) as MovieEntity)
    }

}