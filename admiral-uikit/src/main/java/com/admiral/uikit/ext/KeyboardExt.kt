package com.admiral.uikit.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun View.showKeyboard() {
    if (!isAttachedToWindow) return

    context.getSystemService(Context.INPUT_METHOD_SERVICE)?.let {
        if (!isFocusableInTouchMode) {
            isFocusableInTouchMode = true
        }

        if (!isFocusable) {
            isFocusable = true
        }

        requestFocus()

        (it as InputMethodManager).showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}