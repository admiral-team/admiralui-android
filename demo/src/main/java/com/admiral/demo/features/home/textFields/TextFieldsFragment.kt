package com.admiral.demo.features.home.textFields

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTextFieldsBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.StandardDoubleTextFieldsScreen
import com.admiral.demo.screen.StandardFeedbackTextFieldsScreen
import com.admiral.demo.screen.StandardNumberTextFieldsScreen
import com.admiral.demo.screen.StandardPincodeTextFieldsScreen
import com.admiral.demo.screen.StandardSliderTextFieldsScreen
import com.admiral.demo.screen.StandardTextFieldsScreen

class TextFieldsFragment : BaseFragment(R.layout.fmt_text_fields) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnStandard.setOnClickListener {
            navigationViewModel.open(StandardTextFieldsScreen())
        }

        binding.btnDouble.setOnClickListener {
            navigationViewModel.open(StandardDoubleTextFieldsScreen())
        }

        binding.btnSlider.setOnClickListener {
            navigationViewModel.open(StandardSliderTextFieldsScreen())
        }

        binding.btnNumber.setOnClickListener {
            navigationViewModel.open(StandardNumberTextFieldsScreen())
        }

        binding.btnFeedback.setOnClickListener {
            navigationViewModel.open(StandardFeedbackTextFieldsScreen())
        }

        binding.btnPincode.setOnClickListener {
            navigationViewModel.open(StandardPincodeTextFieldsScreen())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}
