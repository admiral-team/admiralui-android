package com.admiral.demo.features.home.textFields

import android.os.Bundle
import android.text.InputFilter
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTextFieldsSmsBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class TextFieldsSmsFragment : BaseFragment(R.layout.fmt_text_fields_sms) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsSmsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    binding.defaultTab.id -> {
                        binding.textField.isError = false
                        binding.textField.isEnabled = true
                        binding.textField.isEditEnabled = true
                    }
                    binding.disabled.id -> {
                        binding.textField.isError = false
                        binding.textField.isEnabled = false
                        binding.textField.isEditEnabled = true
                    }
                    binding.error.id -> {
                        binding.textField.isError = true
                        binding.textField.isEnabled = true
                        binding.textField.isEditEnabled = true
                    }
                }
            }
        }

        binding.textField.inputLayout.editText?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(MAX))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }

    companion object {
        const val MAX = 12
    }
}
