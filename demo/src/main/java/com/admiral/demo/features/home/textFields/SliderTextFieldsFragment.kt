package com.admiral.demo.features.home.textFields

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTextFieldsSliderBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class SliderTextFieldsFragment : BaseFragment(R.layout.fmt_text_fields_slider) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsSliderBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        with(binding) {
            tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(
                    radioButton: View?,
                    isChecked: Boolean,
                    checkedId: Int
                ) {
                    when (checkedId) {
                        defaultTab.id -> {
                            slider.apply {
                                isError = false
                                isEnabled = true
                            }
                            doubleSlider.apply {
                                isError = false
                                isEnabled = true
                            }
                        }
                        disabled.id -> {
                            slider.apply {
                                isError = false
                                isEnabled = false
                            }
                            doubleSlider.apply {
                                isError = false
                                isEnabled = false
                            }
                        }
                        error.id -> {
                            slider.apply {
                                isError = true
                                isEnabled = true
                            }
                            doubleSlider.apply {
                                isError = true
                                isEnabled = true
                            }
                        }
                    }
                }
            }

            slider.apply {
                valueFrom = MIN_VALUE
                valueTo = MAX_VALUE
                placeholderText = MIN_VALUE.toInt().toString()
                setOnIconClickListener {
                    if (slider.isInformerVisible) {
                        slider.hideInformer()
                    } else {
                        slider.showInformer("Text Informer")
                    }
                }
            }

            doubleSlider.apply {
                valueFrom = MIN_VALUE
                valueTo = MAX_VALUE
                placeholderText = MIN_VALUE.toInt().toString()
                placeholderTextTo = MAX_VALUE.toInt().toString()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }

    private companion object {
        const val MIN_VALUE = 100f
        const val MAX_VALUE = 10000f
    }
}
