package com.admiral.demo.compose.home.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.tabs.TabItem
import com.admiral.uikit.compose.tabs.outline.OutlineSliderTabList
import com.admiral.uikit.compose.tabs.outline.OutlineSliderTabListBadged
import com.admiral.uikit.compose.util.DIMEN_X10
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5

@Composable
@Suppress("LongMethod")
fun OutlineTabsScreen(
    onBackClick: () -> Unit = {},
) {
    val (isEnabled, setValue) = remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = R.string.tabs_outline_title)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
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
                    setValue(it == 0)
                }
            )
            Spacer(modifier = Modifier.size(DIMEN_X10))
            Text(
                modifier = Modifier.padding(horizontal = DIMEN_X4),
                text = stringResource(id = R.string.tabs_three),
                style = ThemeManagerCompose.typography.body1,
                color = AdmiralTheme.colors.textSecondary
            )
            Spacer(modifier = Modifier.size(DIMEN_X5))
            OutlineSliderTabList(
                modifier = Modifier.padding(horizontal = DIMEN_X4),
                items = mutableListOf(
                    stringResource(id = R.string.tabs_one),
                    stringResource(id = R.string.tabs_two),
                    stringResource(id = R.string.tabs_three),
                ),
                selectedIndex = 0,
                isEnabled = isEnabled
            )
            Spacer(modifier = Modifier.size(DIMEN_X10))
            Text(
                modifier = Modifier.padding(horizontal = DIMEN_X4),
                text = stringResource(id = R.string.tabs_slider_control),
                style = ThemeManagerCompose.typography.body1,
                color = AdmiralTheme.colors.textSecondary
            )
            Spacer(modifier = Modifier.size(DIMEN_X5))
            OutlineSliderTabList(
                modifier = Modifier.padding(horizontal = DIMEN_X4),
                items = mutableListOf(
                    stringResource(id = R.string.tabs_one),
                    stringResource(id = R.string.tabs_two),
                    stringResource(id = R.string.tabs_three),
                    stringResource(id = R.string.tabs_four),
                    stringResource(id = R.string.tabs_five),
                    stringResource(id = R.string.tabs_example_six),
                    stringResource(id = R.string.tabs_example_seven),
                    stringResource(id = R.string.tabs_example_eight),
                    stringResource(id = R.string.tabs_example_nine),
                ),
                selectedIndex = 0,
                isEnabled = isEnabled
            )
            Spacer(modifier = Modifier.size(DIMEN_X10))
            Text(
                modifier = Modifier.padding(horizontal = DIMEN_X4),
                text = stringResource(id = R.string.tabs_notifications),
                style = ThemeManagerCompose.typography.body1,
                color = AdmiralTheme.colors.textSecondary
            )
            Spacer(modifier = Modifier.size(DIMEN_X5))
            OutlineSliderTabListBadged(
                modifier = Modifier.padding(horizontal = DIMEN_X4),
                items = mutableListOf(
                    TabItem(
                        stringResource(id = R.string.tabs_one),
                        isBadgeVisible = true,
                        isBadgeEnabled = isEnabled
                    ),
                    TabItem(
                        stringResource(id = R.string.tabs_two),
                        isBadgeVisible = true,
                        isBadgeEnabled = isEnabled
                    ),
                    TabItem(
                        stringResource(id = R.string.tabs_three),
                        isBadgeVisible = true,
                        isBadgeEnabled = false
                    ),
                    TabItem(
                        stringResource(id = R.string.tabs_four),
                    ),
                ),
                selectedIndex = 0,
                isEnabled = isEnabled
            )
        }
    }
}

@Preview
@Composable
fun OutlineTabsScreenPreview() {
    AdmiralTheme {
        OutlineTabsScreen()
    }
}