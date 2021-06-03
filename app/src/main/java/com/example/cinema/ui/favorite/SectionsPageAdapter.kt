@file:Suppress("DEPRECATION")

package com.example.cinema.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.cinema.R
import com.example.cinema.ui.favorite.movies.MoviesFragment
import com.example.cinema.ui.favorite.tvshow.TVShowFragment

class SectionsPageAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movies, R.string.tv_show)
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence = mContext.resources.getString(
        TAB_TITLES[position])


    override fun getItem(position: Int): Fragment =
        when(position){
            0 -> MoviesFragment()
            1 -> TVShowFragment()
            else -> Fragment()
        }
}