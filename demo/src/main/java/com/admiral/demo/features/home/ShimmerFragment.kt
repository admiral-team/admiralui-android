package com.admiral.demo.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtShimmerBinding
import com.admiral.demo.features.main.NavigationViewModel

class ShimmerFragment : BaseFragment(
    layoutId = R.layout.fmt_shimmer,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtShimmerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerToolbar(binding.toolbar, true, navigationViewModel::close)
    }
}
