package com.example.cinema.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cinema.R
import com.example.cinema.databinding.ActivityDetailBinding
import com.example.cinema.ui.detail.movies.DetailMoviesFragment
import com.example.cinema.ui.detail.tvshow.DetailTVShowFragment

class DetailActivity : AppCompatActivity() {

    companion object {
        const val TYPE_MOVIES = "MOVIES"
        const val TYPE_TVSHOW = "TV_SHOW"
        const val ID_CONTENT = "id_content"
        const val TYPE_CONTENT = "type_content"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detailActivity = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailActivity.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val idContent = intent.getStringExtra(ID_CONTENT)
        val typeContent = intent.getStringExtra(TYPE_CONTENT)

        val mFragmentManager = supportFragmentManager
        val mDetailMoviesFragment = DetailMoviesFragment(idContent)
        val mDetailTVShowFragment = DetailTVShowFragment(idContent)


        if(typeContent == TYPE_MOVIES){
            mFragmentManager
                    .beginTransaction()
                    .add(R.id.container_detail, mDetailMoviesFragment, DetailMoviesFragment::class.java.simpleName)
                    .commit()
            Log.d("TYPE", "MOVIES")
        } else {
           mFragmentManager
                    .beginTransaction()
                    .add(R.id.container_detail, mDetailTVShowFragment, DetailTVShowFragment::class.java.simpleName)
                    .commit()
            Log.d("TYPE", "TV SHOW")
        }
    }
}