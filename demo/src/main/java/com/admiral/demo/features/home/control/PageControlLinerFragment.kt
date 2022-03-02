package com.admiral.demo.features.home.control

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtPageControlLinerBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class PageControlLinerFragment : BaseFragment(R.layout.fmt_page_control_liner) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding((FmtPageControlLinerBinding::bind))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.pageControl.setTabsCount(TWO)

        binding.btnNext.setOnClickListener {
            binding.pageControl.selectTab(
                binding.pageControl.getTabAt(
                    (binding.pageControl.selectedTabPosition + 1) % binding.pageControl.tabCount
                )
            )
        }

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    binding.two.id -> {
                        binding.pageControl.setTabsCount(TWO)
                    }
                    binding.three.id -> {
                        binding.pageControl.setTabsCount(THREE)
                    }
                    binding.four.id -> {
                        binding.pageControl.setTabsCount(FOUR)
                    }
                    binding.five.id -> {
                        binding.pageControl.setTabsCount(FIVE)
                    }
                    binding.six.id -> {
                        binding.pageControl.setTabsCount(SIX)
                    }
                    binding.seven.id -> {
                        binding.pageControl.setTabsCount(SEVEN)
                    }
                    binding.eight.id -> {
                        binding.pageControl.setTabsCount(EIGHT)
                    }
                    binding.nine.id -> {
                        binding.pageControl.setTabsCount(NINE)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }

    private companion object {
        const val TWO = 2
        const val THREE = 3
        const val FOUR = 4
        const val FIVE = 5
        const val SIX = 6
        const val SEVEN = 7
        const val EIGHT = 8
        const val NINE = 9
    }
}