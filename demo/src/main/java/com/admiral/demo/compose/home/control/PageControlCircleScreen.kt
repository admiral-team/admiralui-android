package com.admiral.demo.compose.home.control

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.control.CirclePageControl
import com.admiral.uikit.compose.tabs.outline.OutlineSliderTabList
import com.admiral.uikit.compose.util.DIMEN_X25
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.demo.R as DemoRes

@Composable
fun PageControlCircleScreen(
    onBackClick: () -> Unit = {},
) {
    val (pageCount, setPageCount) = remember { mutableStateOf(0) }
    val (currentPage, setCurrentPage) = remember { mutableStateOf(0) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = R.drawable.admiral_ic_chevron_left_outline),
                title = stringResource(id = DemoRes.string.page_control_circle),
                onNavIconClick = onBackClick,
            )
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .verticalScroll(state = rememberScrollState())
        ) {
            OutlineSliderTabList(
                modifier = Modifier
                    .padding(horizontal = DIMEN_X4, vertical = DIMEN_X4)
                    .fillMaxWidth(),
                items = mutableListOf(
                    stringResource(id = com.admiral.demo.R.string.tabs_one),
                    stringResource(id = com.admiral.demo.R.string.tabs_two),
                    stringResource(id = com.admiral.demo.R.string.tabs_three),
                    stringResource(id = com.admiral.demo.R.string.tabs_four),
                    stringResource(id = com.admiral.demo.R.string.tabs_five),
                    stringResource(id = com.admiral.demo.R.string.tabs_example_six),
                    stringResource(id = com.admiral.demo.R.string.tabs_example_seven),
                    stringResource(id = com.admiral.demo.R.string.tabs_example_eight),
                    stringResource(id = com.admiral.demo.R.string.tabs_example_nine),
                ),
                selectedIndex = 0,
                onCheckedChange = { tabIndex ->
                    setPageCount(tabIndex)
                    setCurrentPage(0)
                }
            )

            Spacer(modifier = Modifier.height(DIMEN_X25))

            CirclePageControl(
                pageCount = pageCount + 1,
                currentPage = currentPage,
                onButtonClicked = { currentPage ->
                    if (currentPage < pageCount + 1) {
                        setCurrentPage(currentPage + 1)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun PageControlCircleScreenPreview() {
    AdmiralTheme {
        PageControlCircleScreen()
    }
}