package com.admiral.demo.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCheckBoxBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class CheckBoxFragment : BaseFragment(
    layoutId = R.layout.fmt_check_box,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtCheckBoxBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.defaultTab -> {
                        binding.checkBox1.isEnabled = true
                        binding.checkBox2.isEnabled = true
                        binding.checkBox3.isEnabled = true
                        binding.checkBox4.isEnabled = true
                        binding.checkBox5.isEnabled = true
                        binding.checkBox6.isEnabled = true
                    }

                    R.id.disabledTab -> {
                        binding.checkBox1.isEnabled = false
                        binding.checkBox2.isEnabled = false
                        binding.checkBox3.isEnabled = false
                        binding.checkBox4.isEnabled = false
                        binding.checkBox5.isEnabled = false
                        binding.checkBox6.isEnabled = false
                    }
                }
            }
        }
    }
}