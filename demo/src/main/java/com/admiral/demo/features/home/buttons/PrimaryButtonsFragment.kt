package com.admiral.demo.features.home.buttons

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtButtonsPrimaryBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class PrimaryButtonsFragment : BaseFragment(R.layout.fmt_buttons_primary) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtButtonsPrimaryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        var isLoading = false

        with(binding) {
            btnBigIconStart.setOnClickListener {
                isLoading = !isLoading
                btnAddition.isLoading = isLoading
            }
            tabsState.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(
                    radioButton: View?,
                    isChecked: Boolean,
                    checkedId: Int
                ) {
                    val isEnabled = checkedId == R.id.defaultTab
                    btnAddition.isEnabled = isEnabled
                    btnBigIconStart.isEnabled = isEnabled
                    btnBigIconEnd.isEnabled = isEnabled
                    btnBig.isEnabled = isEnabled
                    btnMediumIconStart.isEnabled = isEnabled
                    btnMediumIconEnd.isEnabled = isEnabled
                    btnMedium.isEnabled = isEnabled
                    btnSmallIconStart.isEnabled = isEnabled
                    btnSmallIconEnd.isEnabled = isEnabled
                    btnSmall.isEnabled = isEnabled
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}