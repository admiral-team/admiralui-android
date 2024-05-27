package com.admiral.demo.compose.home.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.input.InputNumber
import com.admiral.uikit.compose.pincode.PinCodeView
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.core.components.input.InputType
import com.admiral.uikit.core.components.pincode.PinCodeState

@Composable
@Suppress("LongMethod", "MagicNumber")
fun PinCodeTextFieldScreen(
    onBackClick: () -> Unit = {}
) {
    var pinCodeState by remember { mutableStateOf(PinCodeState.DEFAULT) }
    var dotsCount by remember { mutableIntStateOf(6) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = R.string.text_fields_pincode_title),
            )
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = DIMEN_X4)
                .verticalScroll(state = rememberScrollState())
        ) {
            StandardTab(
                items = mutableListOf(
                    stringResource(id = R.string.tabs_default),
                    stringResource(id = R.string.tabs_success),
                    stringResource(id = R.string.tabs_error),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    pinCodeState = when (it) {
                        0 -> PinCodeState.DEFAULT
                        1 -> PinCodeState.SUCCESS
                        2 -> PinCodeState.ERROR
                        else -> PinCodeState.DEFAULT
                    }
                }
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            InputNumber(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = DIMEN_X3),
                inputType = InputType.OVAL,
                optionalText = stringResource(id = R.string.text_fields_digits_count),
                value = 6,
                minValue = 2,
                maxValue = 8,
                onValueChange = { _, new ->
                    dotsCount = new
                }
            )
            Spacer(modifier = Modifier.size(PinCodeTopPadding))
            PinCodeView(
                modifier = Modifier
                    .padding(vertical = PinCodeDotsVerticalPadding)
                    .fillMaxWidth(),
                dotsCount = dotsCount,
                state = pinCodeState,
            )
        }
    }
}

private val PinCodeTopPadding = 112.dp
private val PinCodeDotsVerticalPadding = 26.dp

@Preview
@Composable
private fun PinCodeTextFieldScreenPreview() {
    AdmiralTheme {
        PinCodeTextFieldScreen()
    }
}