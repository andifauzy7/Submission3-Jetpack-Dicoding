package com.example.cinema.ui.favorite.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cinema.databinding.ItemTvshowBinding
import com.example.cinema.room.entity.TVShowEntity
import com.example.cinema.ui.detail.DetailActivity

class TVShowAdapter : RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder>() {
    private var listTVShow = ArrayList<TVShowEntity>()

    fun setShow(tvShow: List<TVShowEntity>){
        this.listTVShow.clear()
        this.listTVShow.addAll(tvShow)
    }

    class TVShowViewHolder(private val binding: ItemTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow : TVShowEntity){
            with(binding){
                tvTvshowTitle.text = tvShow.showTitle
                Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/w500" + tvShow.showPoster)
                        .centerCrop()
                        .transform(RoundedCorners(16))
                        .into(imageTvPoster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.ID_CONTENT, tvShow.showId)
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