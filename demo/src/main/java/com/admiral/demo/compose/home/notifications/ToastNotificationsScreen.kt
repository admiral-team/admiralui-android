package com.admiral.demo.compose.home.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.AdmiralTheme.typography
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.button.AdmiralButtonColor
import com.admiral.uikit.compose.button.AdmiralButtonSize
import com.admiral.uikit.compose.button.Button
import com.admiral.uikit.compose.notifications.toast.AdmiralToastNotificationColor
import com.admiral.uikit.compose.notifications.toast.ToastNotification
import com.admiral.uikit.compose.text.Text
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.compose.util.DIMEN_X9
import kotlinx.coroutines.launch
import com.admiral.demo.R as demoR

@Suppress("LongMethod")
@Composable
fun ToastNotificationsScreen(onBackClick: () -> Unit = {}) {
    var isVisible by remember { mutableStateOf(false) }
    val snackState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val messageText = "At breakpoint boundaries, mini units divide the screen into"
    val linkText = "Link text"

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = demoR.string.notifications_toast_title)
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackState,
                snackbar = { snackbarData: SnackbarData ->
                    ToastNotification(
                        modifier = Modifier
                            .padding(horizontal = DIMEN_X4)
                            .padding(bottom = DIMEN_X6),
                        text = snackbarData.message,
                        duration = DURATION,
                        isCloseIconVisible = true,
                        linkText = snackbarData.actionLabel,
                        icon = painterResource(id = demoR.drawable.admiral_ic_check_solid),
                        colors = AdmiralToastNotificationColor.default(
                            iconTint = AdmiralTheme.colors.elementSuccess,
                        ),
                        onTimerFinish = {
                            isVisible = isVisible.not()
                        },
                        isVisible = isVisible
                    )
                }
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

            Text(
                modifier = Modifier
                    .padding(vertical = DIMEN_X3),
                text = stringResource(id = demoR.string.notifications_toast_text),
                style = typography.body2,
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = DIMEN_X9),
                color = AdmiralButtonColor.ghost(),
                size = AdmiralButtonSize.big(),
                actionText = stringResource(id = demoR.string.notifications_action_show_toast),
                onClick = {
                    isVisible = isVisible.not()
                    scope.launch {
                        snackState.showSnackbar(
                            duration = SnackbarDuration.Long,
                            message = messageText,
                            actionLabel = linkText,
                        )
                    }
                }
            )
        }
    }
}

private const val DURATION = 7000L

@Preview
@Composable
private fun ToastNotificationsScreenPreview() {
    AdmiralTheme {
        ToastNotificationsScreen()
    }
}