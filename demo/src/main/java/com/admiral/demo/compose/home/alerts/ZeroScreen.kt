package com.admiral.demo.compose.home.alerts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.appbar.AppBar
import com.admiral.uikit.compose.empty.EmptyView

@Composable
fun ZeroScreen(
    onBackClick: () -> Unit = {},
) {
    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            EmptyView(
                modifier = Modifier.padding(padding),
                titleText = stringResource(id = R.string.pop_up_example_title),
                buttonText = stringResource(id = R.string.pop_up_example_button),
                subtitleText = stringResource(id = R.string.lorem_ipsum),
                icon = painterResource(id = R.drawable.admiral_ic_check_solid),
                iconColor = AdmiralTheme.colors.elementSuccess
            )
        }
    }
}

@Preview
@Composable
private fun ZeroScreenPreview() {
    AdmiralTheme {
        ZeroScreen()
    }
}