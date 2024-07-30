package com.admiral.demo.compose.home.switcher

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.admiral.uikit.compose.switcher.Switcher
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.compose.util.DIMEN_X6

internal const val SWITCHER_SCREEN_ROUTE = "swithcerScreenRoute"

internal fun NavGraphBuilder.switcherScreen(onBackClick: () -> Unit) {
    composable(route = SWITCHER_SCREEN_ROUTE) {
        SwithcerScreen(onBackClick = onBackClick)
    }
}

internal fun NavController.navigateToSwitcherScreen() {
    navigate(SWITCHER_SCREEN_ROUTE)
}

@Suppress("LongMethod")
@Composable
fun SwithcerScreen(
    onBackClick: () -> Unit = {}
) {
    var switcherFirst by remember { mutableStateOf(true) }
    var switcherSecond by remember { mutableStateOf(false) }

    var isEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                title = stringResource(id = R.string.switcher_title),
                navIcon = painterResource(id = R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick
            )
        },
    ) { paddingValues ->
        Surface(
            color = AdmiralTheme.colors.backgroundBasic,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = DIMEN_X4)
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

                Text(
                    modifier = Modifier
                        .padding(top = DIMEN_X6, bottom = DIMEN_X1)
                        .padding(vertical = TextPadding),
                    text = stringResource(id = R.string.switcher_on),
                    color = AdmiralTheme.colors.textSecondary,
                    style = ThemeManagerCompose.typography.body1,
                )

                Switcher(
                    modifier = Modifier.padding(bottom = DIMEN_X5),
                    isChecked = switcherFirst,
                    isEnabled = isEnabled,
                    onCheckedChange = { isChecked -> switcherFirst = isChecked },
                )

                Text(
                    modifier = Modifier
                        .padding(top = DIMEN_X5, bottom = DIMEN_X1)
                        .padding(vertical = TextPadding),
                    text = stringResource(id = R.string.switcher_off),
                    color = AdmiralTheme.colors.textSecondary,
                    style = ThemeManagerCompose.typography.body1,
                )

                Switcher(
                    isChecked = switcherSecond,
                    isEnabled = isEnabled,
                    onCheckedChange = { isChecked -> switcherSecond = isChecked },
                )
            }
        }
    }
}

private val TextPadding = 18.dp

@Preview
@Composable
private fun SwithcerScreenPreview() {
    AdmiralTheme {
        SwithcerScreen()
    }
}