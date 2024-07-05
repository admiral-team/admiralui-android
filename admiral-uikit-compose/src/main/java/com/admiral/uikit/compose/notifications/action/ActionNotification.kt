package com.admiral.uikit.compose.notifications.action

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.AdmiralTheme.typography
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.button.AdmiralButtonSize
import com.admiral.uikit.compose.button.Button
import com.admiral.uikit.compose.links.Link
import com.admiral.uikit.compose.text.AdmiralTextColor
import com.admiral.uikit.compose.text.Text
import com.admiral.uikit.compose.util.DIMEN_X13
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.compose.util.DIMEN_X7
import com.admiral.uikit.core.components.notification.ActionNotificationCloseType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil

@Suppress("LongMethod", "MagicNumber")
@Composable
fun ActionNotification(
    modifier: Modifier = Modifier,
    text: String? = null,
    closeButtonType: ActionNotificationCloseType = ActionNotificationCloseType.TEXT,
    closeButtonText: String? = null,
    closeButtonIcon: Painter = painterResource(id = R.drawable.admiral_ic_back_outline),
    colors: ActionNotificationColor = AdmiralActionNotificationColor.default(),
    duration: Long = 0,
    isVisible: Boolean = false,
    isTimerVisible: Boolean = false,
    isTimerStart: Boolean = false,
    isCloseButtonVisible: Boolean = false,
    isCloseIconVisible: Boolean = false,
    onCloseButtonClickListener: () -> Unit = {},
    onTimerFinish: () -> Unit = {},
) {

    var millisUntilFinished by remember { mutableLongStateOf(duration) }
    var progressBarValue by remember { mutableIntStateOf(ProgressBarTotal) }
    var secondToShow by remember { mutableIntStateOf((duration / millisUntilFinished).toInt()) }

    LaunchedEffect(key1 = isVisible, key2 = isTimerStart) {
        millisUntilFinished = duration
        while (millisUntilFinished > 0 && isTimerStart) {
            delay(CountDownInterval)
            millisUntilFinished -= CountDownInterval
            secondToShow = ceil(millisUntilFinished / MillisecondsToSeconds).toInt()
            progressBarValue =
                (ProgressBarTotal * (millisUntilFinished.toFloat() / duration)).toInt()
        }
        if (millisUntilFinished <= 0) {
            onTimerFinish()
        }
    }

    if (isVisible) {
        ConstraintLayout(
            modifier = modifier
                .heightIn(min = DIMEN_X13)
                .fillMaxWidth()
                .clip(RoundedCornerShape(DIMEN_X2))
                .background(color = colors.background, shape = RoundedCornerShape(DIMEN_X2))
                .padding(horizontal = DIMEN_X4, vertical = DIMEN_X3),
        ) {

            val (
                timerContainerId,
                textId,
                cancelBtnContainerId,
            ) = createRefs()

            Box(
                modifier = Modifier
                    .constrainAs(timerContainerId) {
                        linkTo(bottom = parent.bottom, top = parent.top)
                        start.linkTo(parent.start)
                    }
                    .size(DIMEN_X7),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    progress = { progressBarValue / 100f },
                    modifier = Modifier
                        .scale(scaleX = ProgressScaleX, scaleY = ProgressScaleY),
                    color = colors.progress,
                    strokeWidth = ProgressStrokeWidth,
                    strokeCap = StrokeCap.Round,
                )

                if (millisUntilFinished > 0 && isTimerVisible) {
                    Text(
                        text = secondToShow.toString(),
                        color = AdmiralTextColor.primary(colors.progress, colors.progress),
                        style = typography.subhead3,
                    )
                }
            }

            text?.let {
                Text(
                    modifier = Modifier
                        .constrainAs(textId) {
                            linkTo(bottom = parent.bottom, top = parent.top)
                            linkTo(start = timerContainerId.end, end = cancelBtnContainerId.start)
                            width = Dimension.fillToConstraints
                        }
                        .padding(start = DIMEN_X3, end = DIMEN_X2),
                    text = text,
                    style = typography.body2,
                    color = AdmiralTextColor.primary(
                        textColorNormal = colors.text,
                        textColorDisabled = colors.text
                    )
                )
            }

            Box(
                modifier = Modifier
                    .constrainAs(cancelBtnContainerId) {
                        linkTo(bottom = parent.bottom, top = parent.top)
                        end.linkTo(parent.end)
                    }
            ) {
                if (closeButtonType == ActionNotificationCloseType.TEXT && isCloseButtonVisible) {
                    Link(
                        modifier = Modifier,
                        linkText = closeButtonText,
                        onClick = onCloseButtonClickListener
                    )
                } else if (isCloseIconVisible) {
                    Icon(
                        modifier = Modifier
                            .size(DIMEN_X7)
                            .clickable(
                                onClick = onCloseButtonClickListener
                            ),
                        painter = closeButtonIcon,
                        tint = colors.closeButton,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

private const val ProgressScaleX = -1.0f
private const val ProgressScaleY = 1.0f
private val ProgressStrokeWidth = 2.dp
private const val ProgressBarTotal = 100
private const val CountDownInterval = 50L
private const val MillisecondsToSeconds = 1000.0

@Preview
@Composable
private fun ActionNotificationPreview() {
    var isVisible by remember { mutableStateOf(false) }
    val snackState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    AdmiralTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            backgroundColor = AdmiralTheme.colors.backgroundBasic,
            snackbarHost = {
                SnackbarHost(
                    hostState = snackState,
                    snackbar = { snackbarData: SnackbarData ->
                        ActionNotification(
                            modifier = Modifier
                                .padding(horizontal = DIMEN_X4)
                                .padding(bottom = DIMEN_X6),
                            text = snackbarData.message,
                            duration = 5000,
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
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(DIMEN_X4),
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    actionText = "Show action notification",
                    size = AdmiralButtonSize.small(),
                    onClick = {
                        isVisible = isVisible.not()
                        scope.launch {
                            snackState.showSnackbar(
                                duration = SnackbarDuration.Indefinite,
                                message = "Сообщение будет удалено",
                                actionLabel = "Отмена"
                            )
                        }
                    },
                )
            }
        }
    }
}
