package com.pratthamarora.moviedb_clone.ui.cast.photo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pratthamarora.moviedb_clone.R
import com.pratthamarora.moviedb_clone.utils.Constants.CAST_ID
import com.pratthamarora.moviedb_clone.utils.Status.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_photo.*
import javax.inject.Inject
import kotlin.properties.Delegates


@AndroidEntryPoint
class PhotoFragment : Fragment(R.layout.fragment_photo) {

    private var castId by Delegates.notNull<Long>()
    private lateinit var photoAdapter: PhotoAdapter

    @Inject
    lateinit var photoViewModelFactory: PhotoViewModel.AssistedFactory

    private val photoViewModel by viewModels<PhotoViewModel> {
        PhotoViewModel.provideFactory(
            photoViewModelFactory,
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

        initViews()

        photoViewModel.photo.observe(viewLifecycleOwner) {
            when (it.status) {
                SUCCESS -> {
                    it.data?.let { photos ->
                        photoAdapter.submitList(photos)
                        showLoading(false)
                    }
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

    private fun initViews() {
        photoAdapter = PhotoAdapter()
        rvPhoto.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = photoAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        srlPhoto.isRefreshing = isLoading
    }


    companion object {
        @JvmStatic
        fun newInstance(castId: Long) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putLong(CAST_ID, castId)
                }
            }
    }
}