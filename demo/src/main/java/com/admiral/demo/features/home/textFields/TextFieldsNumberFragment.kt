package com.admiral.demo.features.home.textFields

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTextFieldsNumberBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.TextFieldsNumberDefaultScreen
import com.admiral.demo.screen.TextFieldsNumberSecondaryScreen

class TextFieldsNumberFragment : BaseFragment(
    layoutId = R.layout.fmt_text_fields_number,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsNumberBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        with(binding) {
            btnDefault.setOnClickListener {
                navigationViewModel.open(TextFieldsNumberDefaultScreen())
            }

            btnSecondary.setOnClickListener {
                navigationViewModel.open(TextFieldsNumberSecondaryScreen())
            }
        }
    }
}
