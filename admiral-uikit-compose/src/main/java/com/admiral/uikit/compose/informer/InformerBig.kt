package com.admiral.uikit.compose.informer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.links.Link
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.core.components.link.LinkSize

@Composable
fun InformerBig(
    modifier: Modifier = Modifier,
    colors: InformerColor = AdmiralInformerColor.info(),
    headlineText: String? = null,
    headlineMaxLines: Int = Int.MAX_VALUE,
    infoText: String? = null,
    infoMaxLines: Int = Int.MAX_VALUE,
    linkText: String? = null,
    linkMaxLines: Int = Int.MAX_VALUE,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(DIMEN_X3))
            .background(color = colors.background)
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = DIMEN_X4, vertical = DIMEN_X5)
        ) {

            headlineText?.let {
                Text(
                    modifier = Modifier,
                    text = headlineText,
                    maxLines = headlineMaxLines,
                    style = AdmiralTheme.typography.subtitle2,
                    color = colors.getTextColor(isEnabled = isEnabled).value
                )
            }

            infoText?.let {
                Text(
                    modifier = Modifier
                        .padding(vertical = DIMEN_X2),
                    text = infoText,
                    maxLines = infoMaxLines,
                    style = AdmiralTheme.typography.body2,
                    color = colors.getTextColor(isEnabled = isEnabled).value
                )
            }

            linkText?.let {
                Link(
                    modifier = Modifier
                        .padding(vertical = LinkVerticalPadding)
                        .padding(top = DIMEN_X4),
                    isEnabled = isEnabled,
                    linkText = linkText,
                    maxLines = linkMaxLines,
                    textEnableColor = colors.linkEnabled,
                    textDisableColor = colors.linkDisabled,
                    pressedColor = colors.linkPressed,
                    linkSize = LinkSize.BIG
                )
            }
        }
    }
}

private val LinkVerticalPadding = 2.dp

@Preview
@Composable
private fun InformerBigPreview() {
    AdmiralTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = DIMEN_X4, vertical = DIMEN_X5),
                verticalArrangement = Arrangement.spacedBy(DIMEN_X2)
            ) {
                InformerBig(
                    headlineText = "Headline",
                    infoText = "At breakpoint boundaries, mini units divide the screen into a fixed master.",
                    linkText = "Link text"
                )

                InformerBig(
                    isEnabled = false,
                    headlineText = "Headline",
                    infoText = "At breakpoint boundaries, mini units divide the screen into a fixed master.",
                    linkText = "Link text",
                    colors = AdmiralInformerColor.attention()
                )

                InformerBig(
                    headlineText = "Headline",
                    infoText = "At breakpoint boundaries, mini units divide the screen into a fixed master.",
                    linkText = "Link text",
                    colors = AdmiralInformerColor.success()
                )

                InformerBig(
                    isEnabled = false,
                    headlineText = "Headline",
                    infoText = "At breakpoint boundaries, mini units divide the screen into a fixed master.",
                    linkText = "Link text",
                    colors = AdmiralInformerColor.error()
                )
            }
        }
    }
}