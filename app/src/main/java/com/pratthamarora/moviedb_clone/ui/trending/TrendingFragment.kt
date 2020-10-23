package com.pratthamarora.moviedb_clone.ui.trending

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pratthamarora.moviedb_clone.R
import com.pratthamarora.moviedb_clone.utils.Status.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.trending_fragment.*

@AndroidEntryPoint
class TrendingFragment : Fragment(R.layout.trending_fragment) {

    private val viewModel by viewModels<TrendingViewModel>()

    private lateinit var trendingMoviesAdapter: TrendingMoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        subscribeToObservers()


    }

    private fun subscribeToObservers() {
        viewModel.trendingMovies.observe(viewLifecycleOwner, {
            it?.let { response ->
                when (response.status) {
                    SUCCESS -> {
                        response.data?.let { it1 -> trendingMoviesAdapter.setMovies(it1) }
                        showLoading(false)
                    }
                    ERROR -> {
                        showLoading(false)
                        Snackbar.make(
                            requireView(),
                            response.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    LOADING -> showLoading(true)
                }
            }

        })
    }

    private fun setupRecyclerView() {
        trendingMoviesAdapter = TrendingMoviesAdapter()
        rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = trendingMoviesAdapter
        }
    }

    private fun showLoading(isShow: Boolean) {
        loadingContainer.isVisible = isShow
    }

}