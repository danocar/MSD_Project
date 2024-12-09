package com.example.project

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


// Adapter for managing fragments in ViewPager2.
class FragmentPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {


    // Returns the number of fragments.
    override fun getItemCount(): Int {
        return 2 // Two fragments: My Plants and Find Plants
    }

    // Creates the fragment for the given position
    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            FirstFragment() // My Plants tab
        else
            SecondFragment() // Find Plants tab
    }
}
