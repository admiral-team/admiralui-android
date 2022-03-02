package com.admiral.demo.features.home.onboarding

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.admiral.demo.features.home.onboarding.OnboardingScreenFragment.Companion.KEY_POSITION

class OnboardingPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return OnboardingScreenFragment().apply {
            arguments = bundleOf(KEY_POSITION to position)
        }
    }

    private companion object {
        const val ITEM_COUNT = 3
    }
}