package com.pratthamarora.moviedb_clone.ui.cast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.tabs.TabLayoutMediator
import com.pratthamarora.moviedb_clone.R
import com.pratthamarora.moviedb_clone.di.glide.GlideApp
import com.pratthamarora.moviedb_clone.utils.Constants.POSTER_ORG_URL
import kotlinx.android.synthetic.main.fragment_cast.*


class CastFragment : Fragment(R.layout.fragment_cast) {

    private val args by navArgs<CastFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        backButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        val cast = args.cast

        GlideApp.with(ivCast)
            .load("$POSTER_ORG_URL${cast.profilePath}")
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)
            .transform(CircleCrop())
            .into(ivCast)

        tvName.text = cast.name

        castViewPager.adapter = CastTabAdapter(this, cast.id)
        castViewPager.offscreenPageLimit = 2
        TabLayoutMediator(castTabs, castViewPager) { tab, pos ->
            when (pos) {
                0 -> tab.text = getString(R.string.about)
                else -> tab.text = getString(R.string.photo)
            }
        }.attach()

    }
}