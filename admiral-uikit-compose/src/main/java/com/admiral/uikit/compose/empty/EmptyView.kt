package com.admiral.uikit.compose.empty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.button.AdmiralButtonColor.primary
import com.admiral.uikit.compose.button.Button
import com.admiral.uikit.compose.button.ButtonColor
import com.admiral.uikit.compose.text.Text
import com.admiral.uikit.compose.text.TextColor
import com.admiral.uikit.compose.util.DIMEN_X10
import com.admiral.uikit.compose.util.DIMEN_X18
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X7

@Composable
@Suppress("LongParameterList", "LongMethod")
fun EmptyView(
    modifier: Modifier = Modifier,
    titleText: String,
    titleTextColor: TextColor = TextColor(textColorNormal = AdmiralTheme.colors.textPrimary),
    subtitleText: String,
    subtitleTextColor: TextColor = TextColor(textColorNormal = AdmiralTheme.colors.textSecondary),
    buttonText: String,
    buttonColor: ButtonColor = primary(),
    icon: Painter? = null,
    iconContentDescription: String = "",
    iconColor: Color? = null,
    onButtonClick: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon?.let {
            Icon(
                modifier = Modifier.size(DIMEN_X18),
                painter = icon,
                contentDescription = iconContentDescription,
                tint = iconColor
                    ?: LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
            )
            Spacer(modifier = Modifier.padding(DIMEN_X10))
        }
        Text(
            modifier = Modifier.padding(horizontal = DIMEN_X4),
            textAlign = TextAlign.Center,
            text = titleText,
            color = titleTextColor,
            style = AdmiralTheme.typography.title1
        )
        Spacer(modifier = Modifier.padding(DIMEN_X7))
        Text(
            modifier = Modifier.padding(horizontal = DIMEN_X4),
            textAlign = TextAlign.Center,
            text = subtitleText,
            color = subtitleTextColor,
            style = AdmiralTheme.typography.subhead3
        )
        Spacer(modifier = Modifier.padding(DIMEN_X10))
        Button(
            modifier = Modifier
                .padding(horizontal = DIMEN_X4)
                .fillMaxWidth(),
            color = buttonColor,
            actionText = buttonText
        ) {
            onButtonClick.invoke()
        }
    }
}

@Preview
@Composable
private fun EmptyViewPreview() {
    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            EmptyView(
                subtitleText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                buttonText = "Ok",
                titleText = "Title",
                icon = painterResource(id = R.drawable.admiral_ic_check_solid),
                iconColor = AdmiralTheme.colors.elementSuccess
            )
        }
    }
}
