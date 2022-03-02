package com.admiral.demo.features.home.textFields

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTextFieldsStandardBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.StandardCardTextFieldsScreen
import com.admiral.demo.screen.StandardIconTextFieldsScreen
import com.admiral.demo.screen.StandardSmsTextFieldsScreen

class StandardTextFieldsFragment : BaseFragment(R.layout.fmt_text_fields_standard) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsStandardBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnStandardIcon.setOnClickListener {
            navigationViewModel.open(StandardIconTextFieldsScreen())
        }

        binding.btnCardNumber.setOnClickListener {
            navigationViewModel.open(StandardCardTextFieldsScreen())
        }

        binding.btnSmsCode.setOnClickListener {
            navigationViewModel.open(StandardSmsTextFieldsScreen())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}
