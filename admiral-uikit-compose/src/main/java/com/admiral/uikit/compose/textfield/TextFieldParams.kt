package com.admiral.uikit.compose.textfield

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.admiral.uikit.core.foundation.ColorState

@Suppress("LongParameterList")
class TextFieldParams(
    val inputText: String = "",
    val optionalText: String? = null,
    val placeholderText: String? = null,
    val additionalText: String? = null,
    val textColorState: ColorState? = null,
    val inputTextColorState: ColorState? = null,
    val errorColor: Int? = null,
    val iconBackgroundColorState: ColorState? = null,
    val iconColorState: ColorState? = null,
    val icon: Painter? = null,
    val onIconClick: () -> Unit = {},
    val isError: Boolean = false,
    val isReadOnly: Boolean = false,
    val isEnabled: Boolean = true,
    val onValueChange: (TextFieldValue) -> Unit = {},
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    val singleLine: Boolean = true,
    val maxLines: Int = Int.MAX_VALUE,
    val visualTransformation: VisualTransformation = VisualTransformation.None
)