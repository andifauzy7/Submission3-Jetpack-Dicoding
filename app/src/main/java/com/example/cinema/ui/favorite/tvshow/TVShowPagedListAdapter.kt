package com.example.cinema.ui.favorite.tvshow

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cinema.databinding.ItemTvshowFavoriteBinding
import com.example.cinema.room.entity.TVShowEntity
import com.example.cinema.ui.detail.DetailActivity

class TVShowPagedListAdapter :
    PagedListAdapter<TVShowEntity, TVShowPagedListAdapter.TVShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<TVShowEntity> = object : DiffUtil.ItemCallback<TVShowEntity>() {
            override fun areItemsTheSame(old: TVShowEntity, new: TVShowEntity): Boolean {
                return old.showId == new.showId
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(old: TVShowEntity, new: TVShowEntity): Boolean {
                return old == new
            }
        }
    }

    inner class TVShowViewHolder(private val binding: ItemTvshowFavoriteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(tvShow : TVShowEntity){
            with(binding){
                tvItemNameShow.text = tvShow.showTitle
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500" + tvShow.showPoster)
                    .centerCrop()
                    .transform(RoundedCorners(16))
                    .into(imgPosterShow)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.ID_CONTENT, tvShow.showId)
                    intent.putExtra(DetailActivity.TYPE_CONTENT, DetailActivity.TYPE_TVSHOW)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TVShowPagedListAdapter.TVShowViewHolder {
        val binding = ItemTvshowFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TVShowPagedListAdapter.TVShowViewHolder, position: Int) {
        holder.bind(getItem(position) as TVShowEntity)
    }
}