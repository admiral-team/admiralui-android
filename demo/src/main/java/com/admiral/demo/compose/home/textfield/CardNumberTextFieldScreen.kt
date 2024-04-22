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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.demo.R
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.tabs.outline.OutlineSliderTab
import com.admiral.uikit.compose.textfield.CardNumberTextField
import com.admiral.uikit.compose.util.DIMEN_X16
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X9
import com.admiral.uikit.core.foundation.ColorState

@Composable
@Suppress("LongMethod")
fun CardNumberTextFieldScreen() {
    val palette = ThemeManagerCompose.theme.value.palette

    var tabIndex by remember { mutableStateOf(0) }
    val tabList = listOf(
        stringResource(id = R.string.text_fields_default),
        stringResource(id = R.string.text_fields_error),
        stringResource(id = R.string.text_fields_disabled)
    )
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
            Spacer(modifier = Modifier.height(VERTICAL_PADDING))
            CardNumberTextField(
                modifier = Modifier.padding(horizontal = DIMEN_X9),
                placeholderText = stringResource(id = R.string.text_fields_bank_card_placeholder),
                additionalText = stringResource(id = R.string.text_fields_example_slider_additional),
                icon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_camera_outline),
                iconColorState = ColorState(
                    normalEnabled = palette.elementAccent
                ),
                isEnabled = isEnabled.not(),
                isError = isError,
            )
        }
    }
}

private val VERTICAL_PADDING = DIMEN_X16 * 2

@Preview
@Composable
private fun CardNumberTextFieldScreenPreview() {
    CardNumberTextFieldScreen()
}