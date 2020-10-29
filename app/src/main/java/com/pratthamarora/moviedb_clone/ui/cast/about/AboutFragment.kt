package com.pratthamarora.moviedb_clone.ui.cast.about

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.pratthamarora.moviedb_clone.R
import com.pratthamarora.moviedb_clone.data.model.Cast
import com.pratthamarora.moviedb_clone.utils.Constants.CAST_ID
import com.pratthamarora.moviedb_clone.utils.Status.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_about.*
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class AboutFragment : Fragment(R.layout.fragment_about) {
    private var castId by Delegates.notNull<Long>()

    @Inject
    lateinit var aboutViewModelFactory: AboutViewModel.AssistedFactory

    private val aboutViewModel by viewModels<AboutViewModel> {
        AboutViewModel.provideFactory(
            aboutViewModelFactory,
            castId
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            castId = it.getLong(CAST_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        srl.isEnabled = false

        aboutViewModel.cast.observe(viewLifecycleOwner) {
            when (it.status) {
                SUCCESS -> {
                    it.data?.let { cast ->
                        updateUI(cast)
                    }
                    showLoading(false)
                }
                ERROR -> {
                    showLoading(false)
                    Snackbar.make(
                        requireView(),
                        it.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                LOADING -> showLoading(true)
            }
        }
    }

    private fun updateUI(cast: Cast) {
        tvBirthday.text =
            if (!cast.birthday.isNullOrEmpty()) cast.birthday else getString(R.string.dash)

        tvAge.text =
            if (!cast.birthday.isNullOrEmpty()) cast.birthday else getString(R.string.dash)

        tvPlaceOfBirth.text =
            if (!cast.placeOfBirth.isNullOrEmpty()) cast.placeOfBirth else getString(R.string.dash)

        tvDepartment.text =
            if (!cast.department.isNullOrEmpty()) cast.department else getString(R.string.dash)

        tvBiography.text =
            if (!cast.biography.isNullOrEmpty()) cast.biography else getString(R.string.dash)

        tvAlsoKnownAs.text =
            if (!cast.knownAs.isNullOrEmpty()) cast.knownAs.joinToString(separator = ", ") else getString(
                R.string.dash
            )

        cast.deathday?.let {
            tvDeathDay.text = it
            tvDeathDay.isVisible = true
        }

    }

    private fun showLoading(isLoading: Boolean) {
        srl.isRefreshing = isLoading
        nsvCast.isVisible = !isLoading
    }


    companion object {
        @JvmStatic
        fun newInstance(castId: Long) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putLong(CAST_ID, castId)
                }
            }
    }
}