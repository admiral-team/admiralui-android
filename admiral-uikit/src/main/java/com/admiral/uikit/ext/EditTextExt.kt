package com.admiral.uikit.ext

import android.widget.EditText
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat

internal fun EditText.applyStyle(@StyleRes styleRes: Int) {
    TextViewCompat.setTextAppearance(this, styleRes)
}