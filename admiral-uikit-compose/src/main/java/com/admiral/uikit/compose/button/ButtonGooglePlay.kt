package com.admiral.uikit.compose.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4

@Composable
@Suppress("LongParameterList")
fun ButtonGooglePlay(
    modifier: Modifier = Modifier,
    startText: String? = null,
    endText: String? = null,
    iconMiddle: Painter? = null,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
    backgroundColor: Color = AdmiralTheme.colors.textPrimary,
    textColor: Color = AdmiralTheme.colors.backgroundBasic,
) {
    val shape = RoundedCornerShape(DIMEN_X2)
    val paddingHorizontal = if(startText.isNullOrEmpty() || endText.isNullOrEmpty()) DIMEN_X4 else DIMEN_X3

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = DIMEN_X4)
            .background(
                color = if (isEnabled) backgroundColor else backgroundColor.withAlpha(),
                shape = shape
            )
            .clip(shape)
            .clickable(
                enabled = isEnabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    color = AdmiralTheme.colors.backgroundBasic.withAlpha(RippleAlpha)
                ),
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        startText?.let {
            Text(
                modifier = Modifier.padding(vertical = DIMEN_X3),
                text = startText,
                style = AdmiralTheme.typography.body1,
                color = if (isEnabled) textColor else textColor.withAlpha()
            )
        }

        iconMiddle?.let {
            Image(
                modifier = Modifier.padding(horizontal = paddingHorizontal, vertical = DIMEN_X3),
                painter = iconMiddle,
                alpha = if (isEnabled) AlphaEnable else AlphaDisable,
                contentDescription = null
            )
        }

        endText?.let {
            Text(
                modifier = Modifier.padding(vertical = DIMEN_X3),
                text = endText,
                style = AdmiralTheme.typography.body1,
                color = if (isEnabled) textColor else textColor.withAlpha()
            )
        }
    }
}

private const val RippleAlpha = 0.1f
private const val AlphaEnable = 1f
private const val AlphaDisable = 0.6f

@Preview(showBackground = false)
@Composable
fun ButtonGooglePlayPreview() {
    AdmiralTheme(true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AdmiralTheme.colors.backgroundBasic)
        ) {
            ButtonGooglePlay(
                startText = "Добавить в",
                iconMiddle = painterResource(id = R.drawable.admiral_ic_google_pay),
                endText = "Pay",
            )

            Spacer(modifier = Modifier.size(DIMEN_X2))

            ButtonGooglePlay(
                startText = "Добавить в",
                isEnabled = false,
                iconMiddle = painterResource(id = R.drawable.admiral_ic_google_pay),
                endText = "Pay",
            )
        }
    }
}