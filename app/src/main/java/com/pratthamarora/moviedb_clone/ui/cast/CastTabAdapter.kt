package com.pratthamarora.moviedb_clone.ui.cast

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CastTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AboutFragment.newInstance("1", "2")
            else -> PhotoFragment.newInstance("1", "2")

        }
    }
}