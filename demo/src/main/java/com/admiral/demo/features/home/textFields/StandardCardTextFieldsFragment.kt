package com.admiral.demo.features.home.textFields

import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTextFieldsStandardCardBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.textfield.TextFieldStyle
import com.admiral.uikit.view.checkable.CheckableGroup
import com.redmadrobot.inputmask.MaskedTextChangedListener

class StandardCardTextFieldsFragment : BaseFragment(R.layout.fmt_text_fields_standard_card) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsStandardCardBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            registerToolbar(toolbar, true, navigationViewModel::close)

            tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                    when (checkedId) {
                        defaultTab.id -> {
                            textField.apply {
                                isError = false
                                isEnabled = true
                                isEditEnabled = true
                            }
                        }
                        disabled.id -> {
                            textField.apply {
                                isError = false
                                isEnabled = false
                                isEditEnabled = true
                            }
                        }
                        error.id -> {
                            textField.apply {
                                isError = true
                                isEnabled = true
                                isEditEnabled = true
                            }
                        }
                    }
                }
            }

            textField.apply {
                placeholderText = getString(R.string.text_fields_bank_card_placeholder)
                textFieldStyle = TextFieldStyle.Clipped

                MaskedTextChangedListener.Companion.installOn(
                    textField.editText,
                    BANK_CARD_MASK
                )
                editText.inputType = InputType.TYPE_CLASS_NUMBER
                editText.keyListener = DigitsKeyListener.getInstance(AVAILABLE_DIGITS)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }

    companion object {
        const val BANK_CARD_MASK = "[0000] [0000] [0000] [0000]"
        const val AVAILABLE_DIGITS = "1234567890 "
    }
}