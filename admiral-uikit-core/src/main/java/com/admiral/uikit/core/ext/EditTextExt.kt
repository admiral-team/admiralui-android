package com.admiral.uikit.core.ext

import android.widget.EditText
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat

internal fun EditText.applyStyle(@StyleRes styleRes: Int) {
    TextViewCompat.setTextAppearance(this, styleRes)
}

fun EditText.setSelectionEnd() {
    if (this.text.isNotEmpty()) {
        this.setSelection(this.text?.toString()?.length ?: 0)
    }
}