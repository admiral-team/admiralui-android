package com.admiral.demo.compose.home.badges

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.badge.AdmiralBadgeColor
import com.admiral.uikit.compose.badge.AdmiralBadgePosition
import com.admiral.uikit.compose.badge.BadgedBox
import com.admiral.uikit.compose.cell.unit.IconBackgroundCellUnit
import com.admiral.uikit.compose.input.AdmiralInputNumberColors
import com.admiral.uikit.compose.input.InputNumber
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.core.components.input.InputType

@Composable
@Suppress("LongMethod", "MagicNumber")
fun NormalBadgesScreen(
    onBackClick: () -> Unit = {},
) {
    var isEnabled by remember { mutableStateOf(true) }
    var additionalValue by remember { mutableIntStateOf(8) }
    var naturalValue by remember { mutableIntStateOf(1) }
    var defaultValue by remember { mutableIntStateOf(1) }
    var successValue by remember { mutableIntStateOf(1) }
    var errorValue by remember { mutableIntStateOf(1) }
    var attentionValue by remember { mutableIntStateOf(1) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = R.string.badges_small_title)
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
                    isEnabled = it == 0
                }
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Column(
                modifier = Modifier.padding(horizontal = DIMEN_X4),
                verticalArrangement = Arrangement.Center
            ) {
                val iconBackgroundCellUnit = IconBackgroundCellUnit(
                    unitType = CellUnitType.LEADING,
                    icon = painterResource(id = R.drawable.admiral_ic_diamond_solid)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BadgedBox(
                        modifier = Modifier.padding(vertical = DIMEN_X2),
                        isEnable = isEnabled,
                        content = additionalValue,
                        color = AdmiralBadgeColor.additional(),
                        position = AdmiralBadgePosition.default(
                            badgeWithContentHorizontalOffset = HorizontalOffset,
                            badgeVerticalWithContentOffset = VerticalOffset
                        ),
                        anchor = {
                            iconBackgroundCellUnit.Create(
                                modifier = Modifier.padding(IconPadding)
                            )
                        }
                    )
                    InputNumber(
                        modifier = Modifier.fillMaxWidth(),
                        isEnabled = isEnabled,
                        value = additionalValue,
                        inputType = InputType.OVAL,
                        colors = AdmiralInputNumberColors.oval(
                            textEnable = AdmiralTheme.colors.textSecondary,
                            textDisable = AdmiralTheme.colors.textSecondary
                        ),
                        onValueChange = { old, _ ->
                            additionalValue = old
                        },
                        optionalText = stringResource(id = R.string.badges_additional)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BadgedBox(
                        modifier = Modifier.padding(vertical = DIMEN_X2),
                        isEnable = isEnabled,
                        content = naturalValue,
                        color = AdmiralBadgeColor.natural(),
                        position = AdmiralBadgePosition.default(
                            badgeWithContentHorizontalOffset = HorizontalOffset,
                            badgeVerticalWithContentOffset = VerticalOffset
                        ),
                        anchor = {
                            iconBackgroundCellUnit.Create(
                                modifier = Modifier.padding(
                                    IconPadding
                                )
                            )
                        }
                    )
                    InputNumber(
                        modifier = Modifier.fillMaxWidth(),
                        value = naturalValue,
                        isEnabled = isEnabled,
                        inputType = InputType.OVAL,
                        colors = AdmiralInputNumberColors.oval(
                            textEnable = AdmiralTheme.colors.textSecondary,
                            textDisable = AdmiralTheme.colors.textSecondary
                        ),
                        onValueChange = { old, _ ->
                            naturalValue = old
                        },
                        optionalText = stringResource(id = R.string.badges_natural)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BadgedBox(
                        modifier = Modifier.padding(vertical = DIMEN_X2),
                        isEnable = isEnabled,
                        content = defaultValue,
                        color = AdmiralBadgeColor.default(),
                        position = AdmiralBadgePosition.default(
                            badgeWithContentHorizontalOffset = HorizontalOffset,
                            badgeVerticalWithContentOffset = VerticalOffset
                        ),
                        anchor = {
                            iconBackgroundCellUnit.Create(
                                modifier = Modifier.padding(
                                    IconPadding
                                )
                            )
                        }
                    )
                    InputNumber(
                        modifier = Modifier.fillMaxWidth(),
                        value = defaultValue,
                        isEnabled = isEnabled,
                        inputType = InputType.OVAL,
                        colors = AdmiralInputNumberColors.oval(
                            textEnable = AdmiralTheme.colors.textSecondary,
                            textDisable = AdmiralTheme.colors.textSecondary
                        ),
                        onValueChange = { old, _ ->
                            defaultValue = old
                        },
                        optionalText = stringResource(id = R.string.badges_default)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BadgedBox(
                        modifier = Modifier.padding(vertical = DIMEN_X2),
                        isEnable = isEnabled,
                        content = successValue,
                        color = AdmiralBadgeColor.success(),
                        position = AdmiralBadgePosition.default(
                            badgeWithContentHorizontalOffset = HorizontalOffset,
                            badgeVerticalWithContentOffset = VerticalOffset
                        ),
                        anchor = {
                            iconBackgroundCellUnit.Create(modifier = Modifier.padding(IconPadding))
                        }
                    )
                    InputNumber(
                        modifier = Modifier.fillMaxWidth(),
                        value = successValue,
                        isEnabled = isEnabled,
                        inputType = InputType.OVAL,
                        colors = AdmiralInputNumberColors.oval(
                            textEnable = AdmiralTheme.colors.textSecondary,
                            textDisable = AdmiralTheme.colors.textSecondary
                        ),
                        onValueChange = { old, _ ->
                            successValue = old
                        },
                        optionalText = stringResource(id = R.string.badges_success)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BadgedBox(
                        modifier = Modifier.padding(vertical = DIMEN_X2),
                        isEnable = isEnabled,
                        content = errorValue,
                        color = AdmiralBadgeColor.error(),
                        position = AdmiralBadgePosition.default(
                            badgeWithContentHorizontalOffset = HorizontalOffset,
                            badgeVerticalWithContentOffset = VerticalOffset
                        ),
                        anchor = {
                            iconBackgroundCellUnit.Create(modifier = Modifier.padding(IconPadding))
                        }
                    )
                    InputNumber(
                        modifier = Modifier.fillMaxWidth(),
                        value = errorValue,
                        isEnabled = isEnabled,
                        inputType = InputType.OVAL,
                        colors = AdmiralInputNumberColors.oval(
                            textEnable = AdmiralTheme.colors.textSecondary,
                            textDisable = AdmiralTheme.colors.textSecondary
                        ),
                        onValueChange = { old, _ ->
                            errorValue = old
                        },
                        optionalText = stringResource(id = R.string.badges_error)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BadgedBox(
                        modifier = Modifier.padding(vertical = DIMEN_X2),
                        isEnable = isEnabled,
                        content = attentionValue,
                        color = AdmiralBadgeColor.attention(),
                        position = AdmiralBadgePosition.default(
                            badgeWithContentHorizontalOffset = HorizontalOffset,
                            badgeVerticalWithContentOffset = VerticalOffset
                        ),
                        anchor = {
                            iconBackgroundCellUnit.Create(modifier = Modifier)
                        }
                    )
                    InputNumber(
                        modifier = Modifier.fillMaxWidth(),
                        value = attentionValue,
                        isEnabled = isEnabled,
                        inputType = InputType.OVAL,
                        colors = AdmiralInputNumberColors.oval(
                            textEnable = AdmiralTheme.colors.textSecondary,
                            textDisable = AdmiralTheme.colors.textSecondary
                        ),
                        onValueChange = { old, _ ->
                            attentionValue = old
                        },
                        optionalText = stringResource(id = R.string.badges_attention)
                    )
                }
            }
        }
    }
}

@Suppress("MagicNumber")
private val VerticalOffset = (-15).dp
@Suppress("MagicNumber")
private val HorizontalOffset = (-16).dp
private val IconPadding = 2.dp

@Preview
@Composable
private fun NormalBadgesScreenPreview() {
    AdmiralTheme {
        NormalBadgesScreen()
    }
}