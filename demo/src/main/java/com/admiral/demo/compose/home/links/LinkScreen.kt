package com.admiral.demo.compose.home.links

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.links.Link
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.compose.util.DIMEN_X9
import com.admiral.uikit.core.components.link.LinkSize

internal const val LINK_SCREEN_ROUTE = "linkScreenRoute"

internal fun NavGraphBuilder.linkScreen(onBackClick: () -> Unit) {
    composable(route = LINK_SCREEN_ROUTE) {
        LinkScreen(onBackClick = onBackClick)
    }
}

internal fun NavController.navigateToLinkScreen() {
    navigate(LINK_SCREEN_ROUTE)
}

@Suppress("LongMethod")
@Composable
fun LinkScreen(onBackClick: () -> Unit = {}) {
    var isEnabled by remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                title = stringResource(id = R.string.links_title),
                navIcon = painterResource(id = R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(horizontal = DIMEN_X4)
                .padding(paddingValues)
        ) {
            StandardTab(
                modifier = Modifier.padding(top = DIMEN_X4),
                items = mutableListOf(
                    stringResource(id = R.string.tabs_default),
                    stringResource(id = R.string.tabs_disabled),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    isEnabled = it == 0
                }
            )

            Spacer(modifier = Modifier.padding(top = DIMEN_X6))

            Text(
                modifier = Modifier.padding(vertical = TextPadding),
                text = stringResource(id = R.string.links_size_big),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )

            Spacer(modifier = Modifier.padding(top = DIMEN_X1))

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Link(
                    linkText = stringResource(id = R.string.links_size_big),
                    iconStart = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_arrow_left_outline),
                    isEnable = isEnabled,
                    linkSize = LinkSize.BIG
                )

                Spacer(modifier = Modifier.padding(start = DIMEN_X9))

                Link(
                    linkText = stringResource(id = R.string.links_size_big),
                    iconEnd = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_arrow_right_outline),
                    isEnable = isEnabled,
                    linkSize = LinkSize.BIG
                )

                Spacer(modifier = Modifier.padding(start = DIMEN_X9))

                Link(
                    linkText = stringResource(id = R.string.links_size_big),
                    isEnable = isEnabled,
                    linkSize = LinkSize.BIG
                )
            }

            Spacer(modifier = Modifier.padding(top = DIMEN_X5))

            Text(
                modifier = Modifier.padding(vertical = TextPadding),
                text = stringResource(id = R.string.links_size_small),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )

            Spacer(modifier = Modifier.padding(top = DIMEN_X1))

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Link(
                    linkText = stringResource(id = R.string.links_size_big),
                    iconStart = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_arrow_left_outline),
                    isEnable = isEnabled,
                )

                Spacer(modifier = Modifier.padding(start = DIMEN_X9))

                Link(
                    linkText = stringResource(id = R.string.links_size_big),
                    iconEnd = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_arrow_right_outline),
                    isEnable = isEnabled,
                )

                Spacer(modifier = Modifier.padding(start = DIMEN_X9))

                Link(
                    linkText = stringResource(id = R.string.links_size_big),
                    isEnable = isEnabled,
                )
            }
        }
    }
}

private val TextPadding = 18.dp

@Preview
@Composable
private fun CheckBoxScreenPreview() {
    AdmiralTheme {
        LinkScreen()
    }
}