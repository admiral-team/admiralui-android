package com.admiral.uikit.components.textfield

import android.content.Context
import android.util.AttributeSet
import com.admiral.uikit.R
import com.admiral.uikit.core.ext.dpToPx
import com.google.android.material.textfield.TextInputLayout

open class TextFieldInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    override fun getBaseline(): Int {
        prefixTextView.setPadding(0, 0, resources.getDimension(R.dimen.module_x1).dpToPx(context), 0)
        return if (editText != null) {
            editText?.let {
                measuredHeight - (it.measuredHeight - it.baseline - it.paddingTop)
            } ?: super.baseline
        } else {
            super.baseline
        }
    }
}