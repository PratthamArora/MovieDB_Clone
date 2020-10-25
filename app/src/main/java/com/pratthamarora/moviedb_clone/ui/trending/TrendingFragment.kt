package com.pratthamarora.moviedb_clone.ui.trending

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratthamarora.moviedb_clone.R
import dagger.hilt.android.AndroidEntryPoint
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
                trendingMoviesAdapter.submitData(lifecycle, response)
            }

        })
    }

    private fun setupRecyclerView() {
        trendingMoviesAdapter = TrendingMoviesAdapter()
        rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = trendingMoviesAdapter.withLoadStateHeaderAndFooter(
                FooterStateAdapter {
                    trendingMoviesAdapter.retry()
                },
                FooterStateAdapter {
                    trendingMoviesAdapter.retry()
                }
            )
        }
    }


}