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
import androidx.compose.ui.unit.dp
import com.admiral.demo.R
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.tabs.outline.OutlineSliderTab
import com.admiral.uikit.compose.textfield.SmsTextField
import com.admiral.uikit.compose.util.DIMEN_X11
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4

@Composable
@Suppress("LongMethod")
fun SmsTextFieldScreen(
    onBackClick: () -> Unit = {}
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabList = listOf(
        stringResource(id = R.string.text_fields_default),
        stringResource(id = R.string.text_fields_error),
        stringResource(id = R.string.text_fields_disabled)
    )
    val isError = tabIndex == ERROR_STATE
    val isDisabled = tabIndex == DISABLED_STATE

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = R.string.text_fields_sms_title),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            LazyRow(
                modifier = Modifier
                    .padding(top = DIMEN_X4, start = DIMEN_X4, end = DIMEN_X4),
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
            SmsTextField(
                isError = isError,
                isEnabled = isDisabled.not(),
                modifier = Modifier.padding(horizontal = 88.dp),
                additionalText = stringResource(id = R.string.text_fields_example_slider_additional),
            )
        }
    }
}

private val VERTICAL_PADDING = DIMEN_X11 * 2
private const val ERROR_STATE = 1
private const val DISABLED_STATE = 2

@Preview
@Composable
private fun SmsTextFieldScreenPreview() {
    SmsTextFieldScreen()
}