package com.admiral.demo.features.home.alert

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getDrawable
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.themes.ThemeManager
import com.admiral.uikit.components.dialogsfragment.AlertDialogFragment
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.databinding.AdmiralViewPopUpDialogBinding
import com.admiral.uikit.R as uikit

class PopUpDialogFragment : AlertDialogFragment(uikit.layout.admiral_view_pop_up_dialog) {

    private val binding by viewBinding(AdmiralViewPopUpDialogBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    private fun setUpUi() {
        with(binding) {
            popUpRoot.backgroundTintList =
                ColorStateList.valueOf(ThemeManager.theme.palette.backgroundExtraSurface)

            ivTopImage.setImageDrawable(
                getDrawable(
                    requireContext(),
                    R.drawable.test_ic_pop_up
                )
            )

            tvTitle.text = getString(R.string.pop_up_example_title)

            tvBody.apply {
                text = getString(R.string.cell_lorem_ipsum)
                textColor = ColorState(normalEnabled = ThemeManager.theme.palette.textSecondary)
            }

            buttonAction.apply {
                text = getString(R.string.pop_up_example_button)
                setOnClickListener { this@PopUpDialogFragment.dismiss() }
            }

            buttonAlternative.apply {
                text = getString(R.string.pop_up_example_button_alternative)
                setOnClickListener {
                    this@PopUpDialogFragment.dismiss()
                }
            }
        }
    }
}