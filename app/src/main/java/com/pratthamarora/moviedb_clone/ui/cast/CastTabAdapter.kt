package com.pratthamarora.moviedb_clone.ui.cast

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pratthamarora.moviedb_clone.ui.cast.about.AboutFragment

class CastTabAdapter(fragment: Fragment, private val castId: Long) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AboutFragment.newInstance(castId)
            else -> PhotoFragment.newInstance("1", "2")

        }
    }
}