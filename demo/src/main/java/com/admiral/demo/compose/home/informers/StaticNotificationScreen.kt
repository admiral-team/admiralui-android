package com.admiral.demo.compose.home.informers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.notifications.fixed.AdmiralStaticNotificationColors
import com.admiral.uikit.compose.notifications.fixed.StaticNotification
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.compose.util.DIMEN_X6

@Suppress("LongMethod")
@Composable
fun StaticNotificationScreen(onBackClick: () -> Unit = {}) {
    var isEnabled by remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = R.string.notifications_static_title)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = DIMEN_X4)
                .padding(bottom = DIMEN_X4)
                .verticalScroll(state = rememberScrollState())
        ) {
            StandardTab(
                modifier = Modifier,
                items = mutableListOf(
                    stringResource(id = R.string.tabs_default),
                    stringResource(id = R.string.tabs_disabled),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    isEnabled = it == 0
                }
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = TextVerticalPadding)
                    .padding(top = DIMEN_X6, bottom = DIMEN_X1)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.informers_default),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )

            StaticNotification(
                notificationText = stringResource(id = R.string.informers_info_text),
                linkText = stringResource(id = R.string.informers_example_link),
                icon = painterResource(id = R.drawable.admiral_ic_info_solid),
                isCloseIconVisible = true,
                isEnabled = isEnabled,
                isBackgroundColorDefault = true,
                colors = AdmiralStaticNotificationColors.info()
            )

            StaticNotification(
                modifier = Modifier.padding(top = 8.dp),
                notificationText = stringResource(id = R.string.informers_info_text),
                linkText = stringResource(id = R.string.informers_example_link),
                icon = painterResource(id = R.drawable.admiral_ic_info_solid),
                isCloseIconVisible = true,
                isEnabled = isEnabled,
                colors = AdmiralStaticNotificationColors.info()
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = TextVerticalPadding)
                    .padding(top = DIMEN_X5, bottom = DIMEN_X1)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.informers_success),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )

            StaticNotification(
                notificationText = stringResource(id = R.string.informers_info_text),
                linkText = stringResource(id = R.string.informers_example_link),
                icon = painterResource(id = R.drawable.admiral_ic_info_solid),
                isCloseIconVisible = true,
                isEnabled = isEnabled,
                isBackgroundColorDefault = true,
                colors = AdmiralStaticNotificationColors.success()
            )

            StaticNotification(
                modifier = Modifier.padding(top = 8.dp),
                notificationText = stringResource(id = R.string.informers_info_text),
                linkText = stringResource(id = R.string.informers_example_link),
                icon = painterResource(id = R.drawable.admiral_ic_info_solid),
                isCloseIconVisible = true,
                isEnabled = isEnabled,
                colors = AdmiralStaticNotificationColors.success()
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = TextVerticalPadding)
                    .padding(top = DIMEN_X5, bottom = DIMEN_X1)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.informers_attention),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )

            StaticNotification(
                notificationText = stringResource(id = R.string.informers_info_text),
                linkText = stringResource(id = R.string.informers_example_link),
                icon = painterResource(id = R.drawable.admiral_ic_info_solid),
                isCloseIconVisible = true,
                isEnabled = isEnabled,
                isBackgroundColorDefault = true,
                colors = AdmiralStaticNotificationColors.attention()
            )

            StaticNotification(
                modifier = Modifier.padding(top = 8.dp),
                notificationText = stringResource(id = R.string.informers_info_text),
                linkText = stringResource(id = R.string.informers_example_link),
                icon = painterResource(id = R.drawable.admiral_ic_info_solid),
                isCloseIconVisible = true,
                isEnabled = isEnabled,
                colors = AdmiralStaticNotificationColors.attention()
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = TextVerticalPadding)
                    .padding(top = DIMEN_X5, bottom = DIMEN_X1)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.informers_error),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )

            StaticNotification(
                notificationText = stringResource(id = R.string.informers_info_text),
                linkText = stringResource(id = R.string.informers_example_link),
                icon = painterResource(id = R.drawable.admiral_ic_info_solid),
                isCloseIconVisible = true,
                isEnabled = isEnabled,
                isBackgroundColorDefault = true,
                colors = AdmiralStaticNotificationColors.error()
            )

            StaticNotification(
                modifier = Modifier.padding(top = 8.dp),
                notificationText = stringResource(id = R.string.informers_info_text),
                linkText = stringResource(id = R.string.informers_example_link),
                icon = painterResource(id = R.drawable.admiral_ic_info_solid),
                isCloseIconVisible = true,
                isEnabled = isEnabled,
                colors = AdmiralStaticNotificationColors.error()
            )
        }
    }
}

private val TextVerticalPadding = 18.dp

@Preview
@Composable
private fun BigInformersScreenPreview() {
    AdmiralTheme {
        StaticNotificationScreen()
    }
}