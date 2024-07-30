package com.admiral.demo.compose.home.radiobutton

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
import com.admiral.uikit.compose.radiobutton.RadioButton
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.compose.util.DIMEN_X6

internal const val RADIO_BUTTON_SCREEN_ROUTE = "radioButtonScreenRoute"

internal fun NavGraphBuilder.radioButtonScreen(onBackClick: () -> Unit) {
    composable(route = RADIO_BUTTON_SCREEN_ROUTE) {
        RadioButtonScreen(onBackClick = onBackClick)
    }
}

internal fun NavController.navigateToRadioButtonScreen() {
    navigate(RADIO_BUTTON_SCREEN_ROUTE)
}

@Suppress("LongMethod")
@Composable
fun RadioButtonScreen(onBackClick: () -> Unit = {}) {
    var selectedDefault by remember { mutableStateOf(false) }
    var selectedDefaultText by remember { mutableStateOf(false) }
    var selectedSelected by remember { mutableStateOf(true) }
    var selectedSelectedText by remember { mutableStateOf(true) }
    var selectedError by remember { mutableStateOf(false) }
    var selectedErrorText by remember { mutableStateOf(false) }

    var isEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                title = stringResource(id = R.string.radio_buttons_title),
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
                    text = stringResource(id = R.string.check_box_default),
                    color = AdmiralTheme.colors.textSecondary,
                    style = ThemeManagerCompose.typography.body1,
                )

                RadioButton(
                    modifier = Modifier
                        .padding(bottom = DIMEN_X5),
                    isSelected = selectedDefault,
                    isEnabled = isEnabled,
                    onClick = {
                        selectedDefault = selectedDefault.not()
                    }
                )

                RadioButton(
                    modifier = Modifier
                        .padding(bottom = DIMEN_X5),
                    isSelected = selectedDefaultText,
                    text = stringResource(id = R.string.radio_buttons_text),
                    isEnabled = isEnabled,
                    onClick = {
                        selectedDefaultText = selectedDefaultText.not()
                    }
                )

                Text(
                    modifier = Modifier
                        .padding(vertical = TextPadding)
                        .padding(bottom = DIMEN_X1),
                    text = stringResource(id = R.string.check_box_selected),
                    color = AdmiralTheme.colors.textSecondary,
                    style = ThemeManagerCompose.typography.body1,
                )

                RadioButton(
                    modifier = Modifier
                        .padding(bottom = DIMEN_X5),
                    isSelected = selectedSelected,
                    isEnabled = isEnabled,
                    onClick = {
                        selectedSelected = selectedSelected.not()
                    }
                )

                RadioButton(
                    modifier = Modifier
                        .padding(bottom = DIMEN_X5),
                    isSelected = selectedSelectedText,
                    text = stringResource(id = R.string.radio_buttons_text),
                    isEnabled = isEnabled,
                    onClick = {
                        selectedSelectedText = selectedSelectedText.not()
                    }
                )

                Text(
                    modifier = Modifier
                        .padding(vertical = TextPadding)
                        .padding(bottom = DIMEN_X1),
                    text = stringResource(id = R.string.check_box_error),
                    color = AdmiralTheme.colors.textSecondary,
                    style = ThemeManagerCompose.typography.body1,
                )

                RadioButton(
                    modifier = Modifier
                        .padding(bottom = DIMEN_X5),
                    isSelected = selectedError,
                    isEnabled = isEnabled,
                    isError = true,
                    onClick = {
                        selectedError = selectedError.not()
                    }
                )

                RadioButton(
                    modifier = Modifier
                        .padding(bottom = DIMEN_X5),
                    isSelected = selectedErrorText,
                    text = stringResource(id = R.string.radio_buttons_text),
                    isEnabled = isEnabled,
                    isError = true,
                    onClick = {
                        selectedErrorText = selectedErrorText.not()
                    }
                )
            }
        }
    }
}

private val TextPadding = 18.dp

@Preview
@Composable
private fun RadioButtonScreenPreview() {
    AdmiralTheme {
        RadioButtonScreen()
    }
}