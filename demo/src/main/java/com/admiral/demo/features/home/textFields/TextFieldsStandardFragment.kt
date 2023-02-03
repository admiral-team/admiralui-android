package com.admiral.demo.features.home.textFields

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.HorizontalScrollView
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTextFieldsStandardBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.chip.Chip
import com.admiral.uikit.components.chip.ChipGroup
import com.admiral.uikit.view.checkable.CheckableGroup

class TextFieldsStandardFragment : BaseFragment(R.layout.fmt_text_fields_standard) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsStandardBinding::bind)

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

    private fun addBottomView() {
        with(binding) {
            textField.addBottomView(HorizontalScrollView(requireContext()).apply {
                addView(ChipGroup(requireContext()).apply {
                    isSingleSelection = true
                    isSingleLine = true
                    addView(Chip(requireContext()).apply {
                        text = "500"
                        isCheckable = true
                    })
                    addView(Chip(requireContext()).apply {
                        text = "1000"
                        isCheckable = true
                    })
                    addView(Chip(requireContext()).apply {
                        text = "10 000"
                        isCheckable = true
                    })
                    addView(Chip(requireContext()).apply {
                        text = "50 000"
                        isCheckable = true
                    })

                    setOnCheckedChangeListener { _, checkedId ->
                        val chip: Chip? = this.findViewById(checkedId)

                        if (chip != null) {
                            textField.inputText = chip.text.toString()
                            textField.setSelection(textField.inputText.length)
                        }
                    }
                })
            })
        }
    }
}
