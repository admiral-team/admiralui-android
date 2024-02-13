package com.admiral.demo.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtMapElementsBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.map.IconTextGenerator
import com.admiral.uikit.components.map.IconZoomGenerator

class MapElementsFragment : BaseFragment(
    layoutId = R.layout.fmt_map_elements,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtMapElementsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.iv1.setImageBitmap(IconTextGenerator(requireContext()).makeTextIcon("22"))
        binding.iv2.setImageBitmap(IconTextGenerator(requireContext()).makeTextIcon("222"))
        binding.iv3.setImageBitmap(IconZoomGenerator(requireContext()).makePlusIcon(EXAMPLE_NUMBER))
        binding.iv4.setImageBitmap(IconZoomGenerator(requireContext()).makeMinusIcon(EXAMPLE_NUMBER))
        binding.iv5.setImageBitmap(IconZoomGenerator(requireContext()).makeLocationIcon(EXAMPLE_NUMBER))
    }

    private companion object {
        const val EXAMPLE_NUMBER = 40
    }
}