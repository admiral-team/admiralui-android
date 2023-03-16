package com.admiral.uikit.components.textfield

import androidx.annotation.DimenRes
import com.admiral.themes.Font
import com.admiral.themes.ThemeManager
import com.admiral.uikit.R

/**
 * Define styles for [TextField]
 */
sealed class TextFieldStyle(
    @DimenRes val paddingTop: Int,
    @DimenRes val paddingBottom: Int,
    @DimenRes val paddingLeft: Int,
    @DimenRes val paddingRight: Int,
    @DimenRes val additionalTextPadding: Int,
    @DimenRes val dividerHeight: Int,
    val textStyle: Font,
    val additionalTextGravity: TextGravity
) {

    /**
     * Used as default style.
     */
    object Extended : TextFieldStyle(
        paddingTop = R.dimen.module_x4,
        paddingBottom = R.dimen.module_x1,
        paddingLeft = R.dimen.module_x4,
        paddingRight = R.dimen.module_x4,
        additionalTextPadding = R.dimen.module_x2,
        dividerHeight = R.dimen.admiral_text_field_divider_height_extended,
        textStyle = ThemeManager.theme.typography.body1,
        additionalTextGravity = TextGravity.Start
    )

    /**
     * Used as additional style. For example, for card number or sms code.
     */
    object Clipped : TextFieldStyle(
        paddingTop = R.dimen.module_x3,
        paddingBottom = R.dimen.module_x1,
        paddingLeft = R.dimen.module_x10,
        paddingRight = R.dimen.module_x10,
        additionalTextPadding = R.dimen.module_x1,
        dividerHeight = R.dimen.admiral_text_field_divider_height_clipped,
        textStyle = ThemeManager.theme.typography.title2,
        additionalTextGravity = TextGravity.Center
    )
}