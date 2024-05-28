package com.admiral.demo.compose.home.button


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.button.ButtonGooglePlay
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X11
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6

@Composable
@Suppress("LongMethod")
fun OtherButtonsScreen(onBackClicked: () -> Unit = {}) {
    var isEnable by remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                title = stringResource(id = R.string.buttons_other_title),
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClicked,
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(state = rememberScrollState())
        ) {
            StandardTab(
                modifier = Modifier.padding(horizontal = DIMEN_X4),
                items = mutableListOf(
                    stringResource(id = R.string.tabs_default),
                    stringResource(id = R.string.tabs_disabled),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    isEnable = it == 0
                }
            )

            ButtonGooglePlay(
                modifier = Modifier
                    .padding(top = DIMEN_X11, bottom = DIMEN_X6),
                startText = stringResource(id = R.string.google_play_button_add_into),
                endText = stringResource(id = R.string.google_play_button_pay),
                isEnabled = isEnable,
                iconMiddle = painterResource(id = R.drawable.admiral_ic_google_pay),
            )

            ButtonGooglePlay(
                modifier = Modifier,
                endText = stringResource(id = R.string.google_play_button_add_into_apple_wallet),
                isEnabled = isEnable,
                iconMiddle = painterResource(id = R.drawable.admiral_ic_google_pay),
            )
        }
    }
}

@Preview
@Composable
fun OtherButtonsScreenPreview() {
    AdmiralTheme {
        OtherButtonsScreen()
    }
}
