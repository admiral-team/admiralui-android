package com.admiral.demo.compose.home.textfield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.admiral.uikit.compose.tabs.outline.OutlineSliderTab
import com.admiral.uikit.compose.textfield.DoubleTextField
import com.admiral.uikit.compose.textfield.DoubleTextFieldRatio
import com.admiral.uikit.compose.textfield.TextFieldParams
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.compose.util.DIMEN_X6

@Composable
@Suppress("LongMethod")
fun DoubleTextFieldScreen(
    onBackClick: () -> Unit = {}
) {
    val palette = ThemeManagerCompose.theme.value.palette
    val typography = ThemeManagerCompose.typography

    var tabIndex by remember { mutableStateOf(0) }
    val tabList = listOf(
        stringResource(id = R.string.text_fields_default),
        stringResource(id = R.string.text_fields_read_only),
        stringResource(id = R.string.text_fields_error),
        stringResource(id = R.string.text_fields_disabled)
    )
    val isReadOnly = tabIndex == READ_ONLY_STATE
    val isError = tabIndex == ERROR_STATE
    val isDisabled = tabIndex == DISABLED_STATE

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = R.string.text_fields_double_title),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = DIMEN_X4),
        ) {
            LazyRow(
                modifier = Modifier
                    .padding(top = DIMEN_X4),
                horizontalArrangement = Arrangement.spacedBy(DIMEN_X2),
            ) {
                itemsIndexed(tabList) { index, title ->
                    OutlineSliderTab(
                        isSelected = tabIndex == index,
                        tabText = title,
                        onClick = { tabIndex = index },
                        isBadgeVisible = false,
                    )
                }
            }
            Spacer(modifier = Modifier.height(DIMEN_X6))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = VERTICAL_PADDING.dp),
                text = stringResource(id = R.string.text_fields_example_50_50),
                color = Color(palette.textSecondary),
                style = typography.body1,
            )
            Spacer(modifier = Modifier.height(DIMEN_X1))
            DoubleTextField(
                startTextFieldParams = TextFieldParams(
                    optionalText = stringResource(id = R.string.text_fields_optional_label),
                    icon = painterResource(id = R.drawable.admiral_ic_edit_outline),
                    isReadOnly = isReadOnly,
                    isEnabled = isDisabled.not(),
                ),
                endTextFieldParams = TextFieldParams(
                    optionalText = stringResource(id = R.string.text_fields_optional_label),
                    icon = painterResource(id = R.drawable.admiral_ic_edit_outline),
                    isReadOnly = isReadOnly,
                    isEnabled = isDisabled.not(),
                ),
                additionalText = stringResource(id = R.string.text_fields_example_slider_additional),
                isError = isError,
                ratio = DoubleTextFieldRatio.BIG_START
            )
            Spacer(modifier = Modifier.height(DIMEN_X5))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = VERTICAL_PADDING.dp),
                text = stringResource(id = R.string.text_fields_example_70_30),
                color = Color(palette.textSecondary),
                style = typography.body1,
            )
            DoubleTextField(
                startTextFieldParams = TextFieldParams(
                    optionalText = stringResource(id = R.string.text_fields_optional_label),
                    icon = painterResource(id = R.drawable.admiral_ic_edit_outline),
                    isReadOnly = isReadOnly,
                    isEnabled = isDisabled.not(),

                    ),
                endTextFieldParams = TextFieldParams(
                    optionalText = stringResource(id = R.string.text_fields_optional_label),
                    icon = painterResource(id = R.drawable.admiral_ic_edit_outline),
                    isReadOnly = isReadOnly,
                    isEnabled = isDisabled.not(),
                ),
                additionalText = stringResource(id = R.string.text_fields_example_slider_additional),
                isError = isError,
                ratio = DoubleTextFieldRatio.BIG_START
            )
        }
    }
}

private const val VERTICAL_PADDING = 18
private const val READ_ONLY_STATE = 1
private const val ERROR_STATE = 2
private const val DISABLED_STATE = 3

@Preview
@Composable
private fun DoubleTextFieldScreenPreview() {
    DoubleTextFieldScreen()
}