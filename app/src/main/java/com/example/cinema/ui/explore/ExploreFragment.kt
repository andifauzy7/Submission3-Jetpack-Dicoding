package com.example.cinema.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinema.databinding.FragmentExploreBinding
import com.example.cinema.utils.Resource
import com.example.cinema.viewmodel.ViewModelFactory

class ExploreFragment : Fragment() {
    private lateinit var fragmentExploreBinding: FragmentExploreBinding
    private lateinit var exploreViewModel: ExploreViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        fragmentExploreBinding = FragmentExploreBinding.inflate(layoutInflater, container, false)
        return fragmentExploreBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        exploreViewModel = ViewModelProvider(this, factory)[ExploreViewModel::class.java]
        val moviesAdapterPopular = MoviesAdapter()
        val tvShowAdapterPopular = TVShowAdapter()

        fragmentExploreBinding.searchItemExplore.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                fragmentExploreBinding.searchItemExplore.onActionViewCollapsed()
                if(fragmentExploreBinding.radioGroupTypeExplore.checkedRadioButtonId == fragmentExploreBinding.moviesButtonExplore.id){
                    exploreViewModel.getMoviesSearch(query.toString()).observe(viewLifecycleOwner, { movies ->
                        fragmentExploreBinding.rvExploreMovies.visibility = View.VISIBLE
                        fragmentExploreBinding.rvExploreShow.visibility = View.GONE
                        if (movies.status == Resource.Status.SUCCESS) {
                            moviesAdapterPopular.setMovies(movies.data)
                            moviesAdapterPopular.notifyDataSetChanged()
                        }
                        else if (movies.status == Resource.Status.ERROR) {
                            Toast.makeText(context, "Error : " + movies.message, Toast.LENGTH_LONG).show()
                        }
                    })
                } else {
                    exploreViewModel.getShowSearch(query.toString()).observe(viewLifecycleOwner, { show ->
                        fragmentExploreBinding.rvExploreShow.visibility = View.VISIBLE
                        fragmentExploreBinding.rvExploreMovies.visibility = View.GONE
                        if (show.status == Resource.Status.SUCCESS) {
                            tvShowAdapterPopular.setShow(show.data!!)
                            tvShowAdapterPopular.notifyDataSetChanged()
                        }
                        else if (show.status == Resource.Status.ERROR) {
                            Toast.makeText(context, "Error : " + show.message, Toast.LENGTH_LONG).show()
                        }
                    })
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })


        with(fragmentExploreBinding.rvExploreMovies) {
            layoutManager = GridLayoutManager(context,3)
            setHasFixedSize(true)
            adapter = moviesAdapterPopular
        }

        with(fragmentExploreBinding.rvExploreShow) {
            layoutManager = GridLayoutManager(context,3)
            setHasFixedSize(true)
            adapter = tvShowAdapterPopular
        }
    }
}