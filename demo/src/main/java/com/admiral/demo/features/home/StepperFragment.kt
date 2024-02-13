package com.admiral.demo.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtStepperBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.stepper.StepperStyle
import com.admiral.uikit.view.checkable.CheckableGroup

class StepperFragment : BaseFragment(
    layoutId = R.layout.fmt_stepper,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtStepperBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.tabCompleted -> {
                        binding.stepper.stepperStyle = StepperStyle.Done
                    }
                    R.id.tabCurrent -> {
                        binding.stepper.stepperStyle = StepperStyle.Current
                    }
                    R.id.tabNext -> {
                        binding.stepper.stepperStyle = StepperStyle.Next
                    }
                    R.id.tabError -> {
                        binding.stepper.stepperStyle = StepperStyle.Error
                    }
                    R.id.tabDisabled -> {
                        binding.stepper.stepperStyle = StepperStyle.Disabled
                    }
                }
            }
        }
    }
}