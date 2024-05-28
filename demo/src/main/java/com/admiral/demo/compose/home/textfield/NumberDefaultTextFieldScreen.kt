package com.admiral.demo.compose.home.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.input.AdmiralInputNumberColors
import com.admiral.uikit.compose.input.InputNumber
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X13
import com.admiral.uikit.compose.util.DIMEN_X20
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.compose.util.DIMEN_X9
import com.admiral.uikit.core.components.input.InputType

@Composable
@Suppress("LongMethod")
fun NumberDefaultTextFieldsScreen(onBackClick: () -> Unit = {}) {
    val (isEnabled, setValue) = remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = R.string.text_fields_default_title),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(horizontal = DIMEN_X4)
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            StandardTab(
                modifier = Modifier,
                items = mutableListOf(
                    stringResource(id = R.string.tabs_default),
                    stringResource(id = R.string.tabs_disabled),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    setValue(it == 0)
                }
            )

            Spacer(modifier = Modifier.size(DIMEN_X13))
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.text_fields_five_symbols),
                style = ThemeManagerCompose.typography.body1,
                color = AdmiralTheme.colors.textSecondary
            )
            Spacer(modifier = Modifier.size(DIMEN_X5))
            InputNumber(
                optionalText = stringResource(id = R.string.text_fields_optional_label),
                isEnabled = isEnabled,
                modifier = Modifier.fillMaxWidth(),
                inputType = InputType.OVAL,
                colors = AdmiralInputNumberColors.oval(),
                maxValue = 99_999,
                value = 1,
            )

            Spacer(modifier = Modifier.size(DIMEN_X9))
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.text_fields_six_symbols),
                style = ThemeManagerCompose.typography.body1,
                color = AdmiralTheme.colors.textSecondary
            )
            Spacer(modifier = Modifier.size(DIMEN_X5))
            InputNumber(
                optionalText = stringResource(id = R.string.text_fields_optional_label),
                isEnabled = isEnabled,
                modifier = Modifier.fillMaxWidth(),
                inputType = InputType.OVAL,
                colors = AdmiralInputNumberColors.oval(),
                value = 100_000,
                maxValue = 1_000_000_000,
            )

            Spacer(modifier = Modifier.size(DIMEN_X9))
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.text_fields_eight_symbols),
                style = ThemeManagerCompose.typography.body1,
                color = AdmiralTheme.colors.textSecondary
            )
            Spacer(modifier = Modifier.size(DIMEN_X5))
            InputNumber(
                optionalText = stringResource(id = R.string.text_fields_optional_label),
                isEnabled = isEnabled,
                modifier = Modifier.fillMaxWidth(),
                inputType = InputType.OVAL,
                colors = AdmiralInputNumberColors.oval(),
                value = 10_000_000,
                maxValue = 1_000_000_000,
            )

            Spacer(modifier = Modifier.size(DIMEN_X9))
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.text_fields_ten_symbols),
                style = ThemeManagerCompose.typography.body1,
                color = AdmiralTheme.colors.textSecondary
            )
            Spacer(modifier = Modifier.size(DIMEN_X5))
            InputNumber(
                optionalText = stringResource(id = R.string.text_fields_optional_label),
                isEnabled = isEnabled,
                modifier = Modifier.fillMaxWidth(),
                inputType = InputType.OVAL,
                colors = AdmiralInputNumberColors.oval(),
                value = 100_000_000,
                maxValue = 1_000_000_000,
            )

            Spacer(modifier = Modifier.size(DIMEN_X9))
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.text_fields_unlimeted_symbols),
                style = ThemeManagerCompose.typography.body1,
                color = AdmiralTheme.colors.textSecondary
            )
            Spacer(modifier = Modifier.size(DIMEN_X5))
            InputNumber(
                optionalText = stringResource(id = R.string.text_fields_optional_label),
                isEnabled = isEnabled,
                modifier = Modifier.fillMaxWidth(),
                inputType = InputType.OVAL,
                colors = AdmiralInputNumberColors.oval(),
                value = 1,
            )
            Spacer(modifier = Modifier.size(DIMEN_X20))
        }
    }
}

@Preview
@Composable
private fun NumberDefaultTextFieldsScreenPreview() {
    AdmiralTheme {
        NumberDefaultTextFieldsScreen()
    }
}
