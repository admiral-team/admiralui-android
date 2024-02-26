package com.admiral.demo.features.home.informers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtInformersBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.InformersBigScreen
import com.admiral.demo.screen.InformersSmallScreen

class InformersFragment : BaseFragment(
    layoutId = R.layout.fmt_informers,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtInformersBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnInformersBig.setOnClickListener {
            navigationViewModel.open(InformersBigScreen())
        }

        binding.btnInformersSmall.setOnClickListener {
            navigationViewModel.open(InformersSmallScreen())
        }
    }
}
