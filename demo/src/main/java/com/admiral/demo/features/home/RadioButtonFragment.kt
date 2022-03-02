package com.admiral.demo.features.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtRadioButtonBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class RadioButtonFragment : BaseFragment(R.layout.fmt_radio_button) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtRadioButtonBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.defaultTab -> {
                        binding.radioButton1.isEnabled = true
                        binding.radioButton2.isEnabled = true
                        binding.radioButton3.isEnabled = true
                        binding.radioButton4.isEnabled = true
                        binding.radioButton5.isEnabled = true
                        binding.radioButton6.isEnabled = true
                    }
                    R.id.disabledTab -> {
                        binding.radioButton1.isEnabled = false
                        binding.radioButton2.isEnabled = false
                        binding.radioButton3.isEnabled = false
                        binding.radioButton4.isEnabled = false
                        binding.radioButton5.isEnabled = false
                        binding.radioButton6.isEnabled = false
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}