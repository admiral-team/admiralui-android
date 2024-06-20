package com.admiral.uikit.compose.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.button.AdmiralButtonColor.ghost
import com.admiral.uikit.compose.button.Button
import com.admiral.uikit.compose.button.ButtonColor
import com.admiral.uikit.compose.text.Text
import com.admiral.uikit.compose.text.TextColor
import com.admiral.uikit.compose.util.DIMEN_X10
import com.admiral.uikit.compose.util.DIMEN_X4

@Composable
@Suppress("LongParameterList", "LongMethod")
fun ErrorView(
    modifier: Modifier = Modifier,
    text: String,
    textColor: TextColor = TextColor(textColorNormal = AdmiralTheme.colors.textSecondary),
    buttonText: String,
    buttonColor: ButtonColor = ghost(),
    onButtonClick: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = DIMEN_X4),
            textAlign = TextAlign.Center,
            text = text,
            color = textColor,
            style = AdmiralTheme.typography.subhead3
        )
        Spacer(modifier = Modifier.padding(DIMEN_X10))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DIMEN_X4),
            color = buttonColor,
            actionText = buttonText,
        ) {
            onButtonClick.invoke()
        }
    }
}

@Preview
@Composable
private fun ErrorViewPreview() {
    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            ErrorView(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                buttonText = "Альтернативное действие"
            )
        }
    }
}
