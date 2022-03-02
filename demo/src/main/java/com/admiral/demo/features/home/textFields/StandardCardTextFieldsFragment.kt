package com.admiral.demo.features.home.textFields

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
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


class StandardCardTextFieldsFragment : BaseFragment(R.layout.fmt_text_fields_standard_card) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsStandardCardBinding::bind)

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

        binding.textField.textFieldStyle = TextFieldStyle.Clipped
        binding.textField.inputLayout.editText?.filters = arrayOf<InputFilter>(LengthFilter(MAX))

        binding.textField.textWatcher = CreditCardNumberFormattingTextWatcher()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }

    companion object {
        const val MAX = 19
    }
}

@Suppress("MagicNumber")
private class CreditCardNumberFormattingTextWatcher : TextWatcher {
    private var space = ' '

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable) {
        // Remove all spacing char
        var pos = 0
        while (true) {
            if (pos >= s.length) break
            if (space == s[pos] && ((pos + 1) % 5 != 0 || pos + 1 == s.length)) {
                s.delete(pos, pos + 1)
            } else {
                pos++
            }
        }

        // Insert char where needed.
        pos = 4
        while (true) {
            if (pos >= s.length) break
            val c = s[pos]
            // Only if its a digit where there should be a space we insert a space
            if ("0123456789".indexOf(c) >= 0) {
                s.insert(pos, "" + space)
            }
            pos += 5
        }
    }
}
