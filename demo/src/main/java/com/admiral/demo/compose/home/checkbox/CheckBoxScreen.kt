package com.admiral.demo.compose.home.checkbox

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.admiral.uikit.compose.checkbox.CheckBox
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.compose.util.DIMEN_X6

internal const val CHECK_BOX_SCREEN_ROUTE = "checkBoxScreenRoute"

internal fun NavGraphBuilder.checkBoxScreen(onBackClick: () -> Unit) {
    composable(route = CHECK_BOX_SCREEN_ROUTE) {
        CheckBoxScreen(onBackClick = onBackClick)
    }
}

internal fun NavController.navigateToCheckBoxScreen() {
    navigate(CHECK_BOX_SCREEN_ROUTE)
}

@Suppress("LongMethod")
@Composable
fun CheckBoxScreen(onBackClick: () -> Unit = {}) {
    var checkedDefault by remember { mutableStateOf(false) }
    var checkedDefaultText by remember { mutableStateOf(false) }
    var checkedSelected by remember { mutableStateOf(true) }
    var checkedSelectedText by remember { mutableStateOf(true) }
    var checkedError by remember { mutableStateOf(false) }
    var checkedErrorText by remember { mutableStateOf(false) }

    var isEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                title = stringResource(id = R.string.check_box_title),
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

                Spacer(modifier = Modifier.padding(top = DIMEN_X6))

                Text(
                    modifier = Modifier.padding(vertical = TextPadding),
                    text = stringResource(id = R.string.check_box_default),
                    color = AdmiralTheme.colors.textSecondary,
                    style = ThemeManagerCompose.typography.body1,
                )

                Spacer(modifier = Modifier.padding(top = DIMEN_X1))

                CheckBox(
                    isChecked = checkedDefault,
                    isEnabled = isEnabled,
                    onCheckedChange = { isChecked -> checkedDefault = isChecked },
                )

                Spacer(modifier = Modifier.padding(top = DIMEN_X5))

                CheckBox(
                    isChecked = checkedDefaultText,
                    isEnabled = isEnabled,
                    onCheckedChange = { isChecked -> checkedDefaultText = isChecked },
                    text = stringResource(id = R.string.check_box_text)
                )

                Spacer(modifier = Modifier.padding(top = DIMEN_X5))

                Text(
                    modifier = Modifier.padding(vertical = TextPadding),
                    text = stringResource(id = R.string.check_box_selected),
                    color = AdmiralTheme.colors.textSecondary,
                    style = ThemeManagerCompose.typography.body1,
                )

                Spacer(modifier = Modifier.padding(top = DIMEN_X1))

                CheckBox(
                    isChecked = checkedSelected,
                    isEnabled = isEnabled,
                    onCheckedChange = { isChecked -> checkedSelected = isChecked },
                )

                Spacer(modifier = Modifier.padding(top = DIMEN_X5))

                CheckBox(
                    isChecked = checkedSelectedText,
                    isEnabled = isEnabled,
                    onCheckedChange = { isChecked -> checkedSelectedText = isChecked },
                    text = stringResource(id = R.string.check_box_text)
                )

                Spacer(modifier = Modifier.padding(top = DIMEN_X5))

                Text(
                    modifier = Modifier.padding(vertical = TextPadding),
                    text = stringResource(id = R.string.check_box_error),
                    color = AdmiralTheme.colors.textSecondary,
                    style = ThemeManagerCompose.typography.body1,
                )

                Spacer(modifier = Modifier.padding(top = DIMEN_X1))

                CheckBox(
                    isChecked = checkedError,
                    isEnabled = isEnabled,
                    isError = true,
                    onCheckedChange = { isChecked -> checkedError = isChecked },
                )

                Spacer(modifier = Modifier.padding(top = DIMEN_X5))

                CheckBox(
                    isChecked = checkedErrorText,
                    isEnabled = isEnabled,
                    isError = true,
                    onCheckedChange = { isChecked -> checkedErrorText = isChecked },
                    text = stringResource(id = R.string.check_box_text)
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
        CheckBoxScreen()
    }
}