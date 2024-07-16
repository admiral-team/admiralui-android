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
import androidx.compose.ui.platform.LocalContext
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
import com.admiral.uikit.compose.notifications.action.ActionNotification
import com.admiral.uikit.compose.text.Text
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.compose.util.DIMEN_X9
import kotlinx.coroutines.launch
import com.admiral.demo.R as demoR

@Suppress("LongMethod")
@Composable
fun ActionNotificationsScreen(onBackClick: () -> Unit = {}) {
    var isVisible by remember { mutableStateOf(false) }
    val snackState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = demoR.string.notifications_action_title)
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackState,
                snackbar = { snackbarData: SnackbarData ->
                    ActionNotification(
                        modifier = Modifier
                            .padding(horizontal = DIMEN_X4)
                            .padding(bottom = DIMEN_X6),
                        text = snackbarData.message,
                        duration = DURATION,
                        closeButtonText = snackbarData.actionLabel,
                        isVisible = isVisible,
                        isCloseButtonVisible = true,
                        isTimerStart = isVisible,
                        isTimerVisible = true,
                        onCloseButtonClickListener = {
                            isVisible = isVisible.not()
                        },
                        onTimerFinish = {
                            isVisible = isVisible.not()
                        }
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
                text = stringResource(id = demoR.string.notifications_action_text),
                style = typography.body2,
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = DIMEN_X9),
                color = AdmiralButtonColor.ghost(),
                size = AdmiralButtonSize.big(),
                actionText = stringResource(id = demoR.string.notifications_action_show_action),
                onClick = {
                    isVisible = isVisible.not()
                    scope.launch {
                        snackState.showSnackbar(
                            duration = SnackbarDuration.Indefinite,
                            message = context.getString(demoR.string.notifications_action_action_text),
                            actionLabel = context.getString(demoR.string.notifications_action_cancel_text),
                        )
                    }
                }
            )
        }
    }
}

private const val DURATION = 5000L

@Preview
@Composable
private fun ActionNotificationsScreenPreview() {
    AdmiralTheme {
        ActionNotificationsScreen()
    }
}