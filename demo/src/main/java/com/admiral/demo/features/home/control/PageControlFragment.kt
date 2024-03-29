package com.admiral.demo.features.home.control

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtPageControlBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.PageControlCircleScreen
import com.admiral.demo.screen.PageControlLinerScreen

class PageControlFragment : BaseFragment(
    layoutId = R.layout.fmt_page_control,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding((FmtPageControlBinding::bind))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnCircle.setOnClickListener {
            navigationViewModel.open(PageControlCircleScreen())
        }

        binding.btnLiner.setOnClickListener {
            navigationViewModel.open(PageControlLinerScreen())
        }
    }
}