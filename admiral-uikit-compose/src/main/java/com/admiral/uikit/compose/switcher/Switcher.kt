package com.admiral.uikit.compose.switcher

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.AdmiralTheme.typography
import com.admiral.uikit.compose.text.AdmiralTextColor
import com.admiral.uikit.compose.text.Text
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Switcher(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    isEnabled: Boolean = true,
    text: String? = null,
    textStyle: TextStyle = typography.subhead3,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SwitcherColor = AdmiralSwithcerColors.default(),
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        text?.let {
            Text(
                text = text,
                style = textStyle,
                color = AdmiralTextColor.primary(
                    textColorNormal = colors.textEnabled,
                    textColorDisabled = colors.textDisabled,
                )
            )
        }

        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Switch(
                modifier = Modifier
                    .padding(start = SwitchStartPadding),
                checked = isChecked,
                enabled = isEnabled,
                interactionSource = interactionSource,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colors.checkedThumb,
                    checkedTrackColor = colors.checkedTrack,
                    checkedTrackAlpha = 1f,
                    uncheckedThumbColor = colors.uncheckedThumb,
                    uncheckedTrackColor = colors.uncheckedTrack,
                    uncheckedTrackAlpha = 1f,
                    disabledCheckedThumbColor = colors.disabledCheckedThumb,
                    disabledCheckedTrackColor = colors.disabledCheckedTrack,
                    disabledUncheckedThumbColor = colors.disabledUncheckedThumb,
                    disabledUncheckedTrackColor = colors.disabledUncheckedTrack,
                )
            )
        }
    }
}

private val SwitchStartPadding = 2.dp

@Preview
@Composable
private fun SwitcherPreview() {
    AdmiralTheme {
        var isCheckedOne by remember { mutableStateOf(true) }
        var isCheckedTwo by remember { mutableStateOf(false) }
        var isCheckedThree by remember { mutableStateOf(false) }
        var isCheckedFour by remember { mutableStateOf(true) }

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column(
                modifier = Modifier
                    .padding(DIMEN_X4),
                verticalArrangement = Arrangement.spacedBy(DIMEN_X2)
            ) {
                Switcher(
                    text = "Switch 1",
                    isChecked = isCheckedOne,
                    onCheckedChange = { newValue ->
                        isCheckedOne = newValue
                    }
                )

                Switcher(
                    isChecked = isCheckedTwo,
                    text = "Switch 2",
                    onCheckedChange = { newValue ->
                        isCheckedTwo = newValue
                    }
                )

                Switcher(
                    isChecked = isCheckedThree,
                    text = "Switch 3",
                    isEnabled = false,
                    onCheckedChange = { newValue ->
                        isCheckedThree = newValue
                    }
                )

                Switcher(
                    isChecked = isCheckedFour,
                    text = "Switch 4",
                    isEnabled = false,
                    onCheckedChange = { newValue ->
                        isCheckedFour = newValue
                    }
                )
            }
        }
    }
}
