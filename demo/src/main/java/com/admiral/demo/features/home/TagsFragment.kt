package com.admiral.demo.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTagsBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class TagsFragment : BaseFragment(
    layoutId = R.layout.fmt_tags,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTagsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.defaultTab -> {
                        binding.chips.isEnabled = true
                        binding.gray.isEnabled = true
                        binding.green.isEnabled = true
                        binding.red.isEnabled = true
                        binding.yellow.isEnabled = true
                    }
                    R.id.disabledTab -> {
                        binding.chips.isEnabled = false
                        binding.gray.isEnabled = false
                        binding.green.isEnabled = false
                        binding.red.isEnabled = false
                        binding.yellow.isEnabled = false
                    }
                }
            }
        }
    }
}