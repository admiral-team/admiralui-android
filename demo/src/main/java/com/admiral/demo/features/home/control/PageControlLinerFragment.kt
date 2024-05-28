package com.admiral.demo.features.home.control

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtPageControlLinerBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class PageControlLinerFragment : BaseFragment(
    layoutId = R.layout.fmt_page_control_liner,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding((FmtPageControlLinerBinding::bind))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        with(binding) {
            pageControl.setTabsCount(TWO)

            btnNext.setOnClickListener {
                pageControl.selectTab(
                    pageControl.getTabAt(
                        (pageControl.selectedTabPosition + 1) % pageControl.tabCount
                    )
                )
            }

            tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(
                    radioButton: View?,
                    isChecked: Boolean,
                    checkedId: Int
                ) {
                    when (checkedId) {
                        two.id -> {
                            pageControl.setTabsCount(TWO)
                        }

                        three.id -> {
                            pageControl.setTabsCount(THREE)
                        }

                        four.id -> {
                            pageControl.setTabsCount(FOUR)
                        }

                        five.id -> {
                            pageControl.setTabsCount(FIVE)
                        }

                        six.id -> {
                            pageControl.setTabsCount(SIX)
                        }

                        seven.id -> {
                            pageControl.setTabsCount(SEVEN)
                        }

                        eight.id -> {
                            pageControl.setTabsCount(EIGHT)
                        }

                        nine.id -> {
                            pageControl.setTabsCount(NINE)
                        }
                    }
                }
            }
        }
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