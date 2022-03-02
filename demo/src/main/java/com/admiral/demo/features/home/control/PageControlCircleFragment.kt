package com.admiral.demo.features.home.control

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtPageControlCircleBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class PageControlCircleFragment : BaseFragment(R.layout.fmt_page_control_circle) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding((FmtPageControlCircleBinding::bind))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        var step = TOTAL
        var selectedItem = 0

        binding.pageControl.setOnClickListener {
            selectedItem++
            binding.pageControl.setProgress((selectedItem * step).toInt())
        }

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    binding.two.id -> {
                        step = TOTAL / TWO
                        binding.pageControl.setProgress(0)
                        selectedItem = 0
                    }
                    binding.three.id -> {
                        step = TOTAL / THREE
                        binding.pageControl.setProgress(0)
                        selectedItem = 0
                    }
                    binding.four.id -> {
                        step = TOTAL / FOUR
                        binding.pageControl.setProgress(0)
                        selectedItem = 0
                    }
                    binding.five.id -> {
                        step = TOTAL / FIVE
                        binding.pageControl.setProgress(0)
                        selectedItem = 0
                    }
                    binding.six.id -> {
                        step = TOTAL / SIX
                        binding.pageControl.setProgress(0)
                        selectedItem = 0
                    }
                    binding.seven.id -> {
                        step = TOTAL / SEVEN
                        binding.pageControl.setProgress(0)
                        selectedItem = 0
                    }
                    binding.eight.id -> {
                        step = TOTAL / EIGHT
                        binding.pageControl.setProgress(0)
                        selectedItem = 0
                    }
                    binding.nine.id -> {
                        step = TOTAL / NINE
                        binding.pageControl.setProgress(0)
                        selectedItem = 0
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }

    private companion object {
        const val TOTAL = 100.0
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