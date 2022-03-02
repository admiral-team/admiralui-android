package com.admiral.demo.features.home.onboarding

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtOnboardingBinding
import com.admiral.themes.ThemeManager

class OnboardingFragment : BaseFragment(R.layout.fmt_onboarding) {

    private val binding by viewBinding(FmtOnboardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.menuText = MENU_TEXT
        binding.toolbar.menuTextColor = ThemeManager.theme.palette.textAccent
        binding.toolbar.onMenuClickListener = View.OnClickListener {
            openMainFragment()
        }
        registerToolbar(binding.toolbar, true) {
            updatePage(DECREASE)
        }

        val pagerAdapter = OnboardingPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        updateProgressBar()

        binding.admiralOnboardingProgress.setOnClickListener {
            if (binding.viewPager.currentItem == LAST_PAGE) {
                openMainFragment()
            } else {
                updatePage(INCREASE)
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateProgressBar()

                if (position == 0) {
                    hideBackButton()
                } else {
                    showBackButton()
                }
            }
        })
    }

    private fun updatePage(increase: Int) {
        binding.viewPager.setCurrentItem(binding.viewPager.currentItem + increase, true)
    }

    private fun updateProgressBar() {
        binding.admiralOnboardingProgress.setProgress(STEP * (binding.viewPager.currentItem + 1))
    }

    private fun openMainFragment() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private companion object {
        const val STEP = 34
        const val LAST_PAGE = 2

        const val INCREASE = 1
        const val DECREASE = -1

        const val MENU_TEXT = "Пропустить"
    }
}