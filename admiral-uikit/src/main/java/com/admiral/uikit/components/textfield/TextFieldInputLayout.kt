package com.admiral.uikit.components.textfield

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

open class TextFieldInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    override fun getBaseline(): Int {
        return if (editText != null) {
            editText?.let {
                measuredHeight - (it.measuredHeight - it.baseline - it.paddingTop)
            } ?: super.getBaseline()
        } else {
            super.getBaseline()
        }
    }
}