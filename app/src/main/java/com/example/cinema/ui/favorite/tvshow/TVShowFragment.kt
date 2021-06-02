package com.example.cinema.ui.favorite.tvshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinema.databinding.FragmentTvShowBinding
import com.example.cinema.viewmodel.ViewModelFactory

class TVShowFragment : Fragment() {
    private lateinit var viewModel: TVShowViewModel
    private lateinit var fragmentTVShowBinding: FragmentTvShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTVShowBinding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return fragmentTVShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[TVShowViewModel::class.java]

        val tvShowAdapter = TVShowAdapter()
        viewModel.getAllTVShow().observe(viewLifecycleOwner,{ show ->
            tvShowAdapter.setShow(show)
            tvShowAdapter.notifyDataSetChanged()
        })

        with(fragmentTVShowBinding.rvShow) {
            layoutManager = GridLayoutManager(context,2)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }
}