package com.admiral.uikit.ext

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat

internal fun TextView.applyStyle(@StyleRes styleRes: Int) {
    TextViewCompat.setTextAppearance(this, styleRes)
}

internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(ContextCompat.getColor(context, color))
