package com.admiral.demo.features.home.informers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtInformersSmallBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class InformersSmallFragment : BaseFragment(
    layoutId = R.layout.fmt_informers_small,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtInformersSmallBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabsState.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.defaultTab -> {
                        binding.informerAttention.isEnabled = true
                        binding.informerDefault.isEnabled = true
                        binding.informerError.isEnabled = true
                        binding.informerSuccess.isEnabled = true
                    }
                    R.id.disabledTab -> {
                        binding.informerAttention.isEnabled = false
                        binding.informerDefault.isEnabled = false
                        binding.informerError.isEnabled = false
                        binding.informerSuccess.isEnabled = false
                    }
                }
            }
        }
    }
}
