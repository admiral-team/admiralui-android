package com.admiral.demo.features.home.alert

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.admiral.demo.R
import com.admiral.themes.ThemeManager
import com.admiral.uikit.components.button.Button
import com.admiral.uikit.components.dialogsfragment.AlertDialogFragment
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.common.foundation.ColorState

class PopUpDialogFragment : AlertDialogFragment(R.layout.admiral_view_pop_up_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().findViewById<LinearLayout>(R.id.popUpRoot).apply {
            backgroundTintList = ColorStateList(
                arrayOf(
                    intArrayOf()
                ),
                intArrayOf(
                    ThemeManager.theme.palette.backgroundExtraSurface
                )
            )
        }
        requireView().findViewById<ImageView>(R.id.ivTopImage).apply {
            setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.test_ic_pop_up))
        }
        requireView().findViewById<TextView>(R.id.tvBody).apply {
            text = getString(R.string.cell_lorem_ipsum)
            textColor = ColorState(normalEnabled = ThemeManager.theme.palette.textSecondary)
        }
        requireView().findViewById<TextView>(R.id.tvTitle).apply {
            text = context.getString(R.string.pop_up_example_title)
        }
        requireView().findViewById<Button>(R.id.buttonAction).apply {
            text = context.getString(R.string.pop_up_example_button)
            setOnClickListener {
                this@PopUpDialogFragment.dismiss()
            }
        }
        requireView().findViewById<Button>(R.id.buttonAlternative).apply {
            text = context.getString(R.string.pop_up_example_button_alternative)
            setOnClickListener {
                this@PopUpDialogFragment.dismiss()
            }
        }
    }
}