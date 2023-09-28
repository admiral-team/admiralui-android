package com.admiral.uikit.core.ext

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat

fun TextView.applyStyle(@StyleRes styleRes: Int) {
    TextViewCompat.setTextAppearance(this, styleRes)
}

fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(ContextCompat.getColor(context, color))
