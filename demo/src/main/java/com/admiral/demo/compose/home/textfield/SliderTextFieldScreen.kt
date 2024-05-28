package com.admiral.demo.compose.home.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.demo.R
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.slider.Slider
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.compose.util.DIMEN_X6

@Composable
@Suppress("LongMethod")
fun SliderTextFieldScreen(
    onBackClick: () -> Unit = {}
) {
    val palette = ThemeManagerCompose.theme.value.palette
    val typography = ThemeManagerCompose.typography

    var tabIndex by remember { mutableStateOf(0) }
    val isError = tabIndex == ERROR_STATE
    val isDisabled = tabIndex == DISABLED_STATE

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = R.string.text_fields_slider_title),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = DIMEN_X4),
        ) {
            StandardTab(
                items = mutableListOf(
                    stringResource(id = R.string.tabs_default),
                    stringResource(id = R.string.text_fields_error),
                    stringResource(id = R.string.tabs_disabled),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    tabIndex = it
                }
            )
            Spacer(modifier = Modifier.height(DIMEN_X6))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = VERTICAL_PADDING.dp),
                text = stringResource(id = R.string.text_fields_standard_title),
                color = Color(palette.textSecondary),
                style = typography.body1,
            )
            Spacer(modifier = Modifier.height(DIMEN_X1))
            Slider(
                value = 500f,
                valueRange = 0f..VALUE_RANGE_MAX,
                additionalText = stringResource(id = R.string.text_fields_example_slider_additional),
                optionalText = stringResource(id = R.string.text_fields_optional_label),
                isError = isError,
                isEnabled = isDisabled.not()
            )
            Spacer(modifier = Modifier.height(DIMEN_X5))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = VERTICAL_PADDING.dp),
                text = stringResource(id = R.string.text_fields_double_title),
                color = Color(palette.textSecondary),
                style = typography.body1,
            )
            Slider(
                value = 500f,
                valueRange = 0f..VALUE_RANGE_MAX,
                additionalText = stringResource(id = R.string.text_fields_example_slider_additional),
                optionalText = stringResource(id = R.string.text_fields_optional_label),
                isError = isError,
                isEnabled = isDisabled.not()
            )
        }
    }
}

private const val VERTICAL_PADDING = 18
private const val ERROR_STATE = 1
private const val DISABLED_STATE = 2
private const val VALUE_RANGE_MAX = 10_000f
@Preview
@Composable
private fun SliderTextFieldScreenPreview() {
    SliderTextFieldScreen()
}