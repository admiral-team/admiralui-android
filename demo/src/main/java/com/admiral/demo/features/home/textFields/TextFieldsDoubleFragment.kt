package com.admiral.demo.features.home.textFields

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTextFieldsDoubleBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class TextFieldsDoubleFragment : BaseFragment(R.layout.fmt_text_fields_double) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsDoubleBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    binding.defaultTab.id -> {
                        binding.textField.isError = false
                        binding.textField.isEnabled = true
                        binding.textField.leftTextField.isEditEnabled = true
                        binding.textField.rightTextField.isEditEnabled = true

                        binding.textFieldSecond.isError = false
                        binding.textFieldSecond.isEnabled = true
                        binding.textFieldSecond.leftTextField.isEditEnabled = true
                        binding.textFieldSecond.rightTextField.isEditEnabled = true
                    }
                    binding.disabled.id -> {
                        binding.textField.isError = false
                        binding.textField.isEnabled = false
                        binding.textField.leftTextField.isEditEnabled = true
                        binding.textField.rightTextField.isEditEnabled = true

                        binding.textFieldSecond.leftTextField.isEditEnabled = true
                        binding.textFieldSecond.rightTextField.isEditEnabled = true
                        binding.textFieldSecond.isError = false
                        binding.textFieldSecond.isEnabled = false
                    }
                    binding.error.id -> {
                        binding.textField.isError = true
                        binding.textField.isEnabled = true
                        binding.textField.leftTextField.isEditEnabled = true
                        binding.textField.rightTextField.isEditEnabled = true

                        binding.textFieldSecond.isError = true
                        binding.textFieldSecond.isEnabled = true
                        binding.textFieldSecond.leftTextField.isEditEnabled = true
                        binding.textFieldSecond.rightTextField.isEditEnabled = true
                    }
                    binding.read.id -> {
                        binding.textField.isError = false
                        binding.textField.isEnabled = true
                        binding.textField.leftTextField.isEditEnabled = false
                        binding.textField.rightTextField.isEditEnabled = false

                        binding.textFieldSecond.isError = false
                        binding.textFieldSecond.isEnabled = true
                        binding.textFieldSecond.leftTextField.isEditEnabled = false
                        binding.textFieldSecond.rightTextField.isEditEnabled = false

                        val imm: InputMethodManager =
                            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}
