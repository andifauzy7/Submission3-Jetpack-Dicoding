package com.example.cinema.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.data.response.Genre
import com.example.cinema.databinding.ItemGenreBinding

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    private var listGenre = ArrayList<Genre>()

    class GenreViewHolder(private val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genre : Genre){
            with(binding){
                tvGenre.text = genre.name
            }
        }
    }

    fun setGenre(genre: List<Genre>?){
        if(genre == null) return
        this.listGenre.clear()
        this.listGenre.addAll(genre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val itemGenreBinding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(itemGenreBinding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = listGenre[position]
        holder.bind(genre)
    }

    override fun getItemCount(): Int = listGenre.size
}