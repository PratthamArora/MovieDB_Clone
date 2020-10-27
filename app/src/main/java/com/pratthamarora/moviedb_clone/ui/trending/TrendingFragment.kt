package com.pratthamarora.moviedb_clone.ui.trending

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratthamarora.moviedb_clone.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.trending_fragment.*

@AndroidEntryPoint
class TrendingFragment : Fragment(R.layout.trending_fragment) {

    private val viewModel by viewModels<TrendingViewModel>()

    private lateinit var trendingMoviesAdapter: TrendingMoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        subscribeToObservers()


    }

    private fun subscribeToObservers() {
        viewModel.trendingMovies.observe(viewLifecycleOwner, {
            it?.let { response ->
                trendingMoviesAdapter.submitData(lifecycle, response)
            }

        })
    }

    private fun initViews() {
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

        trendingMoviesAdapter.addLoadStateListener {
            swipeRefreshLayout.isRefreshing = it.source.refresh is LoadState.Loading
            errorContainer.isVisible = it.source.refresh is LoadState.Error
            rvMovies.isVisible = !errorContainer.isVisible

            if (it.source.refresh is LoadState.Error) {
                btnRetry.setOnClickListener {
                    trendingMoviesAdapter.retry()
                }

                errorContainer.isVisible = it.source.refresh is LoadState.Error
                errorMsg.text = (it.source.refresh as LoadState.Error).error.message

            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

    }


}