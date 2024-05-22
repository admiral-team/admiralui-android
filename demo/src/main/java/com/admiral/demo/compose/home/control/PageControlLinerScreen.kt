package com.admiral.demo.compose.home.control

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.button.AdmiralButtonSize
import com.admiral.uikit.compose.button.Button
import com.admiral.uikit.compose.control.LinerPageControl
import com.admiral.uikit.compose.tabs.outline.OutlineSliderTabList
import com.admiral.uikit.compose.util.DIMEN_X4
import kotlinx.coroutines.launch
import com.admiral.demo.R as DemoRes

@OptIn(ExperimentalFoundationApi::class)
@Suppress("LongParameterList", "LongMethod", "MagicNumber")
@Composable
fun PageControlLinerScreen(
    onBackClick: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    var pagerCount by remember { mutableIntStateOf(2) }
    val pagerState = rememberPagerState(pageCount = { pagerCount })

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = R.drawable.admiral_ic_chevron_left_outline),
                title = stringResource(id = DemoRes.string.spinner_title),
                onNavIconClick = onBackClick,
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
            OutlineSliderTabList(
                modifier = Modifier
                    .padding(top = DIMEN_X4),
                items = mutableListOf(
                    stringResource(id = com.admiral.demo.R.string.tabs_example_two),
                    stringResource(id = com.admiral.demo.R.string.tabs_example_three),
                    stringResource(id = com.admiral.demo.R.string.tabs_example_four),
                    stringResource(id = com.admiral.demo.R.string.tabs_example_five),
                    stringResource(id = com.admiral.demo.R.string.tabs_example_six),
                    stringResource(id = com.admiral.demo.R.string.tabs_example_seven),
                    stringResource(id = com.admiral.demo.R.string.tabs_example_eight),
                    stringResource(id = com.admiral.demo.R.string.tabs_example_nine),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    pagerCount = it + 2
                }
            )
            LinerPageControl(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = PageControlTopPadding,
                        bottom = PageControlBottomPadding
                    ),
                pageCount = pagerCount,
                pagerState = pagerState
            )
            Button(
                size = AdmiralButtonSize.small(),
                actionText = stringResource(id = DemoRes.string.page_control_next),
                iconEnd = painterResource(id = R.drawable.admiral_ic_arrow_right_outline),
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(pagerState.currentPage + 1)
                    }
                },
            )
        }
    }
}

private val PageControlTopPadding = 136.dp
private val PageControlBottomPadding = 72.dp

@Preview
@Composable
private fun PageControlLinerScreenPreview() {
    AdmiralTheme {
        PageControlLinerScreen()
    }
}