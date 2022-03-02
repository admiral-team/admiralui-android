package com.admiral.demo.features.home.colors

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtColorPickerBinding
import com.admiral.demo.features.main.NavigationViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ColorPickerFragment : BaseFragment(R.layout.fmt_color_picker) {

    private val binding by viewBinding(FmtColorPickerBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val colorModel: ColorModel by lazy { requireArguments().getParcelable(ARG_COLOR)!! }
    private var blocked = false

    override val isThemePickerVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerToolbar(binding.toolbar, false, navigationViewModel::close)

        binding.toolbar.title = colorModel.name

        binding.colorPickerView.setInitialColor(colorModel.color)
        binding.colorPickerView.setLifecycleOwner(this)
        binding.colorPickerView.attachAlphaSlider(binding.alphaSlideBar)
        binding.colorPickerView.attachBrightnessSlider(binding.brightnessSlideBar)
        binding.colorPickerView.setColorListener(
            ColorEnvelopeListener { envelope: ColorEnvelope, fromUser: Boolean ->
                blocked = fromUser

                if (fromUser) {
                    val text = "#${envelope.hexCode}"
                    binding.textField.inputText = text
                }

                binding.alphaTileView.setPaintColor(envelope.color)
            })

        viewLifecycleOwner.lifecycleScope.launch {
            binding.textField.textFlow.collect {
                if (blocked) {
                    blocked = false
                } else {
                    it?.let {
                        try {
                            binding.colorPickerView.setInitialColor(Color.parseColor(it))
                        } catch (e: IllegalArgumentException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        binding.button.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable(ARG_COLOR, colorModel.copy(color = binding.colorPickerView.color))
            }
            setFragmentResult(ARG_COLOR, bundle)
            navigationViewModel.close()
        }
    }

    companion object {
        const val ARG_COLOR = "arg_color"
    }
}