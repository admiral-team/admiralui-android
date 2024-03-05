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
import com.admiral.uikit.compose.tabs.outline.OutlineSliderTab
import com.admiral.uikit.compose.textfield.TextField
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.compose.util.DIMEN_X6

@Composable
@Suppress("LongMethod", "LongParameterList")
fun StandardTextFieldScreen() {
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
    val isEnabled = tabIndex == DISABLED_STATE

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = DIMEN_X4, vertical = DIMEN_X4),
        ) {
            LazyRow(
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
                text = stringResource(id = R.string.text_fields_basic),
                color = Color(palette.textSecondary),
                style = typography.body1,
            )
            Spacer(modifier = Modifier.height(DIMEN_X1))
            TextField(
                optionalText = stringResource(id = R.string.text_fields_optional_label),
                additionalText = stringResource(id = R.string.text_fields_example_slider_additional),
                isError = isError,
                isReadOnly =  isReadOnly,
                isEnabled = isEnabled.not(),
            )
            Spacer(modifier = Modifier.height(DIMEN_X5))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = VERTICAL_PADDING.dp),
                text = stringResource(id = R.string.text_fields_masked),
                color = Color(palette.textSecondary),
                style = typography.body1,
            )
            TextField(
                optionalText = stringResource(id = R.string.text_fields_optional_label),
                additionalText = stringResource(id = R.string.text_fields_example_slider_additional),
                icon = painterResource(id = R.drawable.admiral_ic_eye_close_outline),
                isError = isError,
                isReadOnly =  isReadOnly,
                isEnabled = isEnabled.not(),
                onIconClick = {}
            )
            Spacer(modifier = Modifier.height(DIMEN_X5))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = VERTICAL_PADDING.dp),
                text = stringResource(id = R.string.text_fields_multiline),
                color = Color(palette.textSecondary),
                style = typography.body1,
            )
            TextField(
                optionalText = stringResource(id = R.string.text_fields_optional_label),
                additionalText = stringResource(id = R.string.text_fields_example_slider_additional),
                icon = painterResource(id = R.drawable.admiral_ic_eye_close_outline),
                maxLines = Int.MAX_VALUE,
                isError = isError,
                isReadOnly =  isReadOnly,
                isEnabled = isEnabled.not(),
                onIconClick = {}
            )
        }
    }
}

private const val VERTICAL_PADDING = 18
const val READ_ONLY_STATE = 1
const val ERROR_STATE = 2
const val DISABLED_STATE = 3

@Preview
@Composable
private fun StandardTextFieldScreenScreenPreview() {
    StandardTextFieldScreen()
}