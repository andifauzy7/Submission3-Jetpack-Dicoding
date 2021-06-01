package com.example.cinema.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cinema.data.response.ResultTVShow
import com.example.cinema.databinding.ItemTvshowBinding
import com.example.cinema.ui.detail.DetailActivity

class TVShowAdapter : RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder>() {
    private var listTVShow = ArrayList<ResultTVShow>()

    fun setShow(tvShow: List<ResultTVShow>){
        this.listTVShow.clear()
        this.listTVShow.addAll(tvShow)
    }

    class TVShowViewHolder(private val binding: ItemTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow : ResultTVShow){
            with(binding){
                tvTvshowTitle.text = tvShow.name
                ratingTvShow.rating = (tvShow.voteAverage / 2).toFloat()
                Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/w500" + tvShow.posterPath)
                        .centerCrop()
                        .transform(RoundedCorners(16))
                        .into(imageTvPoster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.ID_CONTENT, tvShow.id.toString())
                    intent.putExtra(DetailActivity.TYPE_CONTENT, DetailActivity.TYPE_TVSHOW)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val itemTvShowBinding = ItemTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowViewHolder(itemTvShowBinding)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val tvShow = listTVShow[position]
        holder.bind(tvShow)
    }

    override fun getItemCount(): Int = listTVShow.size
}