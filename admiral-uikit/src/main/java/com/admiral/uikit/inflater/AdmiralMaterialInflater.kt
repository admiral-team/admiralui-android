package com.admiral.uikit.inflater

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.TextViewCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.theme.MaterialComponentsViewInflater
import com.admiral.uikit.components.switcher.Switcher

class AdmiralMaterialInflater : MaterialComponentsViewInflater() {

    override fun createView(context: Context, name: String?, attrs: AttributeSet?): View? {
        return when (name) {
            "ConstraintLayout",
            ConstraintLayout::class.java.canonicalName -> com.admiral.uikit.layout.ConstraintLayout(context, attrs)

            "FrameLayout",
            FrameLayout::class.java.canonicalName -> com.admiral.uikit.layout.FrameLayout(context, attrs)

            "LinearLayout",
            LinearLayout::class.java.canonicalName -> com.admiral.uikit.layout.LinearLayout(context, attrs)

            "RelativeLayout",
            RelativeLayout::class.java.canonicalName -> com.admiral.uikit.layout.RelativeLayout(context, attrs)

            "Button", Button::class.java.canonicalName,
            AppCompatButton::class.java.canonicalName -> com.admiral.uikit.components.button.Button(context, attrs)

            "TextView", TextView::class.java.canonicalName,
            "TextViewCompat", TextViewCompat::class.java.canonicalName
            -> com.admiral.uikit.components.text.TextView(context, attrs)

            "SwitchCompat", SwitchCompat::class.java.canonicalName,
            "Switch", Switch::class.java.canonicalName -> Switcher(context, attrs)

            "RadioButton", RadioButton::class.java.canonicalName ->
                com.admiral.uikit.components.radiobutton.RadioButton(
                    context,
                    attrs
                )

            "ChipGroup", ChipGroup::class.java.canonicalName -> com.admiral.uikit.components.chip.ChipGroup(
                context,
                attrs
            )
            "Chip", Chip::class.java.canonicalName -> com.admiral.uikit.components.chip.Chip(context, attrs)

            "CheckBox", CheckBox::class.java.canonicalName -> com.admiral.uikit.components.checkbox.CheckBox(
                context,
                attrs
            )

            else -> super.createView(context, name, attrs)
        }
    }
}