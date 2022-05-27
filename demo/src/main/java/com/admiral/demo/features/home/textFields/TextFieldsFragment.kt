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
import com.admiral.demo.screen.TextFieldsCardNumberScreen
import com.admiral.demo.screen.TextFieldsDoubleScreen
import com.admiral.demo.screen.TextFieldsFeedbackScreen
import com.admiral.demo.screen.TextFieldsStandardScreen
import com.admiral.demo.screen.TextFieldsNumberScreen
import com.admiral.demo.screen.TextFieldsPincodeScreen
import com.admiral.demo.screen.TextFieldsSliderScreen
import com.admiral.demo.screen.TextFieldsSmsScreen

class TextFieldsFragment : BaseFragment(R.layout.fmt_text_fields) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnStandard.setOnClickListener {
            navigationViewModel.open(TextFieldsStandardScreen())
        }

        binding.btnDouble.setOnClickListener {
            navigationViewModel.open(TextFieldsDoubleScreen())
        }

        binding.btnSlider.setOnClickListener {
            navigationViewModel.open(TextFieldsSliderScreen())
        }

        binding.btnCardNumber.setOnClickListener {
            navigationViewModel.open(TextFieldsCardNumberScreen())
        }

        binding.btnSmsCode.setOnClickListener {
            navigationViewModel.open(TextFieldsSmsScreen())
        }

        binding.btnNumber.setOnClickListener {
            navigationViewModel.open(TextFieldsNumberScreen())
        }

        binding.btnFeedback.setOnClickListener {
            navigationViewModel.open(TextFieldsFeedbackScreen())
        }

        binding.btnPincode.setOnClickListener {
            navigationViewModel.open(TextFieldsPincodeScreen())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}
