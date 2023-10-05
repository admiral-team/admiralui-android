package com.admiral.demo.features.home.textFields

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTextFieldsPincodeBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.keyboard.KeyboardNum
import com.admiral.uikit.components.pincode.PinCodeState
import com.admiral.uikit.view.checkable.CheckableGroup
import com.admiral.resources.R as res

class TextFieldsPincodeFragment : BaseFragment(R.layout.fmt_text_fields_pincode) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsPincodeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.number.onValueChange = { old, new ->
            binding.pinCodeView.dotsCount = new
        }
        binding.pinCodeView.activeDotsCount = 0

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    binding.defaultTab.id -> {
                        binding.pinCodeView.state = PinCodeState.DEFAULT
                    }

                    binding.error.id -> {
                        binding.pinCodeView.state = PinCodeState.ERROR
                    }

                    binding.successTab.id -> {
                        binding.pinCodeView.state = PinCodeState.SUCCESS
                    }
                }
            }
        }

        binding.pinKeyboard.isRightAdditionalButtonVisible = true
        binding.pinKeyboard.rightAdditionalButtonIcon =
            ContextCompat.getDrawable(requireContext(), res.drawable.admiral_ic_fingerprint_outline)

        binding.pinKeyboard.keyListener = {
            when (it) {
                KeyboardNum.ADDITIONAL_RIGHT -> {
                    binding.pinCodeView.activeDotsCount--
                    if (binding.pinCodeView.activeDotsCount == 0) {
                        binding.pinKeyboard.rightAdditionalButtonIcon =
                            ContextCompat.getDrawable(
                                requireContext(),
                                res.drawable.admiral_ic_fingerprint_outline
                            )
                    }
                }

                else -> {
                    binding.pinCodeView.activeDotsCount++
                    binding.pinKeyboard.rightAdditionalButtonIcon =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_pin_keyboard_remove
                        )
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}
