package com.admiral.demo.compose.home.spinner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
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
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.spinner.Spinner
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X25
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.core.components.spinner.SpinnerSize

internal const val SPINNER_SCREEN_ROUTE = "spinnerScreenRoute"

internal fun NavGraphBuilder.spinnerScreen(onBackClick: () -> Unit) {
    composable(route = SPINNER_SCREEN_ROUTE) {
        SpinnerScreen(onBackClick = onBackClick)
    }
}

internal fun NavController.navigateToSpinnerScreen() {
    navigate(SPINNER_SCREEN_ROUTE)
}

@Suppress("LongMethod")
@Composable
fun SpinnerScreen(onBackClick: () -> Unit = {}) {
    var size by remember { mutableStateOf(SpinnerSize.SMALL) }

    Scaffold(
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                title = stringResource(id = R.string.spinner_title),
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = DIMEN_X4)
            ) {
                StandardTab(
                    modifier = Modifier.padding(top = DIMEN_X4),
                    items = mutableListOf(
                        stringResource(id = R.string.spinner_small),
                        stringResource(id = R.string.spinner_medium),
                        stringResource(id = R.string.spinner_big),
                    ),
                    selectedIndex = 0,
                    onCheckedChange = {
                        size = when (it) {
                            0 -> SpinnerSize.SMALL
                            1 -> SpinnerSize.MEDIUM
                            else -> SpinnerSize.BIG
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(top = DIMEN_X25))

                Spinner(size = size)
            }
        }
    }
}

@Preview
@Composable
private fun SpinnerScreenPreview() {
    AdmiralTheme {
        SpinnerScreen()
    }
}