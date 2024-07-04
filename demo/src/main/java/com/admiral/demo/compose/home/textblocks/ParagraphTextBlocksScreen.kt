package com.admiral.demo.compose.home.textblocks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.demo.R as DemoRes
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X4

@Composable
fun ParagraphTextBlocksScreen(
    onBackClick: () -> Unit = {}
) {
    var isEnabled by remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = R.drawable.admiral_ic_chevron_left_outline),
                title = stringResource(id = DemoRes.string.text_blocks_paragraph_title),
                onNavIconClick = onBackClick,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(horizontal = DIMEN_X4)
        ) {
            StandardTab(
                modifier = Modifier
                    .padding(padding)
                    .padding(top = DIMEN_X4),
                items = mutableListOf(
                    stringResource(id = DemoRes.string.tabs_default),
                    stringResource(id = DemoRes.string.tabs_disabled),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    isEnabled = it == 0
                }
            )
        }
    }
}

@Preview
@Composable
private fun ParagraphTextBlocksScreenPreview() {
    AdmiralTheme {
        ParagraphTextBlocksScreen()
    }
}
