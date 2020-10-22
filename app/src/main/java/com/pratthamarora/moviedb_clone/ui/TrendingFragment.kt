package com.pratthamarora.moviedb_clone.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pratthamarora.moviedb_clone.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TrendingFragment : Fragment(R.layout.trending_fragment) {

    private val viewModel by viewModels<TrendingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.trendingMovies.observe(viewLifecycleOwner, {
            Timber.d("success: $it")

        })
    }


}