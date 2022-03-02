package com.admiral.demo.features.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtSpinnerBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class SpinnerFragment : BaseFragment(R.layout.fmt_spinner) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtSpinnerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.smallTab -> {
                        binding.spinnerSmall.isVisible = true
                        binding.spinnerMedium.isVisible = false
                        binding.spinnerBig.isVisible = false
                    }
                    R.id.mediumTab -> {
                        binding.spinnerSmall.isVisible = false
                        binding.spinnerMedium.isVisible = true
                        binding.spinnerBig.isVisible = false
                    }
                    R.id.bigTab -> {
                        binding.spinnerSmall.isVisible = false
                        binding.spinnerMedium.isVisible = false
                        binding.spinnerBig.isVisible = true
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}