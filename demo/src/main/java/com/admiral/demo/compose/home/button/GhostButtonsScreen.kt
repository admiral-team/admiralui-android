package com.admiral.demo.compose.home.button

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.button.AdmiralButtonColor
import com.admiral.uikit.compose.button.AdmiralButtonSize
import com.admiral.uikit.compose.button.Button
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X11
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6

@Composable
@Suppress("LongMethod")
fun GhostButtonsScreen(
    onBackClicked: () -> Unit = {}
) {
    var isLoading by remember { mutableStateOf(false) }
    var isEnable by remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                title = stringResource(id = com.admiral.demo.R.string.buttons_ghost_title),
                navIcon = painterResource(id = R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClicked,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(state = rememberScrollState())
                .padding(horizontal = DIMEN_X4),
        ) {
            StandardTab(
                modifier = Modifier.padding(top = DIMEN_X4),
                items = mutableListOf(
                    stringResource(id = com.admiral.demo.R.string.tabs_default),
                    stringResource(id = com.admiral.demo.R.string.tabs_disabled),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    isEnable = it == 0
                }
            )
            Spacer(modifier = Modifier.size(DIMEN_X11))
            Button(
                actionText = "Готово",
                additionalText = "Выбрать все карты",
                modifier = Modifier
                    .fillMaxWidth(),
                color = AdmiralButtonColor.ghost(),
                isEnabled = isEnable,
                isLoading = isLoading,
                onClick = {
                    isLoading = isLoading.not()
                })
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                actionText = "Big Button",
                modifier = Modifier
                    .fillMaxWidth(),
                isEnabled = isEnable,
                color = AdmiralButtonColor.ghost(),
                iconStart = painterResource(id = R.drawable.admiral_ic_heart_outline)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                actionText = "Big Button",
                modifier = Modifier
                    .fillMaxWidth(),
                isEnabled = isEnable,
                color = AdmiralButtonColor.ghost(),
                iconEnd = painterResource(id = R.drawable.admiral_ic_maintenance_solid)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                actionText = "Big Button",
                modifier = Modifier
                    .fillMaxWidth(),
                isEnabled = isEnable,
                color = AdmiralButtonColor.ghost(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.medium(),
                actionText = "Medium Button",
                isEnabled = isEnable,
                color = AdmiralButtonColor.ghost(),
                iconStart = painterResource(id = R.drawable.admiral_ic_heart_outline)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.medium(),
                actionText = "Medium Button",
                isEnabled = isEnable,
                color = AdmiralButtonColor.ghost(),
                iconEnd = painterResource(id = R.drawable.admiral_ic_maintenance_solid)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.medium(),
                actionText = "Medium Button",
                isEnabled = isEnable,
                color = AdmiralButtonColor.ghost(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.small(),
                actionText = "Small Button",
                isEnabled = isEnable,
                color = AdmiralButtonColor.ghost(),
                iconStart = painterResource(id = R.drawable.admiral_ic_heart_outline)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.small(),
                actionText = "Small Button",
                isEnabled = isEnable,
                color = AdmiralButtonColor.ghost(),
                iconEnd = painterResource(id = R.drawable.admiral_ic_maintenance_solid)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.small(),
                actionText = "Small Button",
                isEnabled = isEnable,
                color = AdmiralButtonColor.ghost(),
            )
        }
    }
}

@Preview
@Composable
fun GhostButtonsScreenPreview() {
    AdmiralTheme {
        GhostButtonsScreen()
    }
}