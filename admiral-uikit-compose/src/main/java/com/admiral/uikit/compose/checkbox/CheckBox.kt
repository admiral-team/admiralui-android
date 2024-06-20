package com.admiral.uikit.compose.checkbox

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.util.DIMEN_X2

/**
 * @see [TriStateCheckbox] if you require support for an indeterminate state, or more advanced
 * color customization between states.
 *
 * @param isChecked whether Checkbox is checked or unchecked
 * @param onCheckedChange callback to be invoked when checkbox is being clicked,
 * therefore the change of checked state in requested.  If null, then this is passive
 * and relies entirely on a higher-level component to control the "checked" state.
 * @param modifier Modifier to be applied to the layout of the checkbox
 * @param isError displays normal color or error color
 * @param text displays text next to the checkbox
 * @param testStyle sets the text style
 * @param isEnabled whether the component is enabled or grayed out
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this Checkbox. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this Checkbox in different [Interaction]s.
 * @param colors [CheckBox] that will be used to determine the color of the checkmark / box
 * / border in different states. See [AdmiralCheckBoxColor].
 */
@Composable
fun CheckBox(
    isChecked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    text: String? = null,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: AdmiralCheckBoxColor = checkBoxColor(),
    testStyle: TextStyle = AdmiralTheme.typography.subhead3,
) {
    val textColor = if (isChecked)
        colors.getTextCheckedColor(isEnabled = isEnabled).value
    else
        colors.getTextNormalColor(isEnabled = isEnabled).value

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TriStateCheckbox(
            state = ToggleableState(isChecked),
            onClick = if (onCheckedChange != null) {
                { onCheckedChange(!isChecked) }
            } else null,
            interactionSource = interactionSource,
            isError = isError,
            isEnabled = isEnabled,
            colors = colors,
            modifier = modifier
        )

        text?.let {
            Spacer(modifier = Modifier.width(DIMEN_X2))

            Text(
                text = it,
                style = testStyle,
                color = textColor
            )
        }
    }
}

@Preview
@Composable
private fun AdmiralCheckBoxPreview() {
    var checked by remember { mutableStateOf(false) }

    AdmiralTheme {
        Box(
            modifier = Modifier
                .background(color = AdmiralTheme.colors.backgroundBasic)
                .padding(DIMEN_X2)
        ) {
            CheckBox(
                isChecked = checked,
                text = "Text",
                onCheckedChange = { isChecked -> checked = isChecked },
            )
        }
    }
}