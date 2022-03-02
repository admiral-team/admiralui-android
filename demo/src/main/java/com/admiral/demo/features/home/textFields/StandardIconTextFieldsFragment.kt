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
import com.admiral.demo.databinding.FmtTextFieldsStandardIconBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

// TODO: rename fragment maybe, there is only one text field with icon
class StandardIconTextFieldsFragment : BaseFragment(R.layout.fmt_text_fields_standard_icon) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsStandardIconBinding::bind)

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

                        binding.textFieldMasked.isError = false
                        binding.textFieldMasked.isEnabled = true
                        binding.textFieldMasked.isEditEnabled = true

                        binding.textFieldMultiline.isError = false
                        binding.textFieldMultiline.isEnabled = true
                        binding.textFieldMultiline.isEditEnabled = true
                    }
                    binding.disabled.id -> {
                        binding.textField.isError = false
                        binding.textField.isEnabled = false
                        binding.textField.isEditEnabled = true

                        binding.textFieldMasked.isError = false
                        binding.textFieldMasked.isEnabled = false
                        binding.textFieldMasked.isEditEnabled = true

                        binding.textFieldMultiline.isError = false
                        binding.textFieldMultiline.isEnabled = false
                        binding.textFieldMultiline.isEditEnabled = true
                    }
                    binding.error.id -> {
                        binding.textField.isError = true
                        binding.textField.isEnabled = true
                        binding.textField.isEditEnabled = true

                        binding.textFieldMasked.isError = true
                        binding.textFieldMasked.isEnabled = true
                        binding.textFieldMasked.isEditEnabled = true

                        binding.textFieldMultiline.isError = true
                        binding.textFieldMultiline.isEnabled = true
                        binding.textFieldMultiline.isEditEnabled = true
                    }
                    binding.read.id -> {
                        binding.textField.isError = false
                        binding.textField.isEnabled = true
                        binding.textField.isEditEnabled = false

                        binding.textFieldMasked.isError = false
                        binding.textFieldMasked.isEnabled = true
                        binding.textFieldMasked.isEditEnabled = false

                        binding.textFieldMultiline.isError = false
                        binding.textFieldMultiline.isEnabled = true
                        binding.textFieldMultiline.isEditEnabled = false

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
